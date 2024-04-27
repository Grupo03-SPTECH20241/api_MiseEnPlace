package school.sptech.crudloginsenha.dto.pedido;

import school.sptech.crudloginsenha.entity.Produto;

import java.time.LocalDate;
import java.util.List;

public class PedidoListagemDTO {
    private int id;
    private String cliente;
    private LocalDate dataEntrega;
    private List<PedidoProdutoListagemDTO> produtos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public List<PedidoProdutoListagemDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<PedidoProdutoListagemDTO> produtos) {
        this.produtos = produtos;
    }
}
