package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.unidadeMedida.UnidadeMedidaCriacaoDto;
import school.sptech.apimiseenplace.dto.unidadeMedida.UnidadeMedidaListagemDto;
import school.sptech.apimiseenplace.dto.unidadeMedida.UnidadeMedidaMapper;
import school.sptech.apimiseenplace.entity.UnidadeMedida;
import school.sptech.apimiseenplace.service.UnidadeMedidaService;

import java.util.List;

@RestController
@RequestMapping("/unidades-medida")
@RequiredArgsConstructor
public class UnidadeMedidaController {
    private final UnidadeMedidaService unidadeMedidaService;

    @PostMapping
    public ResponseEntity<UnidadeMedidaListagemDto> criar(@RequestBody @Valid UnidadeMedidaCriacaoDto unidadeMedidaCriacaoDto){
        UnidadeMedida unidadeMedidaSalva = unidadeMedidaService.criar(unidadeMedidaCriacaoDto);
        return ResponseEntity.ok().body(UnidadeMedidaMapper.toDto(unidadeMedidaSalva));
    }

    @GetMapping
    public ResponseEntity<List<UnidadeMedidaListagemDto>> listar(){
        List<UnidadeMedida> unidadesMedida = unidadeMedidaService.listar();
        if (unidadesMedida.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(UnidadeMedidaMapper.toDto(unidadesMedida));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeMedidaListagemDto> listarPorId(@PathVariable int id){
        return ResponseEntity.ok(UnidadeMedidaMapper.toDto( unidadeMedidaService.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeMedidaListagemDto> atualizar(@PathVariable int id, @RequestBody @Valid UnidadeMedidaCriacaoDto unidadeMedidaAtualizacao){
        UnidadeMedida unidadeMedidaAtualizada = unidadeMedidaService.atualizar(id, unidadeMedidaAtualizacao);
        return ResponseEntity.ok(UnidadeMedidaMapper.toDto(unidadeMedidaAtualizada));
    }

    
}
