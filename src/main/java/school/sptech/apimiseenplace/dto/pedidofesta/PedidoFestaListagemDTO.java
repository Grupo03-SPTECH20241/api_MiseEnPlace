package school.sptech.apimiseenplace.dto.pedidofesta;

import lombok.Data;
import school.sptech.apimiseenplace.entity.CEP;
import school.sptech.apimiseenplace.entity.Endereco;
import school.sptech.apimiseenplace.entity.Produto;

import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoFestaListagemDTO {
    private Integer id;
    private Endereco endereco;

    @Data
    public static class Endereco {
        private Integer id;
        private String logradouro;
        private String complemento;
        private String cep;
        private Integer numero;
    }
}
