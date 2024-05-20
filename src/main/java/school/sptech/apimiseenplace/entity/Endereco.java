package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String logradouro;
    private String complemento;
    private String cep;
    private Integer numero;

    @OneToMany(mappedBy = "endereco")
    private PedidoFesta pedidoFesta;
}
