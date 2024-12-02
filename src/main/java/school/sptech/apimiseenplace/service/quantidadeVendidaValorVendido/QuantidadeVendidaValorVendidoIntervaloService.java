package school.sptech.apimiseenplace.service.quantidadeVendidaValorVendido;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.quantidadeVendidaValorVendidoIntervalo.QuantidadeVendidaValorVendidoIntervaloDto;
import school.sptech.apimiseenplace.repository.quantidadeVendidaValorVendido.QuantidadeVendidaValorVendidoIntervaloRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuantidadeVendidaValorVendidoIntervaloService {
    private final QuantidadeVendidaValorVendidoIntervaloRepository repository;

    public List<QuantidadeVendidaValorVendidoIntervaloDto> findInInterval(LocalDate startDate, LocalDate endDate) {
        return repository.findInInterval(startDate, endDate);
    }
}
