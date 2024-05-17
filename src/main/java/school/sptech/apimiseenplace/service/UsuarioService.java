package school.sptech.apimiseenplace.service;

import ch.qos.logback.classic.spi.IThrowableProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.apimiseenplace.api.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.apimiseenplace.dto.usuario.*;
import school.sptech.apimiseenplace.entity.Pedido;
import school.sptech.apimiseenplace.entity.Usuario;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.ConflitoException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

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

    public Usuario criar(UsuarioCriacaoDto usuarioCriacaoDto){
        if (usuarioCriacaoDto == null) throw new BadRequestException("Usuario");

        if (usuarioRepository.existsByEmail(usuarioCriacaoDto.getEmail())) throw new ConflitoException("Usuario");

        usuarioCriacaoDto.setSenha(passwordEncoder.encode(usuarioCriacaoDto.getSenha()));
        return usuarioRepository.save(UsuarioMapper.toEntity(usuarioCriacaoDto));
    }

    public List<UsuarioListagemDto> listar(){
        return UsuarioMapper.toDto(usuarioRepository.findAll());
    }

    public Usuario encontrarPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Usuario")
        );
    }

    public void deletarUsuarioPorId(Integer id){
        if(!usuarioRepository.existsById(id)){
            throw new NaoEncontradoException("Usuario");
        }

        usuarioRepository.deleteById(id);
    }

    public UsuarioListagemDto atualizar(int id, UsuarioAtualizarDto usuarioAtualizacao){
        if(!usuarioRepository.existsById(id)){
            throw new NaoEncontradoException("Usuario Atualizacao");
        }

        Usuario usuarioAchado = encontrarPorId(id);
        usuarioAchado.setIdUsuario(id);
        usuarioAchado.setNome(usuarioAtualizacao.getNome());
        usuarioAchado.setEmail(usuarioAtualizacao.getEmail());
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
}
