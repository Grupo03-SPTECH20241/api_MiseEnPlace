package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.metas.MetaCriacaoDto;
import school.sptech.apimiseenplace.dto.metas.MetaMapper;
import school.sptech.apimiseenplace.entity.Metas;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.repository.MetaRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MetaService {
    private final MetaRepository repository;
    public Metas criarMeta(MetaCriacaoDto metaCriacaoDto){
        if (Objects.isNull(metaCriacaoDto)) throw new BadRequestException(" ");
        return repository.save(MetaMapper.toEntity(metaCriacaoDto));
    }

    public List<Metas> listarMetas(){
        return repository.findAll();
    }
}
