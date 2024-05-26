package school.sptech.apimiseenplace.dto.endereco;

import lombok.Data;

@Data
public class EnderecoListagemDto {
    private Integer idEndereco;
    private String logradouro;
    private String complemento;
    private String Cep;
    private int numero;
}
