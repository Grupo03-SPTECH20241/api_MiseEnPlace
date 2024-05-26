package school.sptech.apimiseenplace.dto.endereco;

import lombok.Data;

@Data
public class EnderecoCriacaoDto {
    private String logradouro;
    private String complemento;
    private String Cep;
    private int numero;
}
