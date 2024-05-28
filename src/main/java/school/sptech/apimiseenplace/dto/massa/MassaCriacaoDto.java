package school.sptech.apimiseenplace.dto.massa;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MassaCriacaoDto {

    @NotBlank
    private String nome;
}
