package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Personalizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPersonalizacao;
    private String tema;

    @ManyToOne
    @JoinColumn(name = "fkRecheio")
    private Recheio recheio;

    @ManyToOne
    @JoinColumn(name = "fkMassa")
    private Massa massa;

    @ManyToOne
    @JoinColumn(name = "fkCobertura")
    private Cobertura cobertura;

    @OneToMany(mappedBy = "personalizacao")
    private List<ProdutoPedido> produtoPedidos;
}
