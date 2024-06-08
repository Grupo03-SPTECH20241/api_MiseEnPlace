package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Festa {

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
