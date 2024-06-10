package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.entity.FormaPagamento;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.FormaPagamentoRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository repository;

    public FormaPagamento criar(FormaPagamento dto){
        if(Objects.isNull(dto)) throw new BadRequestException("Forma de Pagamento");
        return repository.save(dto);
    }
    public List<FormaPagamento> listar(){
        return repository.findAll();
    }

    public FormaPagamento atualizar(int id, FormaPagamento dto){
        FormaPagamento formaPagamento = buscarPorId(id);
        formaPagamento.setFormaPagamento(dto.getFormaPagamento());
        return repository.save(formaPagamento);
    }

    public FormaPagamento buscarPorId(int id) {
        return repository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Forma de Pagamento")
        );
    }

    public String deletar(int id){
        if(!repository.existsById(id)) throw new NaoEncontradoException("Forma de Pagamento");
        repository.deleteById(id);
        return "Forma de Pagamento deletada com sucesso!";
    }
  
    public FormaPagamento encontrarPorId(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Forma de Pagamento")
        );
    }
}
