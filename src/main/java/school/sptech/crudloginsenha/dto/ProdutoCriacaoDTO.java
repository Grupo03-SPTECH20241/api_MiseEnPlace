package school.sptech.crudloginsenha.dto;

import jakarta.validation.constraints.*;

public class ProdutoCriacaoDTO {
    @Size(min = 3, max = 100)
    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private double preco;
    @NotNull
    @PositiveOrZero
    private int quantidadeEstoque;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

}
