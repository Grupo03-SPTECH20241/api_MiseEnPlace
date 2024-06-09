package school.sptech.apimiseenplace.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.repository.QuantidadeVendidaValorVendidoDiaRepository;
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
