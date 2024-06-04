package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.tipo_produto.TipoProdutoCriacaoDto;
import school.sptech.apimiseenplace.entity.*;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.TipoProdutoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoProdutoService {
    private final TipoProdutoRepository tipoProdutoRepository;

    public TipoProduto buscarPorId(Integer id) {
        return tipoProdutoRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Tipo Produto")
        );
    }

    public TipoProduto salvar(TipoProduto tipoProduto){
        if (tipoProduto == null) throw new BadRequestException("Tipo Produto");
        return tipoProdutoRepository.save(tipoProduto);
    }

    public List<TipoProduto> listar() {
        return tipoProdutoRepository.findAll();
    }

    public TipoProduto atualizar(int id, TipoProduto tipoProdutoAtualizado) {
        TipoProduto tipoProduto = buscarPorId(id);

        tipoProduto.setTipo(tipoProdutoAtualizado.getTipo());

        return tipoProdutoRepository.save(tipoProduto);
    }

    public String deletar(Integer idBusca) {
        if (tipoProdutoRepository.existsById(idBusca)) {
            tipoProdutoRepository.deleteById(idBusca);
            return "Tipo Produto deletado com sucesso!";
        }

        throw new BadRequestException("Id Tipo Produto");
    }
}