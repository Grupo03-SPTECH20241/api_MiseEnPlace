package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.entity.*;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.dto.produto.ProdutoListagemDTO;
import school.sptech.apimiseenplace.dto.produto.ProdutoMapper;
import school.sptech.apimiseenplace.repository.ProdutoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RecheioService recheioService;
    private final MassaService massaService;
    private final CoberturaService coberturaService;
    private final UnidadeMedidaService unidadeMedidaService;
    private final TipoProdutoService tipoProdutoService;

    public Produto cadastrarProduto(
            Produto produto,
            Integer recheioId,
            Integer massaId,
            Integer coberturaId,
            Integer unidadeMedidaId,
            Integer tipoProdutoId
            ){
        if(produto == null) throw new BadRequestException("Produto de Criação");

        Recheio recheio = recheioService.buscarPorId(recheioId);
        Massa massa = massaService.buscarPorId(massaId);
        Cobertura cobertura = coberturaService.buscarPorId(coberturaId);
        UnidadeMedida unidadeMedida = unidadeMedidaService.buscarPorId(unidadeMedidaId);
        TipoProduto tipoProduto = tipoProdutoService.buscarPorId(tipoProdutoId);

        produto.setRecheio(recheio);
        produto.setMassa(massa);
        produto.setCobertura(cobertura);
        produto.setUnidadeMedida(unidadeMedida);
        produto.setTipoProduto(tipoProduto);

        return produtoRepository.save(produto);
    }

    public List<ProdutoListagemDTO> listarProdutos(){
        List<Produto> produtos = produtoRepository.findAll();
        return ProdutoMapper.toListagemDto(produtos);
    }

    public void deletarProdutoPorId(Integer id){
        if(!produtoRepository.existsById(id)){
            throw new BadRequestException("Produto");
        }

        produtoRepository.deleteById(id);
    }

}
