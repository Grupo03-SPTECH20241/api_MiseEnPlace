package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.entity.TipoProduto;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.TipoProdutoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoProdutoService {
    private final TipoProdutoRepository tipoProdutoRepository;

    public TipoProduto buscarPorId(Integer id) {
        return tipoProdutoRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Tipo de Produto")
        );
    }

    public TipoProduto salvar(TipoProduto p){
        return null;
    }

    public List<TipoProduto> listar() {
        return new ArrayList<>();
    }

    public TipoProduto atualizar(TipoProduto tipoProdutoAtualizado) {
        return null;
    }

    public String deletar(Integer idBusca) {
    return "";
    }
}