package school.sptech.apimiseenplace.dto.produto;

import lombok.Data;

@Data
public class ProdutoListagemDTO {
    private Integer id;
    private String nome;
    private Double preco;
    private String descricao;
    private String foto;
    private Integer qtdDisponivel;
    private RecheioDto recheio;
    private MassaDto massa;
    private CoberturaDto cobertura;
    private UnidadeMedidaDto unidadeMedida;
    private TipoProdutoDto tipoProduto;

    @Data
    public static class RecheioDto {
        private Integer id;
        private String nome;
        private Double preco;
    }

    @Data
    public static class MassaDto {
        private Integer id;
        private String nome;
    }

    @Data
    public static class CoberturaDto {
        private Integer id;
        private String nome;
    }

    @Data
    public static class UnidadeMedidaDto {
        private Integer id;
        private String unidadeMedida;
    }

    @Data
    public static class TipoProdutoDto {
        private Integer id;
        private String tipo;
    }
}
