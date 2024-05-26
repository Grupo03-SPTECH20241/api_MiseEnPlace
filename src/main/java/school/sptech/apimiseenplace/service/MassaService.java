package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.entity.Massa;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.MassaRepository;

@Service
@RequiredArgsConstructor
public class MassaService {
    private final MassaRepository massaRepository;

    public Massa buscarPorId(Integer id) {
        return massaRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Massa")
        );
    }
}