package school.sptech.apimiseenplace.dto.cliente;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteListagemDto {
    private Integer idCliente;
    private String nome;
    private String numero;
    private LocalDate dtAniversario;
}
