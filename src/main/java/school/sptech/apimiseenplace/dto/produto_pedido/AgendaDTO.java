package school.sptech.apimiseenplace.dto.produto_pedido;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class AgendaDTO {
    public List<ListagemAgenda> itemsAgenda = new ArrayList<>();
}
