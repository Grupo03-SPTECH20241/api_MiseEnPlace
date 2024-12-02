package school.sptech.apimiseenplace.service.quantidadeVendidos;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidos.quantidadeVendidosIntervalo.QuantidadeVendidosIntervaloDto;
import school.sptech.apimiseenplace.repository.quantidadeVendidos.QuantidadeVendidosIntervaloRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendido;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuantidadeVendidosIntervaloService {
    private final QuantidadeVendidosIntervaloRepository repository;

    public List<QuantidadeVendidosIntervaloDto> findInInterval(LocalDate startDate, LocalDate endDate) {
        return repository.findInInterval(startDate, endDate);
    }
}
