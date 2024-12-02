package school.sptech.apimiseenplace.service.quantidadeVendidaValorVendido;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.repository.quantidadeVendidaValorVendido.QuantidadeVendidaValorVendidoSemanaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendidoSemana;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class QuantidadeVendidaValorVendidoSemanaService {
    private final QuantidadeVendidaValorVendidoSemanaRepository repository;

    public List<VwQuantidadeVendidaValorVendidoSemana> listar() {
        return repository.findAll();
    }

}
