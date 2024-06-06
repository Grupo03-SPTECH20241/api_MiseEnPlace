package school.sptech.apimiseenplace.dto.tipo_produto;

import lombok.Data;
import java.util.List;

@Data
public class TipoProdutoListagemDto {
    private Integer id;
    private String nome;
    private List<ProdutoListagemDto> produtos;

    @Data
    public static class ProdutoListagemDto {
        private Integer idProduto;
        private String nome;
        private Double preco;
        private String descricao;
        private String foto;
        private int qtdDisponivel;
        private RecheioListagemDto recheio;
        private MassaListagemDto massa;
        private CoberturaListagemDto cobertura;
        private UnidadeMedidaListagemDto unidadeMedida;

        @Data
        public static class RecheioListagemDto {
            private Integer id;
            private String nome;
            private Double preco;
        }

        @Data
        public static class MassaListagemDto {
            private Integer id;
            private String nome;
        }

        @Data
        public static class CoberturaListagemDto {
            private Integer id;
            private String nome;
        }

        @Data
        public static class UnidadeMedidaListagemDto {
            private Integer id;
            private String unidadeMedida;
        }
    }
}
