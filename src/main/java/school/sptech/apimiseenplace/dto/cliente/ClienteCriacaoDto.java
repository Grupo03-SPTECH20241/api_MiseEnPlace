package school.sptech.apimiseenplace.dto.cliente;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteCriacaoDto {
    private String nome;
    private String numero;
    private LocalDate dtAniversario;
}
