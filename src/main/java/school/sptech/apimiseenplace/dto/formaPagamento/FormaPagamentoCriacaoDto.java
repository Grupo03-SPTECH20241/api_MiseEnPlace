package school.sptech.apimiseenplace.dto.formaPagamento;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FormaPagamentoCriacaoDto {

    @NotBlank
    private String formaPagamento;
}
