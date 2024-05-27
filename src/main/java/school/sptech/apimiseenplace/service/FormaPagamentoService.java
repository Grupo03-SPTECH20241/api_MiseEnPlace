package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.formaPagamento.FormaPagamentoCriacaoDto;
import school.sptech.apimiseenplace.dto.formaPagamento.FormaPagamentoMapper;
import school.sptech.apimiseenplace.entity.FormaPagamento;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.FormaPagamentoRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository repository;

    public FormaPagamento criar(FormaPagamentoCriacaoDto dto){
        if(Objects.isNull(dto)) throw new IllegalArgumentException("");
        return repository.save(FormaPagamentoMapper.toEntity(dto));
    }
    public List<FormaPagamento> listar(){
        return repository.findAll();
    }

    public FormaPagamento buscarPorId(int id) {
        return repository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Nenhuma forma de pagamento foi encontrada")
        );
    }
}
