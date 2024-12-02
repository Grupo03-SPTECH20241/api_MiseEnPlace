package school.sptech.apimiseenplace.service.quantidadeVendidosTipoProduto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.vwTipoProduto.quantidadeVendidosTipoProdutoIntervalo.QuantidadeVendidosTipoProdutoIntervaloDto;
import school.sptech.apimiseenplace.repository.quantidadeVendidosTipoProduto.QuantidadeVendidosTipoProdutoIntervaloRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendido;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuantidadeVendidosTipoProdutoIntervaloService {
    private final QuantidadeVendidosTipoProdutoIntervaloRepository repository;

    public List<QuantidadeVendidosTipoProdutoIntervaloDto> findInInterval(LocalDate startDate, LocalDate endDate) {
        return repository.findInInterval(startDate, endDate);
    }
}
