package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.sql.model.PreparableMutationOperation;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.massa.MassaCriacaoDto;
import school.sptech.apimiseenplace.dto.massa.MassaMapper;
import school.sptech.apimiseenplace.entity.Massa;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.MassaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MassaService {

    private final MassaRepository repository;

    public List<Massa> listar(){return repository.findAll();}

    public Massa encontrarPorId(Integer id){
        return repository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Massa")
        );
    }

    public Massa criar(MassaCriacaoDto dto){
        return repository.save(MassaMapper.toEntity(dto));
    }

    public Massa atualizar(MassaCriacaoDto massa, Integer id){

        if(massa == null) throw new BadRequestException("Massa");

        Massa massaAchada = encontrarPorId(id);

        massaAchada.setIdMassa(id);
        massaAchada.setNome(massa.getNome());

        return repository.save(massaAchada);
    }

    public void deletar(Integer id){
        if(!repository.existsById(id)) throw new BadRequestException("Massa");

        repository.deleteById(id);
    }
}

