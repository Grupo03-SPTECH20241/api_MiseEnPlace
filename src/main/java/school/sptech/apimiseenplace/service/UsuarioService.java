package school.sptech.apimiseenplace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.apimiseenplace.api.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.apimiseenplace.dto.usuario.*;
import school.sptech.apimiseenplace.entity.BodyMessage;
import school.sptech.apimiseenplace.entity.LogoRecord;
import school.sptech.apimiseenplace.entity.Usuario;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.ConflitoException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.UsuarioRepository;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final LambdaService lambdaService;

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado = usuarioRepository.findByEmail(usuarioLoginDto.getEmail()).orElseThrow(
                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public Usuario criar(UsuarioEmailDto usuarioEmailDto) throws JsonProcessingException {
        if (usuarioEmailDto == null) throw new BadRequestException("Usuario");

        if (usuarioRepository.existsByEmail(usuarioEmailDto.getEmail())) throw new ConflitoException("Usuario");

        Random random = new Random();

        var senha = generateSenha(random.nextInt(10));
        var entity = UsuarioMapper.fromEmailToCriacao(usuarioEmailDto);
        entity.setSenha(passwordEncoder.encode(senha));
        var email = emailService.sendTextEmail(usuarioEmailDto.getEmail(), "Senha de Acesso", """
                Olá %s,
                                
                Esperamos que você esteja bem.
                                
                Conforme solicitado, aqui está a sua nova senha para acessar o Mise En Place:
                                
                Senha: %s
                                
                Por favor, siga as instruções abaixo para garantir a segurança da sua conta:
                1. Faça login no Mise En Place usando a nova senha fornecida.
                2. Após o login, recomendamos que você altere esta senha para uma de sua preferência.
                3. Certifique-se de escolher uma senha forte, combinando letras maiúsculas e minúsculas, números e caracteres especiais.
                                
                Se você não solicitou esta alteração de senha, por favor, entre em contato conosco imediatamente.
                                
                Atenciosamente,
                                
                Equipe QGD Consultoria
                """.formatted(usuarioEmailDto.getNome(), senha));

        byte[] bytes = entity.getLogo();
        var arquivo = Base64.getEncoder().encodeToString(bytes);
        var nomeArquivo = "logo-" + LocalDateTime.now();

        ObjectMapper objectMapper = new ObjectMapper();

        var response = lambdaService.sendToLambda("arn:aws:lambda:us-east-1:942802636108:function:sobeParaS3", "bucket-testee", arquivo, nomeArquivo);
        var reponseString = response.payload().asUtf8String();
        LogoRecord logoRecord = objectMapper.readValue(reponseString, LogoRecord.class);
        BodyMessage bodyMessage = objectMapper.readValue(logoRecord.body(), BodyMessage.class);

        if (response.statusCode() != 200) {
            throw new BadRequestException("Lambda");
        }
        if (!email) {
            throw new BadRequestException("Email");
        }

        return usuarioRepository.save(UsuarioMapper.toEntity(entity, bodyMessage.url()));
    }


    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public String obterFotoCliente(String email) {
        if (email == null || email.isBlank() || email.isEmpty()) throw new BadRequestException("Email Usuario");

        return usuarioRepository.findLogoByEmail(email);
    }

    public Usuario encontrarPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Usuario")
        );
    }

    public String deletarUsuarioPorId(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NaoEncontradoException("Usuario");
        }

        usuarioRepository.deleteById(id);
        return "Usuario deletado com sucesso!";
    }

    public UsuarioListagemDto atualizar(int id, UsuarioAtualizarDto usuarioAtualizacao) {
        if (!usuarioRepository.existsById(id)) {
            throw new NaoEncontradoException("Usuario Atualizacao");
        }

        Usuario usuarioAchado = encontrarPorId(id);
        usuarioAchado.setIdUsuario(id);

        if (usuarioAtualizacao.getNome() != null && !usuarioAtualizacao.getNome().isBlank() && !usuarioAtualizacao.getNome().isEmpty())
            usuarioAchado.setNome(usuarioAtualizacao.getNome());
        if (usuarioAtualizacao.getEmail() != null && !usuarioAtualizacao.getEmail().isBlank() && !usuarioAtualizacao.getEmail().isEmpty())
            usuarioAchado.setEmail(usuarioAtualizacao.getEmail());
        if (usuarioAtualizacao.getCnpj() != null && !usuarioAtualizacao.getCnpj().isBlank() && !usuarioAtualizacao.getCnpj().isEmpty())
            usuarioAchado.setCnpj(usuarioAtualizacao.getCnpj());

        return UsuarioMapper.toDto(usuarioRepository.save(usuarioAchado));
    }

    public String atualizarSenha(int id, String senhaNova) {
        if (!usuarioRepository.existsById(id)) {
            throw new NaoEncontradoException("Usuario");
        }
        Usuario usuarioAchado = encontrarPorId(id);

        usuarioAchado.setIdUsuario(id);
        usuarioAchado.setSenha(senhaNova);
        usuarioRepository.save(usuarioAchado);

        return "Senha Atualizada com Sucesso!";
    }


    private String generateSenha(int tamanho) {

        List<String> CHARS = new ArrayList<>();
        var chars = "!;#;$;%;&;(;);*;+;,;-;.;/;0;1;2;3;4;5;6;7;8;9;:;<;=;>;?;@;A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z;[;];^;_;`;a;b;c;d;e;f;g;h;i;j;k;l;m;n;o;p;q;r;s;t;u;v;w;x;y;z;{;|;};~;'".split(";");

        for (var item : chars) {
            CHARS.add(item);
        }

        CHARS.remove(CHARS.size() - 1);

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < tamanho; i++) {
            password.append(CHARS.get(random.nextInt(0, CHARS.size() - 1)));
        }

        return password.toString();
    }


}
