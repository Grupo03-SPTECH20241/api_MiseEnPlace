package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recheio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRecheio;
    private String nome;
    private Double preco;

    @OneToMany(mappedBy = "recheio")
    private Produto produto;

    @OneToMany(mappedBy = "recheio")
    private Personalizacao personalizacao;
}