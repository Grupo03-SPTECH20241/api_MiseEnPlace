package school.sptech.apimiseenplace.poc.matriz;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.entity.Pedido;
import school.sptech.apimiseenplace.entity.Produto;

import java.util.List;

@RestController
@RequestMapping("/matriz")
@RequiredArgsConstructor
public class MatrizPedidoController {

    private final MatrizPedidoService matrizPedidoService;

    @GetMapping("/{idPedido}")
    public ResponseEntity<String[][]> criarRelatorio(@PathVariable Integer idPedido) {
         List<Produto> produtos = matrizPedidoService.getProdutos(idPedido);
        List<Integer> quantidades = matrizPedidoService.getQuantidades(idPedido);

        if (produtos.isEmpty()) return ResponseEntity.noContent().build();

        String[][] matriz = new String[produtos.size() + 1][3];

        matriz[0][0] = "Produto";
        matriz[0][1] = "Preço";
        matriz[0][2] = "Quantidade";
        for (int linha = 1; linha < produtos.size() + 1; linha++) {
            matriz[linha][0] = produtos.get(linha - 1).getNome();
            matriz[linha][1] = produtos.get(linha - 1).getPreco().toString();
            matriz[linha][2] = quantidades.get(linha - 1).toString();
        }

        System.out.printf("ID do Pedido: %s\n", idPedido);
        System.out.println(matriz);

        return ResponseEntity.ok(matriz);
    }

}
