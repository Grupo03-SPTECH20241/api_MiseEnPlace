package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Pedido {
    public Pedido() {}

    public Pedido(Integer idPedido, LocalDate dtPedido, Double vlPedido, char status, Double valorSinal, FormaEntrega formaEntrega, Cliente cliente, FormaPagamento formaPagamento, LocalDate dtEntrega) {
        this.idPedido = idPedido;
        this.dtPedido = dtPedido;
        this.vlPedido = vlPedido;
        this.status = status;
        this.valorSinal = valorSinal;
        this.formaEntrega = formaEntrega;
        this.cliente = cliente;
        this.formaPagamento = formaPagamento;
        this.dtEntrega = dtEntrega;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;
    private LocalDate dtPedido;
    private Double vlPedido;
    private char status;
    private Double valorSinal;
    private LocalDate dtEntrega;

    @ManyToOne
    @JoinColumn(name = "fkFormaEntrega")
    private FormaEntrega formaEntrega;

    @OneToMany(mappedBy = "pedido")
    private List<ProdutoPedido> produtoPedidos;

    @ManyToOne
    @JoinColumn(name = "fkCliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    private List<Festa> festas;

    @ManyToOne
    @JoinColumn(name = "fkFormaPagamento")
    private FormaPagamento formaPagamento;
}
