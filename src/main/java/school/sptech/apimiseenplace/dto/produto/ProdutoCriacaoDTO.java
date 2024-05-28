package school.sptech.apimiseenplace.dto.produto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProdutoCriacaoDTO {
    private String nome;
    private Double preco;
    private String descricao;
    private String foto;
    private Integer qtdDisponivel;
    private Integer recheioId;
    private Integer massaId;
    private Integer coberturaId;
    private Integer unidadeMedidaId;
    private Integer tipoProdutoId;
}