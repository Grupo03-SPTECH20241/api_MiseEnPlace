package school.sptech.crudloginsenha.dto;

import school.sptech.crudloginsenha.entity.Produto;

import java.time.LocalDate;
import java.util.List;

public class PedidoListagemDTO {
    private int id;
    private String cliente;
    private List<Produto> produtos;
    private LocalDate dataEntrega;
    private double valorTotal;

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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public double getValorTotal() {
        double valorTotalDoPedido = 0.0;

        for (Produto itemAtualDoPedido : produtos){
            valorTotalDoPedido = itemAtualDoPedido.getPreco();
        }
        return valorTotalDoPedido;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

}
