package school.sptech.apimiseenplace.dto.cobertura;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import school.sptech.apimiseenplace.entity.Cobertura;

@Data
public class CoberturaCriacaoDto {

    @NotBlank
    private String nome;

}
