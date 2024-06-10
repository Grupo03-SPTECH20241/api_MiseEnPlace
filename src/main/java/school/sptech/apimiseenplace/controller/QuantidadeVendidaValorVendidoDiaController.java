package school.sptech.apimiseenplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidoValorVendidosDia.VwQuantidadeVendidaValorVendidoDiaDto;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidoValorVendidosDia.VwQuantidadeVendidaValorVendidoDiaMapper;
import school.sptech.apimiseenplace.service.VwQuantidadeVendidaValorVendidoDiaService;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendidoDia;

import java.util.List;

@RestController
@RequestMapping("/quantidade-vendida-valor-vendido-dia")
@RequiredArgsConstructor
public class QuantidadeVendidaValorVendidoDiaController {
    private final VwQuantidadeVendidaValorVendidoDiaService service;

    @GetMapping
    public ResponseEntity<List<VwQuantidadeVendidaValorVendidoDiaDto>> listar(){
        List<VwQuantidadeVendidaValorVendidoDia> lista = service.listar();
        if(lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(VwQuantidadeVendidaValorVendidoDiaMapper.toDto(lista));
    }
}
