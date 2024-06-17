package school.sptech.apimiseenplace.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.usuario.*;
import school.sptech.apimiseenplace.entity.Usuario;
import school.sptech.apimiseenplace.repository.UsuarioRepository;
import school.sptech.apimiseenplace.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioListagemDto> criar(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacao){
        Usuario usuarioSalvo = usuarioService.criar(usuarioCriacao);
        return ResponseEntity.created(null).body(UsuarioMapper.toDto(usuarioSalvo));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioListagemDto>> listar(){
        List<UsuarioListagemDto> usuarios = UsuarioMapper.toDto(usuarioService.listar());
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioListagemDto> listarPorId(@PathVariable int id){
        return ResponseEntity.ok(UsuarioMapper.toDto( usuarioService.encontrarPorId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id) {
        usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioListagemDto> atualizar(@PathVariable int id, @RequestBody @Valid UsuarioAtualizarDto usuarioAtualizacao){
       UsuarioListagemDto usuarioAtualizado = usuarioService.atualizar(id, usuarioAtualizacao);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> atualizarSenha(@PathVariable int id, @RequestParam String senhaNova){
        return ResponseEntity.ok(usuarioService.atualizarSenha(id, senhaNova));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto){
        UsuarioTokenDto usuarioToken = this.usuarioService.autenticar(usuarioLoginDto);
        if (!usuarioToken.getToken().isBlank()){
            Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioToken.getEmail());
            if (usuario.isEmpty()){
                return ResponseEntity.noContent().build();
            }else{
                usuarioService.autenticar(usuarioLoginDto);
            }
        }
        return ResponseEntity.status(200).body(usuarioToken);
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

        System.out.println(pivo);

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
