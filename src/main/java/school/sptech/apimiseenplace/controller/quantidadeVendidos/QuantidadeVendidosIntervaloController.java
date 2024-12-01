package school.sptech.apimiseenplace.controller.quantidadeVendidos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidos.quantidadeVendidosIntervalo.QuantidadeVendidosIntervaloDto;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidos.vw_quantidade_vendida_mes.QuantidadeVendidosListagemDto;
import school.sptech.apimiseenplace.service.quantidadeVendidos.QuantidadeVendidosIntervaloService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/quantidade-vendidos-intervalo")
@RequiredArgsConstructor
public class QuantidadeVendidosIntervaloController {
    private final QuantidadeVendidosIntervaloService service;

    @GetMapping
    public ResponseEntity<List<QuantidadeVendidosIntervaloDto>> listar(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<QuantidadeVendidosIntervaloDto> vwTipoProdutoDtos = service.findInInterval(startDate, endDate);
        if (vwTipoProdutoDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vwTipoProdutoDtos);
    }
}
