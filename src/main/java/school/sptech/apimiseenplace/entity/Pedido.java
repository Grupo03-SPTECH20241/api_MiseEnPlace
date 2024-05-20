package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dtPedido;
    private Double vlPedido;
    private String status;
    private String valorSinal;

    @ManyToOne
    private FormaEntrega formaEntrega;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido")
    private PedidoFesta pedidoFesta;

    @OneToMany(mappedBy = "pedido")
    private ProdutoPedido produtoPedido;
}
