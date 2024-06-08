package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidosDia.QuantidadeVendidoDiaMapper;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidosDia.QuantidadeVendidosDiaDto;
import school.sptech.apimiseenplace.repository.QuantidadeVendidosDiaRepository;
import school.sptech.apimiseenplace.view.VwQuantidadeVendidosDia;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuantidadeVendidosDiaService {
    private final QuantidadeVendidosDiaRepository quantidadeVendidosDiaRepository;

    public List<QuantidadeVendidosDiaDto> listar() {
        List<VwQuantidadeVendidosDia> quantidadeVendidosDia = quantidadeVendidosDiaRepository.findAll();
        return QuantidadeVendidoDiaMapper.toDto(quantidadeVendidosDia);
    }
}
