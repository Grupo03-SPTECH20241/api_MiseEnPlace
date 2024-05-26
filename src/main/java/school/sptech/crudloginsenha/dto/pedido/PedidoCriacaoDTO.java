package school.sptech.crudloginsenha.dto.pedido;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class PedidoCriacaoDTO {
    @Size(min = 3, max = 100)
    private String cliente;
    @NotBlank
    @FutureOrPresent
    private LocalDate dataEntrega;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

}
