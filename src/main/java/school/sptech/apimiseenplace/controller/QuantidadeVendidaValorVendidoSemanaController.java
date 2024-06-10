package school.sptech.apimiseenplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidaValorVendidoSemana.QuantidadeVendidaValorVendidoSemanaDto;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidaValorVendidoSemana.QuantidadeVendidaValorVendidoSemanaMapper;
import school.sptech.apimiseenplace.service.QuantidadeVendidaValorVendidoSemanaService;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendidoSemana;

import java.util.List;

@RestController
@RequestMapping("/quantidade-vendida-valor-vendido-semana")
@RequiredArgsConstructor
public class QuantidadeVendidaValorVendidoSemanaController {
    private final QuantidadeVendidaValorVendidoSemanaService service;

    @GetMapping
    public ResponseEntity<List<QuantidadeVendidaValorVendidoSemanaDto>> listar(){
        List<VwQuantidadeVendidaValorVendidoSemana> lista = service.listar();
        if(lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(QuantidadeVendidaValorVendidoSemanaMapper.toDto(lista));
    }
}
