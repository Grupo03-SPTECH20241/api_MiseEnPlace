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

    public Cliente(Integer idCliente, String nome, String numero, LocalDate dtAniversario) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.numero = numero;
        this.dtAniversario = dtAniversario;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;
    private String nome;
    private String numero;
    private LocalDate dtAniversario;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedido;

}
