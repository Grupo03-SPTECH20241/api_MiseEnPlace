package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.pedido.PedidoCriacaoDTO;
import school.sptech.apimiseenplace.dto.pedido.PedidoMapper;
import school.sptech.apimiseenplace.dto.pedido.PedidoProcedureDto;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.entity.Pedido;
import school.sptech.apimiseenplace.repository.PedidoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    public void inserirPedido(PedidoProcedureDto pedidoProcedureDto) {
        pedidoRepository.inserirPedido(
                pedidoProcedureDto.getTema(), pedidoProcedureDto.getFkRecheio(), pedidoProcedureDto.getFkMassa(), pedidoProcedureDto.getFkCobertura(), pedidoProcedureDto.getObservacoes(), pedidoProcedureDto.getQtProduto(),
                pedidoProcedureDto.getFkProduto(),pedidoProcedureDto.getDtPedido(),pedidoProcedureDto.getVlPedido(),pedidoProcedureDto.getStatus(),pedidoProcedureDto.getValorSinal(),
                pedidoProcedureDto.getFkFormaEntrega(),pedidoProcedureDto.getFkCliente(),pedidoProcedureDto.getFkFormaPagamento()
        );

    }
}
