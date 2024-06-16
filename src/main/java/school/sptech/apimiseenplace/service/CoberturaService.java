package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.cobertura.CoberturaCriacaoDto;
import school.sptech.apimiseenplace.dto.cobertura.CoberturaMapper;
import school.sptech.apimiseenplace.entity.Cobertura;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.CoberturaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoberturaService {

    private final CoberturaRepository repository;

    public List<Cobertura> listar(){
        return repository.findAll();
    }

    public Cobertura encontrarPorId(Integer id){
        return repository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Cobertura")
        );
    }

    public Cobertura criar(Cobertura cobertura){
        if (cobertura == null) {
            throw new BadRequestException("Cobertura");
        }
        return repository.save(cobertura);
    }

    public Cobertura atualizar(Integer id, Cobertura cobertura){

        if (cobertura == null) throw new BadRequestException("Cobertura");

        Cobertura coberturaAchada = encontrarPorId(id);

        coberturaAchada.setIdCobertura(id);
        coberturaAchada.setNome(cobertura.getNome());

        return repository.save(coberturaAchada);
    }

    public String deletar(Integer id){
        if (!repository.existsById(id)) throw new BadRequestException("Cobertura");

        repository.deleteById(id);
        return "Cobertura deletado com sucesso!";
    }
}
