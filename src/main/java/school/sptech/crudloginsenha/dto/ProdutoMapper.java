package school.sptech.crudloginsenha.dto;

import school.sptech.crudloginsenha.entity.Produto;

public class ProdutoMapper {
    public static Produto toEntity(ProdutoCriacaoDTO produtoCriacaoDTO){
        if (produtoCriacaoDTO == null) return null;
        Produto entity = new Produto();

        entity.setId(null);
        entity.setNome(produtoCriacaoDTO.getNome());
        entity.setPreco(produtoCriacaoDTO.getPreco());
        entity.setQuantidadeEstoque(produtoCriacaoDTO.getQuantidadeEstoque());
        return entity;
    }
}
