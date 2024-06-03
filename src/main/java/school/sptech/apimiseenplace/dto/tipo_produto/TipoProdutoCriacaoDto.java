package school.sptech.apimiseenplace.dto.tipo_produto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TipoProdutoCriacaoDto {
    @NotBlank
    private String tipo;
}
