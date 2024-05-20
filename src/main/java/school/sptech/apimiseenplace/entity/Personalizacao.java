package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Personalizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tema;

    @ManyToOne
    private Recheio recheio;

    @ManyToOne
    private Massa massa;

    @ManyToOne
    private Cobertura cobertura;

    @OneToMany(mappedBy = "personalizacao")
    private ProdutoPedido produtoPedido;
}
