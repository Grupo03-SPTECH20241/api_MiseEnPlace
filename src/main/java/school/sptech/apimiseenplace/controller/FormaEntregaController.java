package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.forma_entrega.FormaEntregaCriacaoDto;
import school.sptech.apimiseenplace.dto.forma_entrega.FormaEntregaListagemDto;
import school.sptech.apimiseenplace.dto.forma_entrega.FormaEntregaMapper;
import school.sptech.apimiseenplace.entity.FormaEntrega;
import school.sptech.apimiseenplace.service.FormaEntregaService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forma-entregas")
@RequiredArgsConstructor
public class FormaEntregaController {
    private final FormaEntregaService formaEntregaService;

    @PostMapping
    public ResponseEntity<FormaEntregaListagemDto> cadastrar(@RequestBody @Valid FormaEntregaCriacaoDto formaEntregaCriacaoDto) {
        FormaEntrega formaEntrega = FormaEntregaMapper.toEntity(formaEntregaCriacaoDto);
        formaEntrega = formaEntregaService.cadastrar(formaEntrega);
        FormaEntregaListagemDto formaEntregaListagemDto = FormaEntregaMapper.toDto(formaEntrega);
        URI uri = URI.create("/forma-entregas/" + formaEntregaListagemDto.getIdFormaEntrega());
        return ResponseEntity.created(uri).body(formaEntregaListagemDto);
    }

    @GetMapping
    public ResponseEntity<List<FormaEntregaListagemDto>> listar() {
        List<FormaEntregaListagemDto> formaEntregaListagemDtos = formaEntregaService.listar();
        if (formaEntregaListagemDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(formaEntregaListagemDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaEntregaListagemDto> atualizar(@PathVariable Integer id, @RequestBody FormaEntregaCriacaoDto formaEntregaCriacaoDto) {
        FormaEntrega formaEntrega = FormaEntregaMapper.toEntity(formaEntregaCriacaoDto);
        formaEntrega = formaEntregaService.atualizar(id, formaEntrega);
        return ResponseEntity.ok(FormaEntregaMapper.toDto(formaEntrega));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        formaEntregaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
