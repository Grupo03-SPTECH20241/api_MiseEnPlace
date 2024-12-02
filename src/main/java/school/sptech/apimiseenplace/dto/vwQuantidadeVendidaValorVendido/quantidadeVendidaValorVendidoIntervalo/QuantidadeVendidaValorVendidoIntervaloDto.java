package school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.quantidadeVendidaValorVendidoIntervalo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuantidadeVendidaValorVendidoIntervaloDto {
    private String nome;
    private Long quantidadeVendida;
    private Double valorVendido;

    public QuantidadeVendidaValorVendidoIntervaloDto(String nome, Long quantidadeVendida, Double valorVendido) {
        this.nome = nome;
        this.quantidadeVendida = quantidadeVendida;
        this.valorVendido = valorVendido;
    }
}
