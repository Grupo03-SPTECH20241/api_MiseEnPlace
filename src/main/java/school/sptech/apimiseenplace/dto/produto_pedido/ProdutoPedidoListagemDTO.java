package school.sptech.apimiseenplace.dto.produto_pedido;

import lombok.Data;
import school.sptech.apimiseenplace.dto.pedido.PedidoListagemDTO;
import school.sptech.apimiseenplace.dto.produto.ProdutoListagemDTO;

import java.time.LocalDate;

@Data
public class ProdutoPedidoListagemDTO {

    private Integer idProdutoPedido;
    private int qtProduto;
    private String observacoes;
    private ProdutoDto produtoDto;
    private PersonalizacaoDto personalizacaoDto;
    private PedidoDto pedidoDto;

    @Data
    public static class ProdutoDto {
        private Integer idProduto;
        private String nome;
        private Double preco;
        private String descricao;
        private String foto;
        private int qtdDisponivel;
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

    @Data
    public static class PersonalizacaoDto {
        private Integer idPersonalizacao;
        private String tema;
        private RecheioDto recheio;
        private MassaDto massa;
        private CoberturaDto cobertura;

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
    }

    @Data
    public static class PedidoDto {
        private Integer idPedido;
        private LocalDate dtPedido;
        private Double vlPedido;
        private char status;
        private FormaEntregaDto formaEntregaDto;
        private ClienteDto clienteDto;
        private FormaPagamentoDto formaPagamentoDto;

        @Data
        public static class FormaEntregaDto {
            private Integer idFormaEntrega;
            private String formaEntrega;
        }

        @Data
        public static class ClienteDto {
            private Integer idCliente;
            private String nome;
            private String numero;
            private LocalDate dtAniversario;
        }

        @Data
        public static class FormaPagamentoDto {
            private Integer idFormaPagamento;
            private String formaPagamento;
        }
    }
}
