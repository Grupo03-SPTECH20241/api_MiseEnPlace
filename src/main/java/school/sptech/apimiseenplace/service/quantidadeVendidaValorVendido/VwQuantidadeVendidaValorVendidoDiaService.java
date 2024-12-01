package school.sptech.apimiseenplace.service.quantidadeVendidaValorVendido;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.repository.quantidadeVendidaValorVendido.QuantidadeVendidaValorVendidoDiaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendidoDia;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VwQuantidadeVendidaValorVendidoDiaService {
    private final QuantidadeVendidaValorVendidoDiaRepository repository;
    public List<VwQuantidadeVendidaValorVendidoDia> listar() {
        return repository.findAll();
    }
}
