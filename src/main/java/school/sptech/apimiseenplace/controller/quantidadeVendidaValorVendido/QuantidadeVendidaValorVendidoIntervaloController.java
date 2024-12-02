package school.sptech.apimiseenplace.controller.quantidadeVendidaValorVendido;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.quantidadeVendidaValorVendidoIntervalo.QuantidadeVendidaValorVendidoIntervaloDto;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidaValorVendidoSemana.QuantidadeVendidaValorVendidoSemanaDto;
import school.sptech.apimiseenplace.service.quantidadeVendidaValorVendido.QuantidadeVendidaValorVendidoIntervaloService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/quantidade-vendida-valor-vendido-intervalo")
@RequiredArgsConstructor
public class QuantidadeVendidaValorVendidoIntervaloController {
    private final QuantidadeVendidaValorVendidoIntervaloService service;

    @GetMapping
    public ResponseEntity<List<QuantidadeVendidaValorVendidoIntervaloDto>> listar(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<QuantidadeVendidaValorVendidoIntervaloDto> vwTipoProdutoDtos = service.findInInterval(startDate, endDate);
        if (vwTipoProdutoDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vwTipoProdutoDtos);
    }
}
