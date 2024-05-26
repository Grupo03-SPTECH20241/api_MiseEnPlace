package school.sptech.crudloginsenha.dto.produto;

import school.sptech.crudloginsenha.dto.produto.ProdutoCriacaoDTO;
import school.sptech.crudloginsenha.entity.Pedido;
import school.sptech.crudloginsenha.entity.Produto;

import java.util.List;

public class ProdutoMapper {
    public static Produto toEntity(ProdutoCriacaoDTO produtoCriacaoDTO, Pedido pedido){
        if (produtoCriacaoDTO == null) return null;
        Produto entity = new Produto();

        entity.setId(null);
        entity.setNome(produtoCriacaoDTO.getNome());
        entity.setPreco(produtoCriacaoDTO.getPreco());
        entity.setQuantidadeEstoque(produtoCriacaoDTO.getQuantidadeEstoque());
        entity.setPedido(pedido);
        return entity;
    }

    public static Produto toEntity(ProdutoCriacaoDTO produtoCriacaoDTO){
        if (produtoCriacaoDTO == null) return null;
        Produto entity = new Produto();

        entity.setId(null);
        entity.setNome(produtoCriacaoDTO.getNome());
        entity.setPreco(produtoCriacaoDTO.getPreco());
        entity.setQuantidadeEstoque(produtoCriacaoDTO.getQuantidadeEstoque());
        entity.setPedido(null);
        return entity;
    }

    public static ProdutoListagemDTO toListagemDto(Produto produto){
        if (produto == null) return null;

        ProdutoListagemDTO produtoListagemDTO = new ProdutoListagemDTO();
        produtoListagemDTO.setId(produto.getId());
        produtoListagemDTO.setNome(produto.getNome());
        produtoListagemDTO.setPreco(produto.getPreco());
        produtoListagemDTO.setQuantidadeEstoque(produto.getQuantidadeEstoque());

        ProdutoPedidoListagemDTO pedidoListagemDTO = new ProdutoPedidoListagemDTO();
        pedidoListagemDTO.setId(produto.getPedido().getId());
        pedidoListagemDTO.setCliente(produto.getPedido().getCliente());
        pedidoListagemDTO.setDataEntrega(produto.getPedido().getDataEntrega());

        produtoListagemDTO.setPedido(pedidoListagemDTO);
        return produtoListagemDTO;
    }

    public static List<ProdutoListagemDTO> toListagemDto(List<Produto> produtos){
        if (produtos == null) return null;
        return produtos.stream().map(ProdutoMapper::toListagemDto).toList();
    }
}
