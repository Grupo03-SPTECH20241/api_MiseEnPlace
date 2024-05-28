package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import school.sptech.apimiseenplace.entity.Pedido;

import java.time.LocalDate;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Procedure(name = "Pedido.insercaoPedido")
    void inserirPedido(
            @Param("tema") String tema,
            @Param("fk_recheio") Integer fkRecheio,
            @Param("fk_massa") Integer fkMassa,
            @Param("fk_cobertura") Integer fkCobertura,
            @Param("observacoes") String observacoes,
            @Param("qt_produto") Integer qtProduto,
            @Param("fk_produto") Integer fkProduto,
            @Param("dt_pedido") LocalDate dtPedido,
            @Param("vl_pedido") Double vlPedido,
            @Param("status") Character status,
            @Param("valor_sinal") Double valorSinal,
            @Param("fk_forma_entrega") Integer fkFormaEntrega,
            @Param("fk_cliente") Integer fkCliente,
            @Param("fk_forma_pagamento") Integer fkFormaPagamento
    );
}
