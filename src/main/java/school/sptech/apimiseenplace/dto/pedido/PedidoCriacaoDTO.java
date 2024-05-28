package school.sptech.apimiseenplace.dto.pedido;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PedidoCriacaoDTO {
    private LocalDate dtPedido;
    private Double vlPedido;
    private char status;
    private Double valorSinal;
    private Integer formaEntregaId;
    private Integer clienteId;
    private Integer formaPagamentoId;
}
