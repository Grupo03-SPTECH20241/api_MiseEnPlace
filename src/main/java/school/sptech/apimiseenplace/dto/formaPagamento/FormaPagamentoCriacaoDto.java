package school.sptech.apimiseenplace.dto.formaPagamento;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FormaPagamentoCriacaoDto {

    @NotBlank
    private String formaPagamento;
}
