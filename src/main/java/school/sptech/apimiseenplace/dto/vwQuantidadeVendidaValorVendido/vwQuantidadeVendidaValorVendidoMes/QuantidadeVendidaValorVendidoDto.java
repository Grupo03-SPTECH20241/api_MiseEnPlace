package school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidaValorVendidoMes;

import lombok.Data;

@Data
public class QuantidadeVendidaValorVendidoDto {
    private String nome;
    private Integer mes;
    private Integer quantidadeVendida;
    private Double valorVendido;

}
