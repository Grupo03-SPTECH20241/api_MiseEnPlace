package school.sptech.apimiseenplace.controller.quantidadeVendidosTipoProdutoIntervalorController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.dto.vwTipoProduto.quantidadeVendidosTipoProdutoIntervalo.QuantidadeVendidosTipoProdutoIntervaloDto;
import school.sptech.apimiseenplace.service.quantidadeVendidosTipoProduto.QuantidadeVendidosTipoProdutoIntervaloService;
import school.sptech.apimiseenplace.view.graficoTipoProduto.VwTipoProduto;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/quantidade-vendidos-tipo-produto-intervalo")
@RequiredArgsConstructor
public class QuantidadeVendidosTipoProdutoIntervaloController {
    private final QuantidadeVendidosTipoProdutoIntervaloService service;

    @GetMapping
    public ResponseEntity<List<QuantidadeVendidosTipoProdutoIntervaloDto>> listar(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<QuantidadeVendidosTipoProdutoIntervaloDto> vwTipoProdutoDtos = service.findInInterval(startDate, endDate);
        if (vwTipoProdutoDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vwTipoProdutoDtos);
    }
}
