package school.sptech.crudloginsenha.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crudloginsenha.api.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.crudloginsenha.dto.usuario.*;
import school.sptech.crudloginsenha.entity.Usuario;
import school.sptech.crudloginsenha.repository.UsuarioRepository;
import school.sptech.crudloginsenha.service.UsuarioService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioListagemDto> criar(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacao){
        if(usuarioCriacao == null){
            return ResponseEntity.badRequest().build();
        }
        String senhaCriptografada = passwordEncoder.encode(usuarioCriacao.getSenha());
        usuarioCriacao.setSenha(senhaCriptografada);

        Usuario usuarioSalvo = usuarioRepository.save(UsuarioMapper.toEntity(usuarioCriacao));
        return ResponseEntity.created(null).body(UsuarioMapper.toDto(usuarioSalvo));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioListagemDto>> listar(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(UsuarioMapper.toDto(usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioListagemDto> listarPorId(@PathVariable int id){
        Optional<Usuario> usuarioAchado = usuarioRepository.findById(id);

        if (usuarioAchado.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(UsuarioMapper.toDto(usuarioAchado.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        if (!usuarioRepository.existsById(id)){
            return ResponseEntity.badRequest().build();
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioListagemDto> atualizar(@PathVariable int id, @RequestBody UsuarioCriacaoDto usuarioAtualizacao){
        if(!usuarioRepository.existsById(id)){
            return ResponseEntity.badRequest().build();
        }

        Optional<Usuario> usuarioAchado = usuarioRepository.findById(id);
        if (usuarioAchado.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        usuarioAchado.get().setIdUsuario(id);
        usuarioAchado.get().setNome(usuarioAtualizacao.getNome());
        usuarioAchado.get().setEmail(usuarioAtualizacao.getEmail());

        Usuario usuarioSalvo = usuarioRepository.save(usuarioAchado.get());
        return ResponseEntity.ok(UsuarioMapper.toDto(usuarioSalvo));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> atualizarSenha(@PathVariable int id, @RequestParam String senhaNova){
        if(!usuarioRepository.existsById(id)){
            return ResponseEntity.badRequest().build();
        }

        Optional<Usuario> usuarioAchado = usuarioRepository.findById(id);

        if (usuarioAchado.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        usuarioAchado.get().setIdUsuario(id);
        usuarioAchado.get().setSenha(senhaNova);

        usuarioRepository.save(usuarioAchado.get());
        return ResponseEntity.ok("Senha Atualizada com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto){
        UsuarioTokenDto usuarioToken = this.usuarioService.autenticar(usuarioLoginDto);
        if (!usuarioToken.getToken().isBlank()){
            Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioToken.getEmail());
            if (usuario.isEmpty()){
                return ResponseEntity.noContent().build();
            }else{
                usuario.get().setLogado(1);
                usuarioRepository.save(usuario.get());
            }
        }
        return ResponseEntity.status(200).body(usuarioToken);
    }

    @PatchMapping("/logoff")
    public ResponseEntity<String> logoff(@RequestParam int id){
        Optional<Usuario> usuarioAchado = usuarioRepository.findById(id);
        if (usuarioAchado.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        usuarioAchado.get().setIdUsuario(id);
        usuarioAchado.get().setLogado(0);
        usuarioRepository.save(usuarioAchado.get());

        return ResponseEntity.ok("Usuario deslogado com sucesso!");
    }


}
