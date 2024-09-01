package school.sptech.apimiseenplace.dto.produto_pedido;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ListagemAgenda {
    public String title;
    public List<AgendaItemsDTO> items = new ArrayList<>();
}
