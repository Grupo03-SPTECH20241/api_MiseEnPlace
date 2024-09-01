package school.sptech.apimiseenplace.dto.produto_pedido;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class AgendaItemsDTO {
    private String cliente;
    private String pedido;
    private String descricao;
    private Character status;
    private LocalDate dataEntrega;
}
