package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity

@NamedStoredProcedureQuery(
        name = "Pedido.insercaoPedido",
        procedureName = "pc_insercao_pedido",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "tema", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "fk_recheio", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "fk_massa", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "fk_cobertura", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "observacoes", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "qt_produto", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "fk_produto", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "dt_pedido", type = LocalDate.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "vl_pedido", type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "status", type = Character.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "valor_sinal", type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "fk_forma_entrega", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "fk_cliente", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "fk_forma_pagamento", type = Integer.class)
        }
)
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;
    private LocalDate dtPedido;
    private Double vlPedido;
    private char status;
    private Double valorSinal;

    @ManyToOne
    private FormaEntrega formaEntrega;

    @OneToMany(mappedBy = "pedido")
    private List<ProdutoPedido> produtoPedidos;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    private List<Festa> festas;

    @ManyToOne
    private FormaPagamento formaPagamento;
}
