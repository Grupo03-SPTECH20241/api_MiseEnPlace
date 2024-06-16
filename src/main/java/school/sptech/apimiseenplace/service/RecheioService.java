package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.entity.Recheio;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.RecheioRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RecheioService {

    private final RecheioRepository repository;

    public Recheio cadastrarRecheio(Recheio recheioCriacaoDto){
        if(recheioCriacaoDto == null) throw new BadRequestException("Recheio");
        return repository.save(recheioCriacaoDto);
    }

    public List<Recheio> listarRecheios(){
        return repository.findAll();
    }

    public Recheio encontrarPorId(int id){
        return repository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Recheio")
        );
    }

    public Recheio atualizarRecheio(int id, Recheio recheioCriacaoDto){
        Recheio recheioAchado = encontrarPorId(id);

        recheioAchado.setIdRecheio(id);
        recheioAchado.setNome(recheioCriacaoDto.getNome());
        recheioAchado.setPreco(recheioCriacaoDto.getPreco());

        return repository.save(recheioAchado);
    }

    public String deletarRecheioPorId(int id){
        if(!repository.existsById(id)) throw new NaoEncontradoException("Recheio");

        repository.deleteById(id);
        return "Recheio deletado com sucesso!";
    }

    public void excluirRecheio(Integer idBusca) {
    }
}
