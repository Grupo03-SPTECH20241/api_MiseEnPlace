package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.dto.unidadeMedida.UnidadeMedidaCriacaoDto;
import school.sptech.apimiseenplace.dto.unidadeMedida.UnidadeMedidaListagemDto;
import school.sptech.apimiseenplace.dto.unidadeMedida.UnidadeMedidaMapper;
import school.sptech.apimiseenplace.entity.UnidadeMedida;
import school.sptech.apimiseenplace.service.UnidadeMedidaService;

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
}
