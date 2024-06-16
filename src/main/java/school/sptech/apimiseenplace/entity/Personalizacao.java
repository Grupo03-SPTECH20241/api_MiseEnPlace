package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Personalizacao {

    public Personalizacao() {}

    public Personalizacao(Integer idPersonalizacao, String tema, Recheio recheio, Massa massa, Cobertura cobertura) {
        this.idPersonalizacao = idPersonalizacao;
        this.tema = tema;
        this.recheio = recheio;
        this.massa = massa;
        this.cobertura = cobertura;
    }

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
