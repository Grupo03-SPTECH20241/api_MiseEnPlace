package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.entity.Cobertura;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.CoberturaRepository;

@Service
@RequiredArgsConstructor
public class CoberturaService {
    private final CoberturaRepository coberturaRepository;

    public Cobertura buscarPorId(Integer id) {
        return coberturaRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Cobertura")
        );
    }
}