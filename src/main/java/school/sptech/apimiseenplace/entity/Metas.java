package school.sptech.apimiseenplace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Metas {

    public Metas() {}

    public Metas(Integer idMeta, Double valor, LocalDate dtTermino, LocalDate dtInicio) {
        this.idMeta = idMeta;
        this.valor = valor;
        this.dtTermino = dtTermino;
        this.dtInicio = dtInicio;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMeta;
    private Double valor;
    private LocalDate dtTermino;
    private LocalDate dtInicio;
}
