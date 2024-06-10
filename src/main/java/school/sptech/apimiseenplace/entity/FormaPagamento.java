package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFormaPagamento;
    private String formaPagamento;

    @OneToMany(mappedBy = "formaPagamento")
    private List<Pedido> pedidos;

    public FormaPagamento() {
    }

    public FormaPagamento(Integer idFormaPagamento, String formaPagamento, List<Pedido> pedidos) {
        this.idFormaPagamento = idFormaPagamento;
        this.formaPagamento = formaPagamento;
        this.pedidos = pedidos;
    }
}
