package school.sptech.apimiseenplace.dto.unidadeMedida;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UnidadeMedidaCriacaoDto {
    @NotBlank
    private String unidadeMedida;
}
