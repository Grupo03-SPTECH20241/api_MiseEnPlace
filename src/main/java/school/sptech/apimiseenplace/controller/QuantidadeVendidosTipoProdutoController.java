package school.sptech.apimiseenplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.service.QuantidadeVendidosTipoProdutoService;
import school.sptech.apimiseenplace.view.graficoTipoProduto.VwTipoProduto;

import java.util.List;

@RestController
@RequestMapping("/quantidade-vendidos-tipo-produto")
@RequiredArgsConstructor
public class QuantidadeVendidosTipoProdutoController {

        private final QuantidadeVendidosTipoProdutoService service;

        @GetMapping
        public ResponseEntity<List<VwTipoProduto>> listar() {
            List<VwTipoProduto> vwTipoProdutoDtos = service.findAll();
            if (vwTipoProdutoDtos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(vwTipoProdutoDtos);
        }
}
