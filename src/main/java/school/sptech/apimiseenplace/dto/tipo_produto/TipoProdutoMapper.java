package school.sptech.apimiseenplace.dto.tipo_produto;

import school.sptech.apimiseenplace.entity.Produto;
import school.sptech.apimiseenplace.entity.ProdutoPedido;
import school.sptech.apimiseenplace.entity.TipoProduto;

import java.util.ArrayList;
import java.util.List;

public class TipoProdutoMapper {
    public static TipoProduto toEntity(TipoProdutoCriacaoDto tipoProdutoCriacaoDto) {
        if (tipoProdutoCriacaoDto == null) return null;

        TipoProduto tipoProduto = new TipoProduto();
        tipoProduto.setTipo(tipoProdutoCriacaoDto.getTipo());

        return tipoProduto;
    }

    public static List<TipoProdutoListagemDto.ProdutoListagemDto> toProdutoListagemDto(List<Produto> produtos) {
        if (produtos == null) return null;

        List<TipoProdutoListagemDto.ProdutoListagemDto> produtosListagemDto = new ArrayList<>();

        for (Produto produtoDaVez : produtos) {
            TipoProdutoListagemDto.ProdutoListagemDto produtoDto = new TipoProdutoListagemDto.ProdutoListagemDto();

            produtoDto.setIdProduto(produtoDaVez.getIdProduto());
            produtoDto.setNome(produtoDaVez.getNome());
            produtoDto.setPreco(produtoDaVez.getPreco());
            produtoDto.setDescricao(produtoDaVez.getDescricao());
            produtoDto.setFoto(produtoDaVez.getFoto());
            produtoDto.setQtdDisponivel(produtoDaVez.getQtdDisponivel());

            TipoProdutoListagemDto.ProdutoListagemDto.RecheioListagemDto recheioListagemDto = new TipoProdutoListagemDto.ProdutoListagemDto.RecheioListagemDto();
            recheioListagemDto.setId(produtoDaVez.getRecheio().getIdRecheio());
            recheioListagemDto.setNome(produtoDaVez.getRecheio().getNome());
            recheioListagemDto.setPreco(produtoDaVez.getRecheio().getPreco());
            produtoDto.setRecheio(recheioListagemDto);

            TipoProdutoListagemDto.ProdutoListagemDto.MassaListagemDto massaListagemDto = new TipoProdutoListagemDto.ProdutoListagemDto.MassaListagemDto();
            massaListagemDto.setId(produtoDaVez.getMassa().getIdMassa());
            massaListagemDto.setNome(produtoDaVez.getMassa().getNome());
            produtoDto.setMassa(massaListagemDto);

            TipoProdutoListagemDto.ProdutoListagemDto.CoberturaListagemDto coberturaListagemDto = new TipoProdutoListagemDto.ProdutoListagemDto.CoberturaListagemDto();
            coberturaListagemDto.setId(produtoDaVez.getCobertura().getIdCobertura());
            coberturaListagemDto.setNome(produtoDaVez.getCobertura().getNome());
            produtoDto.setCobertura(coberturaListagemDto);

            TipoProdutoListagemDto.ProdutoListagemDto.UnidadeMedidaListagemDto unidadeMedidaListagemDto = new TipoProdutoListagemDto.ProdutoListagemDto.UnidadeMedidaListagemDto();
            unidadeMedidaListagemDto.setId(produtoDaVez.getUnidadeMedida().getIdUnidadeMedida());
            unidadeMedidaListagemDto.setUnidadeMedida(produtoDaVez.getUnidadeMedida().getUnidadeMedida());
            produtoDto.setUnidadeMedida(unidadeMedidaListagemDto);

            produtosListagemDto.add(produtoDto);
        }

        return produtosListagemDto;
    }

    public static TipoProdutoListagemDto toDto(TipoProduto tipoProduto) {
        if (tipoProduto == null) return null;

        TipoProdutoListagemDto tipoProdutoListagemDto = new TipoProdutoListagemDto();

        tipoProdutoListagemDto.setId(tipoProduto.getIdTipoProduto());
        tipoProdutoListagemDto.setNome(tipoProduto.getTipo());

        tipoProdutoListagemDto.setProdutos(toProdutoListagemDto(tipoProduto.getProdutos()));

        return tipoProdutoListagemDto;
    }

    public static List<TipoProdutoListagemDto> toDto (List<TipoProduto> tipoProdutos) {
        return tipoProdutos.stream().map(TipoProdutoMapper::toDto).toList();
    }
}
