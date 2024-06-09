package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.repository.QuantidadeVendidaValorVendidoRepository;

import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendido;


import java.util.List;

@Service
@RequiredArgsConstructor

public class QuantidadeVendidaValorVendidoService {
    private final QuantidadeVendidaValorVendidoRepository repository;

    public List<VwQuantidadeVendidaValorVendido> findAll() {
        return repository.findAll();
    }
}
