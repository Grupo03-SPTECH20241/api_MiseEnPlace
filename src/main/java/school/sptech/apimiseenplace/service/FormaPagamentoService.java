package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.entity.FormaPagamento;
import school.sptech.apimiseenplace.repository.FormaPagamentoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository repository;

    public List<FormaPagamento> listar(){
        return repository.findAll();
    }

}
