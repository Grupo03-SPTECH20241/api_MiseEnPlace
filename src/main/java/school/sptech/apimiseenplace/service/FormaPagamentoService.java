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

    public FormaPagamento atualizar(int id, FormaPagamentoCriacaoDto dto){
        FormaPagamento formaPagamento = buscarPorId(id);
        formaPagamento.setFormaPagamento(dto.getFormaPagamento());
        return repository.save(formaPagamento);
    }

    public FormaPagamento buscarPorId(int id) {
        return repository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Nenhuma forma de pagamento foi encontrada")
        );
    }

    public void deletar(int id){
        if(!repository.existsById(id)) throw new NaoEncontradoException("Nenhuma forma de pagamento foi encontrada");
        repository.deleteById(id);
    }
  
    public FormaPagamento encontrarPorId(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Produto")
        );
    }
}
