package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Endereco {

    public Endereco() {}

    public Endereco(Integer idEndereco, String logradouro, String complemento, String cep, int numero) {
        this.idEndereco = idEndereco;
        this.logradouro = logradouro;
        this.complemento = complemento;
        Cep = cep;
        this.numero = numero;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEndereco;
    private String logradouro;
    private String complemento;
    private String Cep;
    private int numero;

    @OneToMany(mappedBy = "endereco")
    private List<Festa> festas;
}
