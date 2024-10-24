package school.sptech.apimiseenplace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.entity.*;
import school.sptech.apimiseenplace.enums.ELambdaFunction;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.dto.produto.ProdutoListagemDTO;
import school.sptech.apimiseenplace.dto.produto.ProdutoMapper;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.ProdutoRepository;

import java.util.Base64;
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
    private final LambdaService lambdaService;
    public Produto encontrarPorId(Integer id) {
        return produtoRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Produto")
        );
    }

    public Produto cadastrarProduto(
            Produto produto,
            byte[] foto,
            Integer recheioId,
            Integer massaId,
            Integer coberturaId,
            Integer unidadeMedidaId,
            Integer tipoProdutoId
    ) throws JsonProcessingException {
        if(produto == null) throw new BadRequestException("Produto");



        Recheio recheio = recheioService.encontrarPorId(recheioId);
        Massa massa = massaService.encontrarPorId(massaId);
        Cobertura cobertura = coberturaService.encontrarPorId(coberturaId);
        UnidadeMedida unidadeMedida = unidadeMedidaService.buscarPorId(unidadeMedidaId);
        TipoProduto tipoProduto = tipoProdutoService.buscarPorId(tipoProdutoId);


        var arquivo = Base64.getEncoder().encodeToString(foto);
        var nomeArquivo = produto.getNome().replace(" ", "_");
        ObjectMapper objectMapper = new ObjectMapper();

        var response = lambdaService.sendToLambda(ELambdaFunction.LAMBDA_FUNCTION_NAME.getValue(), ELambdaFunction.BUCKET_NAME.getValue(), arquivo, nomeArquivo);
        var responseString = response.payload().asUtf8String();
        LogoRecord logoRecord =objectMapper.readValue(responseString, LogoRecord.class) ;
        BodyMessage bodyMessage = objectMapper.readValue(logoRecord.body(), BodyMessage.class);
        if(response.statusCode() != 200){
            throw new BadRequestException("Lambda");
        }


        produto.setRecheio(recheio);
        produto.setMassa(massa);
        produto.setFoto(bodyMessage.url());
        produto.setCobertura(cobertura);
        produto.setUnidadeMedida(unidadeMedida);
        produto.setTipoProduto(tipoProduto);

        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos(){
        List<Produto> produtos = produtoRepository.findAll();
        return produtos;
    }

    public Produto atualizarProduto(int id, Produto produto, Integer recheioId, Integer massaId, Integer coberturaId, Integer unidadeMedidaId, Integer tipoProdutoId) {
        Produto produtoEncontrado = encontrarPorId(id);

        produtoEncontrado.setIdProduto(id);
        produtoEncontrado.setNome(produto.getNome());
        produtoEncontrado.setPreco(produto.getPreco());
        produtoEncontrado.setDescricao(produto.getDescricao());
        produtoEncontrado.setFoto(produto.getFoto());
        produtoEncontrado.setQtdDisponivel(produto.getQtdDisponivel());

        Recheio recheio = recheioService.encontrarPorId(recheioId);
        Massa massa = massaService.encontrarPorId(massaId);
        Cobertura cobertura = coberturaService.encontrarPorId(coberturaId);
        UnidadeMedida unidadeMedida = unidadeMedidaService.buscarPorId(unidadeMedidaId);
        TipoProduto tipoProduto = tipoProdutoService.buscarPorId(tipoProdutoId);

        produtoEncontrado.setRecheio(recheio);
        produtoEncontrado.setMassa(massa);
        produtoEncontrado.setCobertura(cobertura);
        produtoEncontrado.setUnidadeMedida(unidadeMedida);
        produtoEncontrado.setTipoProduto(tipoProduto);

        return produtoRepository.save(produtoEncontrado);
    }

    public String deletarProdutoPorId(Integer id){
        if(!produtoRepository.existsById(id)){
            throw new BadRequestException("Produto");
        }

        produtoRepository.deleteById(id);
        return "Produto deletado com sucesso!";
    }

    public List<Produto> filtrarNome() {
        List<Produto> produtos = produtoRepository.filtrarNome();
        return produtos;
    }

    public List<Produto> filtrarPreco() {
        List<Produto> produtos = produtoRepository.filtrarPreco();
        return produtos;
    }

    public List<Produto> filtrarTipo(String tipo) {
        List<Produto> produtos = produtoRepository.filtrarTipo(tipo);
        return produtos;
    }

}