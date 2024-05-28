package school.sptech.apimiseenplace.dto.festa;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FestaListagemDto {
    private Integer id;
    private PedidoDto pedidoDto;
    private EnderecoDto enderecoDto;

    @Data
    public static class PedidoDto {
        private Integer idPedido;
        private LocalDate dtPedido;
        private Double vlPedido;
        private char status;
        private Double valorSinal;
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

    @Data
    public static class EnderecoDto {
        private Integer idEndereco;
        private String logradouro;
        private String complemento;
        private String Cep;
        private int numero;
    }
}
