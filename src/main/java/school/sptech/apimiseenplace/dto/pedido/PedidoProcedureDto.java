package school.sptech.apimiseenplace.dto.pedido;

import lombok.Data;

import java.time.LocalDate;

public class PedidoProcedureDto {
    private String tema;
    private Integer fkRecheio;
    private Integer fkMassa;
    private Integer fkCobertura;
    private String observacoes;
    private Integer qtProduto;
    private Integer fkProduto;
    private LocalDate dtPedido;
    private Double vlPedido;
    private Character status;
    private Double valorSinal;
    private Integer fkFormaEntrega;
    private Integer fkCliente;
    private Integer fkFormaPagamento;

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Integer getFkRecheio() {
        return fkRecheio;
    }

    public void setFkRecheio(Integer fkRecheio) {
        this.fkRecheio = fkRecheio;
    }

    public Integer getFkMassa() {
        return fkMassa;
    }

    public void setFkMassa(Integer fkMassa) {
        this.fkMassa = fkMassa;
    }

    public Integer getFkCobertura() {
        return fkCobertura;
    }

    public void setFkCobertura(Integer fkCobertura) {
        this.fkCobertura = fkCobertura;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getQtProduto() {
        return qtProduto;
    }

    public void setQtProduto(Integer qtProduto) {
        this.qtProduto = qtProduto;
    }

    public Integer getFkProduto() {
        return fkProduto;
    }

    public void setFkProduto(Integer fkProduto) {
        this.fkProduto = fkProduto;
    }

    public LocalDate getDtPedido() {
        return dtPedido;
    }

    public void setDtPedido(LocalDate dtPedido) {
        this.dtPedido = dtPedido;
    }

    public Double getVlPedido() {
        return vlPedido;
    }

    public void setVlPedido(Double vlPedido) {
        this.vlPedido = vlPedido;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Double getValorSinal() {
        return valorSinal;
    }

    public void setValorSinal(Double valorSinal) {
        this.valorSinal = valorSinal;
    }

    public Integer getFkFormaEntrega() {
        return fkFormaEntrega;
    }

    public void setFkFormaEntrega(Integer fkFormaEntrega) {
        this.fkFormaEntrega = fkFormaEntrega;
    }

    public Integer getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Integer fkCliente) {
        this.fkCliente = fkCliente;
    }

    public Integer getFkFormaPagamento() {
        return fkFormaPagamento;
    }

    public void setFkFormaPagamento(Integer fkFormaPagamento) {
        this.fkFormaPagamento = fkFormaPagamento;
    }
}
