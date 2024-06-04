package school.sptech.apimiseenplace.dto.formaPagamento;

import lombok.Data;
import org.springframework.cglib.core.Local;
import school.sptech.apimiseenplace.entity.Pedido;

import java.time.LocalDate;
import java.util.List;

@Data
public class FormaPagamentoListagemDto {
    private Integer idFormaPagamento;
    private String formaPagamento;

}
