package school.sptech.apimiseenplace.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.repository.QuantidadeVendidosTipoProdutoRepository;
import school.sptech.apimiseenplace.view.graficoTipoProduto.VwTipoProduto;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class QuantidadeVendidosTipoProdutoService {
    private final QuantidadeVendidosTipoProdutoRepository repository;


    public List<VwTipoProduto> findAll() {
        return repository.findAll();
    }
}
