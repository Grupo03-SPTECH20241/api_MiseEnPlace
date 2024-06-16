package school.sptech.apimiseenplace.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.apimiseenplace.entity.*;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private RecheioService recheioService;
    @Mock
    private MassaService massaService;
    @Mock
    private CoberturaService coberturaService;
    @Mock
    private UnidadeMedidaService unidadeMedidaService;
    @Mock
    private TipoProdutoService tipoProdutoService;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{

        @Test
        @DisplayName("1.1 - Deve Retornar produto cadastrado, quando nome passado corretamento")
        void deveRetornarProduto(){

            Recheio recheioInsertion = new Recheio(1, "Chocolate", 3.50);
            Massa massaInsertion = new Massa(1, "Massa Chocolate");
            Cobertura coberturaInsertion = new Cobertura(1, "Calda de Chocolate");
            UnidadeMedida unidadeMedidaInsertion = new UnidadeMedida(1, "Quilo", null);
            TipoProduto tipoProdutoInsertion = new TipoProduto(1, "Bolo", null);
            Produto produtoInsertion = new Produto(null, "Bolo de Chocolate", 80.0, "Bolo de Chocolate Comum", "Foto", 1, recheioInsertion, massaInsertion, coberturaInsertion, unidadeMedidaInsertion, tipoProdutoInsertion);
            Produto produtoEsperado = new Produto(1, "Bolo de Chocolate", 80.0, "Bolo de Chocolate Comum", "Foto", 1, recheioInsertion, massaInsertion, coberturaInsertion, unidadeMedidaInsertion, tipoProdutoInsertion);

            Mockito.when(recheioService.encontrarPorId(1)).thenReturn(recheioInsertion);
            Mockito.when(massaService.encontrarPorId(1)).thenReturn(massaInsertion);
            Mockito.when(coberturaService.encontrarPorId(1)).thenReturn(coberturaInsertion);
            Mockito.when(unidadeMedidaService.buscarPorId(1)).thenReturn(unidadeMedidaInsertion);
            Mockito.when(tipoProdutoService.buscarPorId(1)).thenReturn(tipoProdutoInsertion);
            Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produtoEsperado);

            Produto produtoSalvo = produtoService.cadastrarProduto(produtoInsertion, 1, 1, 1, 1, 1);

            Assertions.assertEquals(produtoEsperado.getIdProduto(), produtoSalvo.getIdProduto());
            Assertions.assertEquals(produtoEsperado.getNome(), produtoSalvo.getNome());
            Assertions.assertEquals(produtoEsperado.getPreco(), produtoSalvo.getPreco());
            Assertions.assertEquals(produtoEsperado.getDescricao(), produtoSalvo.getDescricao());
            Assertions.assertEquals(produtoEsperado.getFoto(), produtoSalvo.getFoto());
            Assertions.assertEquals(produtoEsperado.getQtdDisponivel(), produtoSalvo.getQtdDisponivel());
            Assertions.assertEquals(produtoEsperado.getRecheio(), produtoSalvo.getRecheio());
            Assertions.assertEquals(produtoEsperado.getMassa(), produtoSalvo.getMassa());
            Assertions.assertEquals(produtoEsperado.getCobertura(), produtoSalvo.getCobertura());
            Assertions.assertEquals(produtoEsperado.getUnidadeMedida(), produtoSalvo.getUnidadeMedida());
            Assertions.assertEquals(produtoEsperado.getTipoProduto(), produtoSalvo.getTipoProduto());
            Mockito.verify(produtoRepository, Mockito.times(1)).save(produtoInsertion);

        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando produto passado como null")
        void deveThrowlarException(){

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> produtoService.cadastrarProduto(null, null, null, null, null, null));

            Assertions.assertEquals("Produto enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(produtoRepository, Mockito.times(0)).save(Mockito.any(Produto.class));

        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{

        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll{
            @Test
            @DisplayName("2.1.1 - Deve retornar lista de produtos")
            void deveRetornarListaProduto(){

                Recheio recheioInsertion = new Recheio(1, "Chocolate", 3.50);
                Massa massaInsertion = new Massa(1, "Massa Chocolate");
                Cobertura coberturaInsertion = new Cobertura(1, "Calda de Chocolate");
                UnidadeMedida unidadeMedidaInsertion = new UnidadeMedida(1, "Quilo", null);
                TipoProduto tipoProdutoInsertion = new TipoProduto(1, "Bolo", null);

                List<Produto> produtos = List.of(
                        new Produto(1, "Bolo de Chocolate", 60.0, "Bolo de Chocolate Comum", "Foto", 1, recheioInsertion, massaInsertion, coberturaInsertion, unidadeMedidaInsertion, tipoProdutoInsertion),
                        new Produto(2, "Bolo de Brigadeiro", 55.0, "Bolo de Chocolate com Granulado", "Foto", 1, recheioInsertion, massaInsertion, coberturaInsertion, unidadeMedidaInsertion, tipoProdutoInsertion),
                        new Produto(3, "Bolo Formigueiro com Chocolate", 70.0, "Bolo Formigueiro com Calda de Chocolate", "Foto", 1, recheioInsertion, massaInsertion, coberturaInsertion, unidadeMedidaInsertion, tipoProdutoInsertion)
                );

                Mockito.when(produtoRepository.findAll()).thenReturn(produtos);

                List<Produto> produtosRetornados = produtoService.listarProdutos();

                Assertions.assertEquals(produtos.size(), produtosRetornados.size());
                Assertions.assertEquals(3, produtosRetornados.size());
                Assertions.assertFalse(produtosRetornados.isEmpty());
                Assertions.assertEquals(produtos.get(0).getIdProduto(), produtosRetornados.get(0).getIdProduto());
                Assertions.assertEquals(produtos.get(0).getNome(), produtosRetornados.get(0).getNome());
                Assertions.assertEquals(produtos.get(0).getPreco(), produtosRetornados.get(0).getPreco());
                Assertions.assertEquals(produtos.get(0).getDescricao(), produtosRetornados.get(0).getDescricao());
                Assertions.assertEquals(produtos.get(0).getFoto(), produtosRetornados.get(0).getFoto());
                Assertions.assertEquals(produtos.get(0).getQtdDisponivel(), produtosRetornados.get(0).getQtdDisponivel());
                Assertions.assertEquals(produtos.get(0).getRecheio(), produtosRetornados.get(0).getRecheio());
                Assertions.assertEquals(produtos.get(0).getMassa(), produtosRetornados.get(0).getMassa());
                Assertions.assertEquals(produtos.get(0).getCobertura(), produtosRetornados.get(0).getCobertura());
                Assertions.assertEquals(produtos.get(0).getUnidadeMedida(), produtosRetornados.get(0).getUnidadeMedida());
                Assertions.assertEquals(produtos.get(0).getTipoProduto(), produtosRetornados.get(0).getTipoProduto());
                Mockito.verify(produtoRepository, Mockito.times(1)).findAll();

            }

            @Test@DisplayName("2.1.2 - Deve retornara lista vazia de produtos")
            void retornaListaVazia(){

                List<Produto> produtos = new ArrayList<>();

                Mockito.when(produtoRepository.findAll()).thenReturn(produtos);

                List<Produto> produtosRetornados = produtoService.listarProdutos();

                Assertions.assertTrue(produtosRetornados.isEmpty());
                Mockito.verify(produtoRepository, Mockito.times(1)).findAll();

            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem por id")
        public class CenarioFindById{

            @Test
            @DisplayName("2.2.1 - Deve retornar o produto por id")
            void retornaProdutoPorId(){

                Recheio recheioInsertion = new Recheio(1, "Chocolate", 3.50);
                Massa massaInsertion = new Massa(1, "Massa Chocolate");
                Cobertura coberturaInsertion = new Cobertura(1, "Calda de Chocolate");
                UnidadeMedida unidadeMedidaInsertion = new UnidadeMedida(1, "Quilo", null);
                TipoProduto tipoProdutoInsertion = new TipoProduto(1, "Bolo", null);
                Produto produtoInsertion = new Produto(1, "Bolo de Chocolate", 80.0, "Bolo de Chocolate Comum", "Foto", 1, recheioInsertion, massaInsertion, coberturaInsertion, unidadeMedidaInsertion, tipoProdutoInsertion);

                Integer idBusca = 1;

                Mockito.when(produtoRepository.findById(idBusca)).thenReturn(Optional.of(produtoInsertion));

                Produto produtoEncontrado = produtoService.encontrarPorId(idBusca);

                Assertions.assertEquals(idBusca, produtoEncontrado.getIdProduto());
                Mockito.verify(produtoRepository, Mockito.times(1)).findById(idBusca);

            }

            @Test
            @DisplayName("2.2.2 - Deve retornar NaoEncontradoException caso não exista o id")
            void retornaExceptionCasoNaoExite(){

                Integer idBusca = 100;

                Mockito.when(produtoRepository.findById(idBusca)).thenReturn(Optional.empty());
                NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                        () -> produtoService.encontrarPorId(idBusca));

                Assertions.assertEquals("Produto não encontrado", exception.getLocalizedMessage());

                Mockito.verify(produtoRepository, Mockito.times(1)).findById(idBusca);

            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualizar")
    public class CenarioPut{

        @Test
        @DisplayName("3.1 - Deve retornar o Objeto Atualizado, quando passado o produto de atualização correto")
        void retornaObjetoAtualizado(){

            Recheio recheioAntigo = new Recheio(1, "Chocolate", 3.50);
            Massa massaAntigo = new Massa(1, "Massa Chocolate");
            Cobertura coberturaAntigo = new Cobertura(1, "Calda de Chocolate");
            UnidadeMedida unidadeMedidaAntigo = new UnidadeMedida(1, "Quilo", null);
            TipoProduto tipoProdutoAntigo = new TipoProduto(1, "Bolo", null);
            Produto produtoAntigo = new Produto(1, "Bolo de Chocolate", 80.0, "Bolo de Chocolate Comum", "Foto", 1, recheioAntigo, massaAntigo, coberturaAntigo, unidadeMedidaAntigo, tipoProdutoAntigo);

            Recheio recheioAtualizado = new Recheio(2, "Morango", 4.50);
            Massa massaAtualizado = new Massa(2, "Massa Ninho");
            Cobertura coberturaAtualizado = new Cobertura(2, "Calda de Morango");
            UnidadeMedida unidadeMedidaAtualizado = new UnidadeMedida(1, "Quilo", null);
            TipoProduto tipoProdutoAtualizado = new TipoProduto(1, "Bolo", null);
            Produto produtoAtualizado = new Produto(1, "Bolo de Morango", 70.0, "Bolo de Morango Comum", "Foto", 1, recheioAtualizado, massaAtualizado, coberturaAtualizado, unidadeMedidaAtualizado, tipoProdutoAtualizado);

            Mockito.when(produtoRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(produtoAntigo));

            Produto produtoEncontrado = produtoService.encontrarPorId(Mockito.any(Integer.class));

            Assertions.assertEquals(produtoEncontrado.getIdProduto(), produtoAntigo.getIdProduto());
            Assertions.assertEquals(produtoEncontrado.getNome(), produtoAntigo.getNome());
            Assertions.assertEquals(produtoEncontrado.getPreco(), produtoAntigo.getPreco());
            Assertions.assertEquals(produtoEncontrado.getDescricao(), produtoAntigo.getDescricao());
            Assertions.assertEquals(produtoEncontrado.getFoto(), produtoAntigo.getFoto());
            Assertions.assertEquals(produtoEncontrado.getQtdDisponivel(), produtoAntigo.getQtdDisponivel());
            Assertions.assertEquals(produtoEncontrado.getRecheio(), produtoAntigo.getRecheio());
            Assertions.assertEquals(produtoEncontrado.getMassa(), produtoAntigo.getMassa());
            Assertions.assertEquals(produtoEncontrado.getCobertura(), produtoAntigo.getCobertura());
            Assertions.assertEquals(produtoEncontrado.getUnidadeMedida(), produtoAntigo.getUnidadeMedida());
            Assertions.assertEquals(produtoEncontrado.getTipoProduto(), produtoAntigo.getTipoProduto());

            Mockito.when(recheioService.encontrarPorId(2)).thenReturn(recheioAtualizado);
            Mockito.when(massaService.encontrarPorId(2)).thenReturn(massaAtualizado);
            Mockito.when(coberturaService.encontrarPorId(2)).thenReturn(coberturaAtualizado);
            Mockito.when(unidadeMedidaService.buscarPorId(2)).thenReturn(unidadeMedidaAtualizado);
            Mockito.when(tipoProdutoService.buscarPorId(2)).thenReturn(tipoProdutoAtualizado);
            Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produtoAtualizado);

            Produto produtoRetornado = produtoService.atualizarProduto(Mockito.any(Integer.class),produtoAtualizado,2, 2, 2, 2, 2);

            Assertions.assertEquals(produtoRetornado.getIdProduto(), produtoAtualizado.getIdProduto());
            Assertions.assertEquals(produtoRetornado.getNome(), produtoAtualizado.getNome());
            Assertions.assertEquals(produtoRetornado.getPreco(), produtoAtualizado.getPreco());
            Assertions.assertEquals(produtoRetornado.getDescricao(), produtoAtualizado.getDescricao());
            Assertions.assertEquals(produtoRetornado.getFoto(), produtoAtualizado.getFoto());
            Assertions.assertEquals(produtoRetornado.getQtdDisponivel(), produtoAtualizado.getQtdDisponivel());
            Assertions.assertEquals(produtoRetornado.getRecheio(), produtoAtualizado.getRecheio());
            Assertions.assertEquals(produtoRetornado.getMassa(), produtoAtualizado.getMassa());
            Assertions.assertEquals(produtoRetornado.getCobertura(), produtoAtualizado.getCobertura());
            Assertions.assertEquals(produtoRetornado.getUnidadeMedida(), produtoAtualizado.getUnidadeMedida());
            Assertions.assertEquals(produtoRetornado.getTipoProduto(), produtoAtualizado.getTipoProduto());

            Mockito.verify(produtoRepository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(produtoRepository, Mockito.times(1)).save(Mockito.any(Produto.class));

        }

        @Test
        @DisplayName("3.2 - Deve throwlar BadRequestException, quando passado o id de atualização incorreto")
        void throwlaBadRequest(){

            Integer idBusca = 1;

            Mockito.when(produtoRepository.findById(idBusca)).thenReturn(Optional.empty());

            NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                    () -> produtoService.encontrarPorId(idBusca));

            Assertions.assertEquals("Produto não encontrado", exception.getLocalizedMessage());

            Mockito.verify(produtoRepository, Mockito.times(1)).findById(idBusca);

        }
    }


    @Nested
    @DisplayName("4 - Cenários de Deletar")
    public class CenarioDelete{
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso quando id do produto passado corretamente")
        void retornaStringSucesso(){

            Mockito.when(produtoRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = produtoService.deletarProdutoPorId(Mockito.any(Integer.class));

            Assertions.assertEquals("Produto deletado com sucesso!", retorno);
            Mockito.verify(produtoRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(produtoRepository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));

        }
        @Test
        @DisplayName("4.2 - Deve retornar BadRequestException quando id do produto passado incorretamente")
        void throwlaBadRequest(){

            Mockito.when(produtoRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> produtoService.deletarProdutoPorId(Mockito.any(Integer.class)));

            Assertions.assertEquals(exception.getLocalizedMessage(), "Produto enviado de forma incorreta!");
            Mockito.verify(produtoRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));

        }
    }


}