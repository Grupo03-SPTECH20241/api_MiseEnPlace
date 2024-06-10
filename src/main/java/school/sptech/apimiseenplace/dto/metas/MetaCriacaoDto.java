package school.sptech.apimiseenplace.dto.metas;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class MetaCriacaoDto {
    @NotNull    
    private Double valor;
    @NotNull
    @FutureOrPresent
    private LocalDate dtTermino;

}
