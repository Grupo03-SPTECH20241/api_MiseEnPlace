package school.sptech.apimiseenplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidos.vwQuantidadeVendidosDia.QuantidadeVendidosDiaDto;
import school.sptech.apimiseenplace.service.QuantidadeVendidosDiaService;

import java.util.List;

@RestController
@RequestMapping("/quantidade-vendidos-dia")
@RequiredArgsConstructor
public class QuantidadeVendidosDiaController {
    private final QuantidadeVendidosDiaService quantidadeVendidosDiaService;

    @GetMapping
    public ResponseEntity<List<QuantidadeVendidosDiaDto>> listar() {
        List<QuantidadeVendidosDiaDto> quantidadeVendidosDia = quantidadeVendidosDiaService.listar();
        if (quantidadeVendidosDia.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(quantidadeVendidosDia);
    }
}
