package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.vw_quantidade_vendida_mes.QuantidadeVendidosListagemDto;
import school.sptech.apimiseenplace.dto.vw_quantidade_vendida_mes.QuantidadeVendidosMapper;
import school.sptech.apimiseenplace.repository.QuantidadeVendidosRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos.VwQuantidadeVendidosMes;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuantidadeVendidosMesService {
    private final QuantidadeVendidosRepository quantidadeVendidosRepository;

    public List<QuantidadeVendidosListagemDto> listar() {
        List<VwQuantidadeVendidosMes> quantidadeVendidos = quantidadeVendidosRepository.findAll();
        return QuantidadeVendidosMapper.toDto(quantidadeVendidos);
    }
}
