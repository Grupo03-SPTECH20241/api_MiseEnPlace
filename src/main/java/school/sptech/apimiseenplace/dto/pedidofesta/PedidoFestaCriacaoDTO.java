package school.sptech.apimiseenplace.dto.pedidofesta;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PedidoFestaCriacaoDTO {
    private Integer enderecoId;
    private Integer pedidoId;
}
