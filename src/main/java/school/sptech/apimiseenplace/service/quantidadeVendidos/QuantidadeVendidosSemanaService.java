package school.sptech.apimiseenplace.service.quantidadeVendidos;

import lombok.Data;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.repository.quantidadeVendidos.QuantidadeVendidosSemanaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos.VwQuantidadeVendidosSemana;

import java.util.List;

@Service
@Data
public class QuantidadeVendidosSemanaService {
    private final QuantidadeVendidosSemanaRepository repository;
    public List<VwQuantidadeVendidosSemana> listar() {
        return repository.findAll();
    }
}
