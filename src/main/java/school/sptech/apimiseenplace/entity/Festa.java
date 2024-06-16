package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Festa {

    public Festa() {}

    public Festa(Integer idFesta, Pedido pedido, Endereco endereco) {
        this.idFesta = idFesta;
        this.pedido = pedido;
        this.endereco = endereco;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFesta;


    @ManyToOne
    @JoinColumn(name = "fkPedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "fkEndereco")
    private Endereco endereco;
}
