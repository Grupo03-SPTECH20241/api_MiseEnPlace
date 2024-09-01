package school.sptech.apimiseenplace.dto.pedido;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PedidoListagemDTO {
    private Integer idPedido;
    private LocalDate dtPedido;
    private Double vlPedido;
    private char status;
    private Double valorSinal;
    private FormaEntregaDto formaEntregaDto;
    private ClienteDto clienteDto;
    private FormaPagamentoDto formaPagamentoDto;
    private LocalDate dtEntrega;

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
