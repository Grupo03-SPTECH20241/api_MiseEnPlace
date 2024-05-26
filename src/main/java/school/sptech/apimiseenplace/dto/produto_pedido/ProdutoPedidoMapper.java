package school.sptech.apimiseenplace.dto.produto_pedido;

import school.sptech.apimiseenplace.dto.festa.FestaMapper;
import school.sptech.apimiseenplace.dto.produto.ProdutoListagemDTO;
import school.sptech.apimiseenplace.entity.ProdutoPedido;

import java.util.List;

public class ProdutoPedidoMapper {
    public static ProdutoPedido toEntity(ProdutoPedidoCriacaoDto produtoPedidoCriacaoDto) {
        ProdutoPedido produtoPedido = new ProdutoPedido();

        produtoPedido.setQtProduto(produtoPedidoCriacaoDto.getQtProduto());
        produtoPedido.setObservacoes(produtoPedidoCriacaoDto.getObservacoes());

        return produtoPedido;
    }

    public static ProdutoPedidoListagemDTO toDto(ProdutoPedido produtoPedido) {
        ProdutoPedidoListagemDTO produtoPedidoListagemDTO = new ProdutoPedidoListagemDTO();

        produtoPedidoListagemDTO.setIdProdutoPedido(produtoPedido.getIdProdutoPedido());
        produtoPedidoListagemDTO.setQtProduto(produtoPedido.getQtProduto());
        produtoPedidoListagemDTO.setObservacoes(produtoPedido.getObservacoes());

        ProdutoPedidoListagemDTO.ProdutoDto produtoDto = new ProdutoPedidoListagemDTO.ProdutoDto();
        produtoDto.setIdProduto(produtoPedido.getProduto().getIdProduto());
        produtoDto.setNome(produtoPedido.getProduto().getNome());
        produtoDto.setPreco(produtoPedido.getProduto().getPreco());
        produtoDto.setDescricao(produtoPedido.getProduto().getDescricao());
        produtoDto.setFoto(produtoPedido.getProduto().getFoto());
        produtoDto.setQtdDisponivel(produtoPedido.getProduto().getQtdDisponivel());
        ProdutoPedidoListagemDTO.ProdutoDto.RecheioDto recheioDto = new ProdutoPedidoListagemDTO.ProdutoDto.RecheioDto();
        recheioDto.setId(produtoPedido.getProduto().getRecheio().getIdRecheio());
        recheioDto.setNome(produtoPedido.getProduto().getRecheio().getNome());
        recheioDto.setPreco(produtoPedido.getProduto().getRecheio().getPreco());
        produtoDto.setRecheio(recheioDto);
        ProdutoPedidoListagemDTO.ProdutoDto.MassaDto massaDto = new ProdutoPedidoListagemDTO.ProdutoDto.MassaDto();
        massaDto.setId(produtoPedido.getProduto().getMassa().getIdMassa());
        massaDto.setNome(produtoPedido.getProduto().getMassa().getNome());
        produtoDto.setMassa(massaDto);
        ProdutoPedidoListagemDTO.ProdutoDto.CoberturaDto coberturaDto = new ProdutoPedidoListagemDTO.ProdutoDto.CoberturaDto();
        coberturaDto.setId(produtoPedido.getProduto().getCobertura().getIdCobertura());
        coberturaDto.setNome(produtoPedido.getProduto().getCobertura().getNome());
        produtoDto.setCobertura(coberturaDto);
        ProdutoPedidoListagemDTO.ProdutoDto.UnidadeMedidaDto unidadeMedidaDto = new ProdutoPedidoListagemDTO.ProdutoDto.UnidadeMedidaDto();
        unidadeMedidaDto.setId(produtoPedido.getProduto().getUnidadeMedida().getIdUnidadeMedida());
        unidadeMedidaDto.setUnidadeMedida(produtoPedido.getProduto().getUnidadeMedida().getUnidadeMedida());
        produtoDto.setUnidadeMedida(unidadeMedidaDto);
        ProdutoPedidoListagemDTO.ProdutoDto.TipoProdutoDto tipoProdutoDto = new ProdutoPedidoListagemDTO.ProdutoDto.TipoProdutoDto();
        tipoProdutoDto.setId(produtoPedido.getProduto().getTipoProduto().getIdTipoProduto());
        tipoProdutoDto.setTipo(produtoPedido.getProduto().getTipoProduto().getTipo());
        produtoDto.setTipoProduto(tipoProdutoDto);
        produtoPedidoListagemDTO.setProdutoDto(produtoDto);

        ProdutoPedidoListagemDTO.PersonalizacaoDto personalizacaoDto = new ProdutoPedidoListagemDTO.PersonalizacaoDto();
        personalizacaoDto.setIdPersonalizacao(produtoPedido.getPersonalizacao().getIdPersonalizacao());
        personalizacaoDto.setTema(produtoPedido.getPersonalizacao().getTema());
        ProdutoPedidoListagemDTO.PersonalizacaoDto.RecheioDto recheioDtoPersonalizacao = new ProdutoPedidoListagemDTO.PersonalizacaoDto.RecheioDto();
        recheioDtoPersonalizacao.setId(produtoPedido.getPersonalizacao().getRecheio().getIdRecheio());
        recheioDtoPersonalizacao.setNome(produtoPedido.getPersonalizacao().getRecheio().getNome());
        recheioDtoPersonalizacao.setPreco(produtoPedido.getPersonalizacao().getRecheio().getPreco());
        personalizacaoDto.setRecheio(recheioDtoPersonalizacao);
        ProdutoPedidoListagemDTO.PersonalizacaoDto.MassaDto massaDtoPersonalizacao = new ProdutoPedidoListagemDTO.PersonalizacaoDto.MassaDto();
        massaDtoPersonalizacao.setId(produtoPedido.getPersonalizacao().getMassa().getIdMassa());
        massaDtoPersonalizacao.setNome(produtoPedido.getPersonalizacao().getMassa().getNome());
        personalizacaoDto.setMassa(massaDtoPersonalizacao);
        ProdutoPedidoListagemDTO.PersonalizacaoDto.CoberturaDto coberturaDtoPersonalizacao = new ProdutoPedidoListagemDTO.PersonalizacaoDto.CoberturaDto();
        coberturaDtoPersonalizacao.setId(produtoPedido.getPersonalizacao().getCobertura().getIdCobertura());
        coberturaDtoPersonalizacao.setNome(produtoPedido.getPersonalizacao().getCobertura().getNome());
        personalizacaoDto.setCobertura(coberturaDtoPersonalizacao);
        produtoPedidoListagemDTO.setPersonalizacaoDto(personalizacaoDto);

        ProdutoPedidoListagemDTO.PedidoDto pedidoDto = new ProdutoPedidoListagemDTO.PedidoDto();
        pedidoDto.setIdPedido(produtoPedido.getPedido().getIdPedido());
        pedidoDto.setDtPedido(produtoPedido.getPedido().getDtPedido());
        pedidoDto.setVlPedido(produtoPedido.getPedido().getVlPedido());
        pedidoDto.setStatus(produtoPedido.getPedido().getStatus());
        ProdutoPedidoListagemDTO.PedidoDto.FormaEntregaDto formaEntregaDto = new ProdutoPedidoListagemDTO.PedidoDto.FormaEntregaDto();
        formaEntregaDto.setIdFormaEntrega(produtoPedido.getPedido().getFormaEntrega().getIdFormaEntrega());
        formaEntregaDto.setFormaEntrega(produtoPedido.getPedido().getFormaEntrega().getFormaEntrega());
        pedidoDto.setFormaEntregaDto(formaEntregaDto);
        ProdutoPedidoListagemDTO.PedidoDto.ClienteDto clienteDto = new ProdutoPedidoListagemDTO.PedidoDto.ClienteDto();
        clienteDto.setIdCliente(produtoPedido.getPedido().getCliente().getIdCliente());
        clienteDto.setNome(produtoPedido.getPedido().getCliente().getNome());
        clienteDto.setNumero(produtoPedido.getPedido().getCliente().getNumero());
        clienteDto.setDtAniversario(produtoPedido.getPedido().getCliente().getDtAniversario());
        pedidoDto.setClienteDto(clienteDto);
        ProdutoPedidoListagemDTO.PedidoDto.FormaPagamentoDto formaPagamentoDto = new ProdutoPedidoListagemDTO.PedidoDto.FormaPagamentoDto();
        formaPagamentoDto.setIdFormaPagamento(produtoPedido.getPedido().getFormaPagamento().getIdFormaPagamento());
        formaPagamentoDto.setFormaPagamento(produtoPedido.getPedido().getFormaPagamento().getFormaPagamento());
        pedidoDto.setFormaPagamentoDto(formaPagamentoDto);
        produtoPedidoListagemDTO.setPedidoDto(pedidoDto);

        return produtoPedidoListagemDTO;
    }

    public static List<ProdutoPedidoListagemDTO> toDto(List<ProdutoPedido> produtoPedidos) {
        if (produtoPedidos == null) return null;
        return produtoPedidos.stream().map(ProdutoPedidoMapper::toDto).toList();
    }
}
