package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Cliente {

    public Cliente() {}

    public Cliente(Integer idCliente, String nome, String numero) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.numero = numero;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;
    private String nome;
    private String numero;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedido;

}
