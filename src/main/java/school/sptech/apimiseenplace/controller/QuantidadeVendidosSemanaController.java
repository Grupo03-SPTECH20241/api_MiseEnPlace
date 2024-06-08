package school.sptech.apimiseenplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.dto.vwQuandidadeVendidosSemana.QuandidadeVendidosSemanaDto;
import school.sptech.apimiseenplace.dto.vwQuandidadeVendidosSemana.QuantidadeVendidosSemanaMapper;
import school.sptech.apimiseenplace.service.QuantidadeVendidosSemanaService;
import school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos.VwQuantidadeVendidosSemana;

import java.util.List;

@RestController
@RequestMapping("/quantidade-vendidos-semana")
@RequiredArgsConstructor
public class QuantidadeVendidosSemanaController {
    private final QuantidadeVendidosSemanaService quantidadeVendidosSemanaService;


    @GetMapping
    public ResponseEntity<List<QuandidadeVendidosSemanaDto>> listar(){
        List<VwQuantidadeVendidosSemana> quantidadeVendidosSemana = quantidadeVendidosSemanaService.listar();
        if (quantidadeVendidosSemana.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(QuantidadeVendidosSemanaMapper.toDto(quantidadeVendidosSemana));
    }
}
