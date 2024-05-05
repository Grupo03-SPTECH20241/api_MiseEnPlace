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
import school.sptech.crudloginsenha.entity.Pedido;
import school.sptech.crudloginsenha.entity.Usuario;
import school.sptech.crudloginsenha.repository.UsuarioRepository;
import school.sptech.crudloginsenha.service.UsuarioService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

        if(usuarioRepository.existsByEmail(usuarioCriacao.getEmail())){
            return ResponseEntity.status(409).build();
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

    @GetMapping("/quicksort-nome")
    public ResponseEntity<List<UsuarioListagemDto>> ordernar() {
        List<Usuario> usuarioList = usuarioRepository.findAll();

        if (usuarioList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Usuario[] usuarios = new Usuario[usuarioRepository.findAll().size()];

        for (int i = 0; i < usuarioList.size(); i++) {
            usuarios[i] = usuarioList.get(i);
        }

        Usuario[] usuariosOrdenados = this.ordenarNomeQuicksort(
                usuarios,
                0,
                usuarios.length
        );

        List<Usuario> entidadeLista = new ArrayList<>();

        for (int i = 0; i < usuariosOrdenados.length; i++) {
            entidadeLista.add(usuariosOrdenados[i]);
        }

        List<UsuarioListagemDto> dtoOrdenado = UsuarioMapper.toDto(entidadeLista);

        return ResponseEntity.ok(dtoOrdenado);
    }

    public Usuario[] ordenarNomeQuicksort(
            Usuario[] v,
            int indInicio,
            int indFim
    ) {
        int i, j;
        String pivo;

        i = indInicio;
        j = indFim - 1;
        pivo = v[(indInicio + j) / 2].getNome();

        while(i <= j) {
            while (i < indFim && v[i].getNome().compareTo(pivo) < 0) {
                i = i + 1;
            }
            while (j > indInicio && v[j].getNome().compareTo(pivo) > 0) {
                j = j - 1;
            }
            if (i <= j) {
                Usuario temp = v[i];
                v[i] = v[j];
                v[j] = temp;
                i = i + 1;
                j = j - 1;
            }
        }

        if (indInicio < j + 1) {
            ordenarNomeQuicksort(v, indInicio, j + 1);
        }
        if (i < indFim) {
            ordenarNomeQuicksort(v, i, indFim);
        }
        return v;
    }
}
