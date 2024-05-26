package school.sptech.apimiseenplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.festa.FestaCriacaoDto;
import school.sptech.apimiseenplace.dto.festa.FestaListagemDto;
import school.sptech.apimiseenplace.dto.festa.FestaMapper;
import school.sptech.apimiseenplace.entity.Festa;
import school.sptech.apimiseenplace.service.FestaService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/festas")
@RequiredArgsConstructor
public class FestaController {
    private final FestaService festaService;

    @PostMapping
    public ResponseEntity<FestaListagemDto> cadastrar(@RequestBody FestaCriacaoDto festaCriacaoDto) {
        Festa festa = FestaMapper.toEntity();
        festa = festaService.cadastrar(
                festa,
                festaCriacaoDto.getPedidoId(),
                festaCriacaoDto.getEnderecoId()
        );
        FestaListagemDto festaListagemDto = FestaMapper.toDto(festa);
        URI uri = URI.create("/festas/" + festaListagemDto.getId());
        return ResponseEntity.created(uri).body(festaListagemDto);
    }

    @GetMapping
    public ResponseEntity<List<FestaListagemDto>> listar() {
        List<FestaListagemDto> festaListagemDtos = festaService.listar();
        if (festaListagemDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(festaListagemDtos);
    }

    @PostMapping("/{id}")
    public ResponseEntity<FestaListagemDto> atualizar(@PathVariable Integer id, @RequestBody FestaCriacaoDto festaCriacaoDto) {
        Festa festa = FestaMapper.toEntity();
        festa = festaService.atualizar(id, festa, festaCriacaoDto.getPedidoId(), festaCriacaoDto.getEnderecoId());
        return ResponseEntity.ok(FestaMapper.toDto(festa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        festaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
