package school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Entity
@Immutable
@Data
public class VwQuantidadeVendidaValorVendidoDia {
    @Id
    @Column(name = "nome")
    private String nome;

    @Column(name = "dia")
    private LocalDate dia;
    @Column(name = "quantidade vendida")
    private Integer quantidadeVendida;
    @Column(name = "Valor Vendido")
    private Double valorVendido;

    public VwQuantidadeVendidaValorVendidoDia() {
    }

    public VwQuantidadeVendidaValorVendidoDia(String nome, LocalDate dia, Integer quantidadeVendida, Double valorVendido) {
        this.nome = nome;
        this.dia = dia;
        this.quantidadeVendida = quantidadeVendida;
        this.valorVendido = valorVendido;
    }
}
