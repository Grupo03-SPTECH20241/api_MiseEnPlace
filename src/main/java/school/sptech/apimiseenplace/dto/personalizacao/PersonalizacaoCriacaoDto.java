package school.sptech.apimiseenplace.dto.personalizacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonalizacaoCriacaoDto {

    @NotBlank
    private String tema;

    @NotNull
    private Integer idRecheio;

    @NotNull
    private Integer idMassa;

    @NotNull
    private Integer idCobertura;
}
