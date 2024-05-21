package school.sptech.apimiseenplace.dto.produto;

import school.sptech.apimiseenplace.entity.Pedido;
import school.sptech.apimiseenplace.entity.Produto;

import java.util.List;

public class ProdutoMapper {
    public static Produto toEntity(ProdutoCriacaoDTO produtoCriacaoDTO){
        if (produtoCriacaoDTO == null) return null;
        Produto entity = new Produto();

        entity.setNome(produtoCriacaoDTO.getNome());
        entity.setPreco(produtoCriacaoDTO.getPreco());
        entity.setDescricao(produtoCriacaoDTO.getDescricao());
        entity.setFoto(produtoCriacaoDTO.getFoto());
        entity.setQtdDisponivel(produtoCriacaoDTO.getQtdDisponivel());

        return entity;
    }

    public static ProdutoListagemDTO toListagemDto(Produto produto){
        if (produto == null) return null;

        ProdutoListagemDTO produtoListagemDTO = new ProdutoListagemDTO();

        produtoListagemDTO.setId(produto.getId());
        produtoListagemDTO.setNome(produto.getNome());
        produtoListagemDTO.setPreco(produto.getPreco());
        produtoListagemDTO.setDescricao(produto.getDescricao());
        produtoListagemDTO.setFoto(produto.getFoto());
        produtoListagemDTO.setQtdDisponivel(produto.getQtdDisponivel());

        ProdutoListagemDTO.RecheioDto recheioDto = new ProdutoListagemDTO.RecheioDto();
        recheioDto.setId(produto.getRecheio().getIdRecheio());
        recheioDto.setNome(produto.getRecheio().getNome());
        recheioDto.setPreco(produto.getRecheio().getPreco());
        produtoListagemDTO.setRecheio(recheioDto);

        ProdutoListagemDTO.MassaDto massaDto = new ProdutoListagemDTO.MassaDto();
        massaDto.setId(produto.getMassa().getId());
        massaDto.setNome(produto.getMassa().getNome());
        produtoListagemDTO.setMassa(massaDto);

        ProdutoListagemDTO.CoberturaDto coberturaDto = new ProdutoListagemDTO.CoberturaDto();
        coberturaDto.setId(produto.getCobertura().getId());
        coberturaDto.setNome(produto.getCobertura().getNome());
        produtoListagemDTO.setCobertura(coberturaDto);

        ProdutoListagemDTO.UnidadeMedidaDto unidadeMedidaDto = new ProdutoListagemDTO.UnidadeMedidaDto();
        unidadeMedidaDto.setId(produto.getUnidadeMedida().getId());
        unidadeMedidaDto.setUnidadeMedida(produto.getUnidadeMedida().getUnidadeMedida());
        produtoListagemDTO.setUnidadeMedida(unidadeMedidaDto);

        ProdutoListagemDTO.TipoProdutoDto tipoProdutoDto = new ProdutoListagemDTO.TipoProdutoDto();
        tipoProdutoDto.setId(produto.getTipoProduto().getId());
        tipoProdutoDto.setTipo(produto.getTipoProduto().getTipo());
        produtoListagemDTO.setTipoProduto(tipoProdutoDto);

        return produtoListagemDTO;
    }

    public static List<ProdutoListagemDTO> toListagemDto(List<Produto> produtos){
        if (produtos == null) return null;
        return produtos.stream().map(ProdutoMapper::toListagemDto).toList();
    }
}
