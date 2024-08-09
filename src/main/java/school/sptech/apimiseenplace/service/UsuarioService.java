package school.sptech.apimiseenplace.service;

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
import school.sptech.apimiseenplace.entity.Usuario;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.ConflitoException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.UsuarioRepository;

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

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto){
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado = usuarioRepository.findByEmail(usuarioLoginDto.getEmail()).orElseThrow(
                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public Usuario criar(UsuarioEmailDto usuarioEmailDto){
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

        if(!email){
            throw new BadRequestException("Email");
        }
        return usuarioRepository.save(UsuarioMapper.toEntity(entity));
    }

    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    public Usuario encontrarPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Usuario")
        );
    }

    public String deletarUsuarioPorId(Integer id){
        if(!usuarioRepository.existsById(id)){
            throw new NaoEncontradoException("Usuario");
        }

        usuarioRepository.deleteById(id);
        return "Usuario deletado com sucesso!";
    }

    public UsuarioListagemDto atualizar(int id, UsuarioAtualizarDto usuarioAtualizacao){
        if(!usuarioRepository.existsById(id)){
            throw new NaoEncontradoException("Usuario Atualizacao");
        }

        Usuario usuarioAchado = encontrarPorId(id);
        usuarioAchado.setIdUsuario(id);

        if(usuarioAtualizacao.getNome() != null && !usuarioAtualizacao.getNome().isBlank() && !usuarioAtualizacao.getNome().isEmpty() ) usuarioAchado.setNome(usuarioAtualizacao.getNome());
        if(usuarioAtualizacao.getEmail() != null && !usuarioAtualizacao.getEmail().isBlank() && !usuarioAtualizacao.getEmail().isEmpty() ) usuarioAchado.setEmail(usuarioAtualizacao.getEmail());
        if(usuarioAtualizacao.getCnpj() != null && !usuarioAtualizacao.getCnpj().isBlank() && !usuarioAtualizacao.getCnpj().isEmpty() ) usuarioAchado.setCnpj(usuarioAtualizacao.getCnpj());
        if(usuarioAtualizacao.getLogo() != null && !usuarioAtualizacao.getLogo().isBlank() && !usuarioAtualizacao.getLogo().isEmpty() ) usuarioAchado.setLogo(usuarioAtualizacao.getLogo());

        return UsuarioMapper.toDto(usuarioRepository.save(usuarioAchado));
    }

    public String atualizarSenha(int id, String senhaNova){
        if(!usuarioRepository.existsById(id)){
            throw new NaoEncontradoException("Usuario");
        }
        Usuario usuarioAchado = encontrarPorId(id);

        usuarioAchado.setIdUsuario(id);
        usuarioAchado.setSenha(senhaNova);
        usuarioRepository.save(usuarioAchado);

        return "Senha Atualizada com Sucesso!";
    }

    private String generateSenha(int tamanho){
        final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LOWER = UPPER.toLowerCase();
        final String DIGITS = "0123456789";
        final String SPECIAL_CHARS = "!@#$%^&*()_+{}[]";

        final String CHARS = UPPER + LOWER + DIGITS + SPECIAL_CHARS;

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for(int i =0; i < tamanho; i++){
            password.append(CHARS.charAt(random.nextInt(10, CHARS.length() - 1)));
        }

        return password.toString();
    }
}
