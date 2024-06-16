package school.sptech.apimiseenplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.endereco.EnderecoCriacaoDto;
import school.sptech.apimiseenplace.dto.endereco.EnderecoListagemDto;
import school.sptech.apimiseenplace.dto.endereco.EnderecoMapper;
import school.sptech.apimiseenplace.entity.Endereco;
import school.sptech.apimiseenplace.service.EnderecoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {
    private final EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<EnderecoListagemDto> cadastrar(@RequestBody EnderecoCriacaoDto enderecoCriacaoDto) {
        Endereco endereco = EnderecoMapper.toEntity(enderecoCriacaoDto);
        endereco = enderecoService.cadastrar(endereco);
        EnderecoListagemDto enderecoListagemDto = EnderecoMapper.toDto(endereco);
        URI uri = URI.create("/enderecos/" + enderecoListagemDto.getIdEndereco());
        return ResponseEntity.created(uri).body(enderecoListagemDto);
    }

    @GetMapping
    public ResponseEntity<List<EnderecoListagemDto>> listar() {
        List<Endereco> enderecos = enderecoService.listar();
        if (enderecos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(EnderecoMapper.toDto(enderecos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoListagemDto> atualizar(@PathVariable Integer id, @RequestBody EnderecoCriacaoDto enderecoCriacaoDto) {
        Endereco endereco = EnderecoMapper.toEntity(enderecoCriacaoDto);
        endereco = enderecoService.atualizar(id, endereco);
        return ResponseEntity.ok(EnderecoMapper.toDto(endereco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
