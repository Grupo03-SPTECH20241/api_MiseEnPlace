package school.sptech.apimiseenplace.dto.pedido;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class PedidoListagemDTO {
    private LocalDate dtPedido;
    private Double vlPedido;
    private String status;
    private Double valorSinal;
    private
}
