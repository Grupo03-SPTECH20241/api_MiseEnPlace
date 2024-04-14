package school.sptech.crudloginsenha.dto;

import school.sptech.crudloginsenha.entity.Pedido;
import school.sptech.crudloginsenha.entity.Produto;

import java.util.ArrayList;

public class PedidoMapper {
    public static Pedido toEntity(PedidoCriacaoDTO pedidoCriacaoDTO){
        if (pedidoCriacaoDTO == null) return null;
        Pedido entity = new Pedido();
        entity.setId(null);
        entity.setProdutos(new ArrayList<Produto>());
        entity.setCliente(pedidoCriacaoDTO.getCliente());
        entity.setDataEntrega(pedidoCriacaoDTO.getDataEntrega());
        return entity;
    }

    public static PedidoListagemDTO toDto(Pedido entity){
        if (entity == null) return null;
        PedidoListagemDTO listagemDTO = new PedidoListagemDTO();
        listagemDTO.setId(entity.getId());
        listagemDTO.setCliente(entity.getCliente());
        listagemDTO.setProdutos(entity.getProdutos());
        listagemDTO.setDataEntrega(entity.getDataEntrega());
        listagemDTO.setValorTotal(listagemDTO.getValorTotal());
        return listagemDTO;
    }
}
