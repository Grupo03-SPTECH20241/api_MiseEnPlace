package school.sptech.apimiseenplace.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.apimiseenplace.dto.produto_pedido.ProdutoPedidoListagemDTO;
import school.sptech.apimiseenplace.entity.*;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.PedidoRepository;
import school.sptech.apimiseenplace.repository.PersonalizacaoRepository;
import school.sptech.apimiseenplace.repository.ProdutoPedidoRepository;
import school.sptech.apimiseenplace.repository.ProdutoRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class ProdutoPedidoServiceTest {

    @InjectMocks
    private ProdutoPedidoService produtoPedidoService;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private PersonalizacaoService personalizacaoService;

    @Mock
    private ProdutoPedidoRepository produtoPedidoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PersonalizacaoRepository personalizacaoRepository;



    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{
        @Test
        @DisplayName("1.1 - Deve Retornar ProdutoPedido cadastrado, quando produtoPedido passado corretamente")
        void deveRetornarProdutoPedido(){

            Recheio recheioEnviado = new Recheio(1, "recheio", 35.0);
            Massa massaEnviada = new Massa(1, "massa");
            Cobertura coberturaEnviada = new Cobertura(1, "cobertura");
            UnidadeMedida unidadeMedida = new UnidadeMedida(1, "unidadeMedida", Collections.emptyList());
            TipoProduto tipoProduto = new TipoProduto(1, "tipoProduto", Collections.emptyList());
            Personalizacao personalizacaoEnviada = new Personalizacao(1, "personalizacao",recheioEnviado, massaEnviada, coberturaEnviada);

            FormaEntrega formaEntrega = new FormaEntrega(1, "formaEntrega", Collections.emptyList());

            LocalDate dtPedido = LocalDate.now();

            Cliente cliente = new Cliente(1, "cliente", "(11) 94296-1501", LocalDate.now());
            FormaPagamento formaPagamento = new FormaPagamento(1, "formaPagamento", Collections.emptyList());
            Pedido pedidoEnviado = new Pedido(1, dtPedido, 100.0, '1', 10.0, formaEntrega, cliente, formaPagamento);
            Produto produtoEnviado = new Produto(
                    1,
                    "produto",
                    35.0,
                    "desc",
                    "foto",
                    10,
                    recheioEnviado,
                    massaEnviada,
                    coberturaEnviada,
                    unidadeMedida,
                    tipoProduto);

            ProdutoPedido produtoPedidoEnviado = new ProdutoPedido(null, 1, "Obs", produtoEnviado, personalizacaoEnviada, pedidoEnviado);

            Mockito.when(produtoPedidoRepository.save(any(ProdutoPedido.class))).thenReturn(produtoPedidoEnviado);

            ProdutoPedido produtoPedidoRetornado = produtoPedidoService.cadastrar(produtoPedidoEnviado, 1, 1, 1);

            assertNotNull(produtoPedidoRetornado);
            assertEquals(produtoPedidoEnviado.getQtProduto(), produtoPedidoRetornado.getQtProduto());
            assertEquals(produtoPedidoEnviado.getObservacoes(), produtoPedidoRetornado.getObservacoes());
            assertEquals(produtoPedidoEnviado.getProduto(), produtoPedidoRetornado.getProduto());
            assertEquals(produtoPedidoEnviado.getPersonalizacao(), produtoPedidoRetornado.getPersonalizacao());
            assertEquals(produtoPedidoEnviado.getPedido(), produtoPedidoRetornado.getPedido());

            Mockito.verify(produtoPedidoRepository, Mockito.times(1)).save(produtoPedidoEnviado);
        }

        @Test
        @DisplayName("1.2 - Deve lançar exceção BadRequestException, quando produtoPedido passado nulo")
        void deveLancarExcecaoBadRequestException(){
            ProdutoPedido produtoPedidoEnviado = null;

            BadRequestException exception =
                    assertThrows(BadRequestException.class,
                            () -> produtoPedidoService.cadastrar(
                                    produtoPedidoEnviado, 1, 1, 1)
                    );

            assertEquals("Produto Pedido enviado de forma incorreta!", exception.getMessage());
            Mockito.verify(produtoPedidoRepository, Mockito.never()).save(any(ProdutoPedido.class));
        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{

        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll{

            @Test
            @DisplayName("2.1.1 - Deve Retornar Lista de ProdutoPedido, quando existir ProdutoPedido")
            void deveRetornarListaProdutoPedido(){
                Recheio recheioEnviado = new Recheio(1, "recheio", 35.0);
                Massa massaEnviada = new Massa(1, "massa");
                Cobertura coberturaEnviada = new Cobertura(1, "cobertura");
                UnidadeMedida unidadeMedida = new UnidadeMedida(1, "unidadeMedida", Collections.emptyList());
                TipoProduto tipoProduto = new TipoProduto(1, "tipoProduto", Collections.emptyList());
                Personalizacao personalizacaoEnviada = new Personalizacao(1, "personalizacao",recheioEnviado, massaEnviada, coberturaEnviada);

                FormaEntrega formaEntrega = new FormaEntrega(1, "formaEntrega", Collections.emptyList());

                LocalDate dtPedido = LocalDate.now();

                Cliente cliente = new Cliente(1, "cliente", "(11) 94296-1501", LocalDate.now());
                FormaPagamento formaPagamento = new FormaPagamento(1, "formaPagamento", Collections.emptyList());
                Pedido pedidoEnviado = new Pedido(1, dtPedido, 100.0, '1', 10.0, formaEntrega, cliente, formaPagamento);
                Produto produtoEnviado = new Produto(
                        1,
                        "produto",
                        35.0,
                        "desc",
                        "foto",
                        10,
                        recheioEnviado,
                        massaEnviada,
                        coberturaEnviada,
                        unidadeMedida,
                        tipoProduto);

                ProdutoPedido produtoPedidoEnviado = new ProdutoPedido(null, 1, "Obs", produtoEnviado, personalizacaoEnviada, pedidoEnviado);

                Mockito.when(produtoPedidoRepository.findAll()).thenReturn(Collections.singletonList(produtoPedidoEnviado));

                List<ProdutoPedidoListagemDTO> produtoPedidoListagemDTOS = produtoPedidoService.listar();

                assertNotNull(produtoPedidoListagemDTOS);
                assertFalse(produtoPedidoListagemDTOS.isEmpty());
                assertEquals(1, produtoPedidoListagemDTOS.size());
                assertEquals(produtoPedidoEnviado.getIdProdutoPedido(), produtoPedidoListagemDTOS.get(0).getIdProdutoPedido());
                assertEquals(produtoPedidoEnviado.getQtProduto(), produtoPedidoListagemDTOS.get(0).getQtProduto());
                assertEquals(produtoPedidoEnviado.getObservacoes(), produtoPedidoListagemDTOS.get(0).getObservacoes());

                Mockito.verify(produtoPedidoRepository, Mockito.times(1)).findAll();
            }

            @Test
            @DisplayName("2.1.2 - Deve Retornar Lista Vazia, quando não existir ProdutoPedido")
            void deveRetornarListaVazia(){
                Mockito.when(produtoPedidoRepository.findAll()).thenReturn(Collections.emptyList());

                List<ProdutoPedidoListagemDTO> produtoPedidoListagemDTOS = produtoPedidoService.listar();

                assertNotNull(produtoPedidoListagemDTOS);
                assertTrue(produtoPedidoListagemDTOS.isEmpty());
                Mockito.verify(produtoPedidoRepository, Mockito.times(1)).findAll();
            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de FindById")
        public class CenarioFindById{

            @Test
            @DisplayName("2.2.1 - Deve Retornar ProdutoPedido, quando existir ProdutoPedido com id informado")
            void retornarProdutoPedido(){
                Recheio recheioEnviado = new Recheio(1, "recheio", 35.0);
                Massa massaEnviada = new Massa(1, "massa");
                Cobertura coberturaEnviada = new Cobertura(1, "cobertura");
                UnidadeMedida unidadeMedida = new UnidadeMedida(1, "unidadeMedida", Collections.emptyList());
                TipoProduto tipoProduto = new TipoProduto(1, "tipoProduto", Collections.emptyList());
                Personalizacao personalizacaoEnviada = new Personalizacao(1, "personalizacao",recheioEnviado, massaEnviada, coberturaEnviada);

                FormaEntrega formaEntrega = new FormaEntrega(1, "formaEntrega", Collections.emptyList());

                LocalDate dtPedido = LocalDate.now();

                Cliente cliente = new Cliente(1, "cliente", "(11) 94296-1501", LocalDate.now());
                FormaPagamento formaPagamento = new FormaPagamento(1, "formaPagamento", Collections.emptyList());
                Pedido pedidoEnviado = new Pedido(1, dtPedido, 100.0, '1', 10.0, formaEntrega, cliente, formaPagamento);
                Produto produtoEnviado = new Produto(
                        1,
                        "produto",
                        35.0,
                        "desc",
                        "foto",
                        10,
                        recheioEnviado,
                        massaEnviada,
                        coberturaEnviada,
                        unidadeMedida,
                        tipoProduto);

                ProdutoPedido produtoPedidoEnviado = new ProdutoPedido(null, 1, "Obs", produtoEnviado, personalizacaoEnviada, pedidoEnviado);

                Mockito.when(produtoPedidoRepository.findById(anyInt())).thenReturn(Optional.of(produtoPedidoEnviado));

                ProdutoPedido produtoPedidoRetornado = produtoPedidoService.encontrarPorId(1);

                assertNotNull(produtoPedidoRetornado);
                assertEquals(produtoPedidoEnviado.getIdProdutoPedido(), produtoPedidoRetornado.getIdProdutoPedido());
                assertEquals(produtoPedidoEnviado.getQtProduto(), produtoPedidoRetornado.getQtProduto());
                assertEquals(produtoPedidoEnviado.getObservacoes(), produtoPedidoRetornado.getObservacoes());
                assertEquals(produtoPedidoEnviado.getProduto(), produtoPedidoRetornado.getProduto());
                Mockito.verify(produtoPedidoRepository, Mockito.times(1)).findById(1);
            }

            @Test
            @DisplayName("2.2.2 - Deve lançar exceção NaoEncontradoException, quando não existir ProdutoPedido com id informado")
            void deveLancarExcecaoNaoEncontradoException() {
                Mockito.when(produtoPedidoRepository.findById(anyInt())).thenReturn(Optional.empty());

                NaoEncontradoException exception = assertThrows(NaoEncontradoException.class,
                        () -> produtoPedidoService.encontrarPorId(1));

                assertEquals("Produto Pedido não encontrado", exception.getMessage());
                Mockito.verify(produtoPedidoRepository, Mockito.times(1)).findById(Mockito.anyInt());
            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualização")
    public class CenarioAtualizacao{

        @Test
        @DisplayName("3.1 - Deve Retornar ProdutoPedido Atualizado, quando produtoPedido passado corretamente")
        void deveRetornarProdutoPedidoAtualizado() {
            Recheio recheioEnviado = new Recheio(1, "recheio", 35.0);
            Massa massaEnviada = new Massa(1, "massa");
            Cobertura coberturaEnviada = new Cobertura(1, "cobertura");
            UnidadeMedida unidadeMedida = new UnidadeMedida(1, "unidadeMedida", Collections.emptyList());
            TipoProduto tipoProduto = new TipoProduto(1, "tipoProduto", Collections.emptyList());
            Personalizacao personalizacaoEnviada = new Personalizacao(1, "personalizacao", recheioEnviado, massaEnviada, coberturaEnviada);

            FormaEntrega formaEntrega = new FormaEntrega(1, "formaEntrega", Collections.emptyList());

            LocalDate dtPedido = LocalDate.now();

            Cliente cliente = new Cliente(1, "cliente", "(11) 94296-1501", LocalDate.now());
            FormaPagamento formaPagamento = new FormaPagamento(1, "formaPagamento", Collections.emptyList());
            Pedido pedidoEnviado = new Pedido(1, dtPedido, 100.0, '1', 10.0, formaEntrega, cliente, formaPagamento);
            Produto produtoEnviado = new Produto(
                    1,
                    "produto",
                    35.0,
                    "desc",
                    "foto",
                    10,
                    recheioEnviado,
                    massaEnviada,
                    coberturaEnviada,
                    unidadeMedida,
                    tipoProduto);

            Recheio recheioRecebido = new Recheio(1, "recheio", 35.0);
            Massa massaRecebido = new Massa(1, "massa");
            Cobertura coberturaRecebido = new Cobertura(1, "cobertura");
            UnidadeMedida unidadeMedidaRecebido = new UnidadeMedida(1, "unidadeMedida", Collections.emptyList());
            TipoProduto tipoProdutoRecebido = new TipoProduto(1, "tipoProduto", Collections.emptyList());
            Personalizacao personalizacaoRecebido = new Personalizacao(1, "personalizacao", recheioRecebido, massaRecebido, coberturaRecebido);

            FormaEntrega formaEntregaRecebido = new FormaEntrega(1, "formaEntrega", Collections.emptyList());

            LocalDate dtPedidoRecebido = LocalDate.now();

            Cliente clienteRecebido = new Cliente(1, "cliente", "(11) 94296-1501", LocalDate.now());
            FormaPagamento formaPagamentoRecebido = new FormaPagamento(1, "formaPagamento", Collections.emptyList());
            Pedido pedidoRecebido = new Pedido(1, dtPedidoRecebido, 100.0, '1', 10.0, formaEntregaRecebido, clienteRecebido, formaPagamentoRecebido);
            Produto produtoRecebido = new Produto(
                    1,
                    "produto",
                    35.0,
                    "desc",
                    "foto",
                    10,
                    recheioRecebido,
                    massaRecebido,
                    coberturaRecebido,
                    unidadeMedidaRecebido,
                    tipoProdutoRecebido);

            ProdutoPedido produtoPedido = new ProdutoPedido(null, 1, "Obs", produtoEnviado, personalizacaoEnviada, pedidoEnviado);
            ProdutoPedido produtoPedidoAtualizado = new ProdutoPedido(null, 1, "Obs", produtoRecebido, personalizacaoRecebido, pedidoRecebido);

            Mockito.when(produtoPedidoRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(produtoPedido));

            ProdutoPedido produtoPedidoEncontrado = produtoPedidoService.encontrarPorId(Mockito.any(Integer.class));
            assertEquals(produtoPedido.getIdProdutoPedido(), produtoPedidoEncontrado.getIdProdutoPedido());
            assertEquals(produtoPedido.getQtProduto(), produtoPedidoEncontrado.getQtProduto());
            assertEquals(produtoPedido.getObservacoes(), produtoPedidoEncontrado.getObservacoes());
            assertEquals(produtoPedido.getProduto(), produtoPedidoEncontrado.getProduto());
            assertEquals(produtoPedido.getPersonalizacao(), produtoPedidoEncontrado.getPersonalizacao());
            assertEquals(produtoPedido.getPedido(), produtoPedidoEncontrado.getPedido());

            Mockito.when(produtoPedidoRepository.save(Mockito.any(ProdutoPedido.class))).thenReturn(produtoPedidoAtualizado);

            ProdutoPedido produtoPedidoRetornado = produtoPedidoService.atualizar(1, produtoPedidoAtualizado, 1, 1, 1);

            assertEquals(produtoPedidoAtualizado.getIdProdutoPedido(), produtoPedidoRetornado.getIdProdutoPedido());
            assertEquals(produtoPedidoAtualizado.getQtProduto(), produtoPedidoRetornado.getQtProduto());
            assertEquals(produtoPedidoAtualizado.getObservacoes(), produtoPedidoRetornado.getObservacoes());
            assertEquals(produtoPedidoAtualizado.getProduto(), produtoPedidoRetornado.getProduto());
            assertEquals(produtoPedidoAtualizado.getPersonalizacao(), produtoPedidoRetornado.getPersonalizacao());
            assertEquals(produtoPedidoAtualizado.getPedido(), produtoPedidoRetornado.getPedido());

            Mockito.verify(produtoPedidoRepository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(produtoPedidoRepository, Mockito.times(1)).save(Mockito.any(ProdutoPedido.class));
        }

        @Test
        @DisplayName("3.2 - Deve lançar exceção NaoEncontradoException, quando produtoPedido passado como null")
        void deveThrowlarException(){
            Recheio recheioEnviado = new Recheio(1, "recheio", 35.0);
            Massa massaEnviada = new Massa(1, "massa");
            Cobertura coberturaEnviada = new Cobertura(1, "cobertura");
            UnidadeMedida unidadeMedida = new UnidadeMedida(1, "unidadeMedida", Collections.emptyList());
            TipoProduto tipoProduto = new TipoProduto(1, "tipoProduto", Collections.emptyList());
            Personalizacao personalizacaoEnviada = new Personalizacao(1, "personalizacao",recheioEnviado, massaEnviada, coberturaEnviada);

            FormaEntrega formaEntrega = new FormaEntrega(1, "formaEntrega", Collections.emptyList());

            LocalDate dtPedido = LocalDate.now();

            Cliente cliente = new Cliente(1, "cliente", "(11) 94296-1501", LocalDate.now());
            FormaPagamento formaPagamento = new FormaPagamento(1, "formaPagamento", Collections.emptyList());
            Pedido pedidoEnviado = new Pedido(1, dtPedido, 100.0, '1', 10.0, formaEntrega, cliente, formaPagamento);
            Produto produtoEnviado = new Produto(
                    1,
                    "produto",
                    35.0,
                    "desc",
                    "foto",
                    10,
                    recheioEnviado,
                    massaEnviada,
                    coberturaEnviada,
                    unidadeMedida,
                    tipoProduto);

            ProdutoPedido produtoPedidoEnviado = new ProdutoPedido(null, 1, "Obs", produtoEnviado, personalizacaoEnviada, pedidoEnviado);

            Mockito.when(produtoPedidoRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

            NaoEncontradoException exception = assertThrows(
                    NaoEncontradoException.class,
                    () -> produtoPedidoService.atualizar(Mockito.any(Integer.class), produtoPedidoEnviado, 1, 1, 1)
            );

            assertEquals("Produto Pedido não encontrado", exception.getMessage());

            Mockito.verify(produtoPedidoRepository, Mockito.times(1)).findById(Mockito.any(Integer.class));
            Mockito.verify(produtoPedidoRepository, Mockito.times(0)).save(Mockito.any(ProdutoPedido.class));
        }
    }

    @Nested
    @DisplayName("4 - Cenários de Exclusão")
    public class CenarioExclusao{

        @Test
        @DisplayName("4.1 - Deve Excluir ProdutoPedido, quando existir ProdutoPedido com id informado")
        void deveExcluirProdutoPedido() {

            Mockito.when(produtoPedidoRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno =  produtoPedidoService.deletar(Mockito.any(Integer.class));

            assertEquals(retorno, "Produto Pedido deletado com sucesso!");
            Mockito.verify(produtoPedidoRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(produtoPedidoRepository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));
        }

        @Test
        @DisplayName("4.2 - Deve lançar exceção NaoEncontradoException, quando não existir ProdutoPedido com id informado")
        void deveLancarExcecaoNaoEncontradoException() {
            Mockito.when(produtoPedidoRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            NaoEncontradoException exception = assertThrows(
                    NaoEncontradoException.class,
                    () -> produtoPedidoService.deletar(Mockito.any(Integer.class))
            );

            assertEquals("Produto Pedido não encontrado", exception.getMessage());
            Mockito.verify(produtoPedidoRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(produtoPedidoRepository, Mockito.times(0)).deleteById(Mockito.any(Integer.class));
        }
    }

    @Nested
    @DisplayName("5 - Cenários de Listagem Where Id")
    public class CenarioListagemWhereId{
        @Test
        @DisplayName("5.1 - Deve Retornar Lista de ProdutoPedido, quando existir ProdutoPedido com id informado")
        void deveRetornarListaProdutoPedido(){
            Recheio recheioEnviado = new Recheio(1, "recheio", 35.0);
            Massa massaEnviada = new Massa(1, "massa");
            Cobertura coberturaEnviada = new Cobertura(1, "cobertura");
            UnidadeMedida unidadeMedida = new UnidadeMedida(1, "unidadeMedida", Collections.emptyList());
            TipoProduto tipoProduto = new TipoProduto(1, "tipoProduto", Collections.emptyList());
            Personalizacao personalizacaoEnviada = new Personalizacao(1, "personalizacao",recheioEnviado, massaEnviada, coberturaEnviada);

            FormaEntrega formaEntrega = new FormaEntrega(1, "formaEntrega", Collections.emptyList());

            LocalDate dtPedido = LocalDate.now();

            Cliente cliente = new Cliente(1, "cliente", "(11) 94296-1501", LocalDate.now());
            FormaPagamento formaPagamento = new FormaPagamento(1, "formaPagamento", Collections.emptyList());
            Pedido pedidoEnviado = new Pedido(1, dtPedido, 100.0, '1', 10.0, formaEntrega, cliente, formaPagamento);
            Produto produtoEnviado = new Produto(
                    1,
                    "produto",
                    35.0,
                    "desc",
                    "foto",
                    10,
                    recheioEnviado,
                    massaEnviada,
                    coberturaEnviada,
                    unidadeMedida,
                    tipoProduto);

            ProdutoPedido produtoPedidoEnviado = new ProdutoPedido(null, 1, "Obs", produtoEnviado, personalizacaoEnviada, pedidoEnviado);
            Integer idPedido = 1;

            List<ProdutoPedido> produtoPedidos = Collections.singletonList(produtoPedidoEnviado);
            Mockito.when(produtoPedidoRepository.findByIdPersonalizacaoEquals(idPedido)).thenReturn(produtoPedidos);

            List<ProdutoPedidoListagemDTO> result = produtoPedidoService.listarWhereIdPersonalizacaoEquals(idPedido);

            assertNotNull(result);
            assertEquals(produtoPedidos.size(), result.size());
            Mockito.verify(produtoPedidoRepository, Mockito.times(1)).findByIdPersonalizacaoEquals(idPedido);
        }

        @Test
        @DisplayName("5.2 - Deve Retornar Lista Vazia, quando não existir ProdutoPedido com id informado")
        void deveRetornarListaVazia(){
            Integer idPedido = 1;
            List<ProdutoPedido> produtoPedidos = Collections.emptyList();
            Mockito.when(produtoPedidoRepository.findByIdPersonalizacaoEquals(idPedido)).thenReturn(produtoPedidos);

            List<ProdutoPedidoListagemDTO> result = produtoPedidoService.listarWhereIdPersonalizacaoEquals(idPedido);

            assertNotNull(result);
            assertTrue(result.isEmpty());
            Mockito.verify(produtoPedidoRepository, Mockito.times(1)).findByIdPersonalizacaoEquals(idPedido);
        }
    }

    @Nested
    @DisplayName("6 - Cenários de Update Personalizacao")
    public class CenarioUpdatePersonalizacao{
        @Test
        @DisplayName("6.1 - Deve atualizar a personalizacao do produtoPedido, quando existir ProdutoPedido com id informado")
        void deveAtualizarPersonalizacaoProdutoPedido() {
            Recheio recheioEnviado = new Recheio(1, "recheio", 35.0);
            Massa massaEnviada = new Massa(1, "massa");
            Cobertura coberturaEnviada = new Cobertura(1, "cobertura");
            UnidadeMedida unidadeMedida = new UnidadeMedida(1, "unidadeMedida", Collections.emptyList());
            TipoProduto tipoProduto = new TipoProduto(1, "tipoProduto", Collections.emptyList());
            Personalizacao personalizacaoEnviada = new Personalizacao(1, "personalizacao",recheioEnviado, massaEnviada, coberturaEnviada);

            FormaEntrega formaEntrega = new FormaEntrega(1, "formaEntrega", Collections.emptyList());

            LocalDate dtPedido = LocalDate.now();

            Cliente cliente = new Cliente(1, "cliente", "(11) 94296-1501", LocalDate.now());
            FormaPagamento formaPagamento = new FormaPagamento(1, "formaPagamento", Collections.emptyList());
            Pedido pedidoEnviado = new Pedido(1, dtPedido, 100.0, '1', 10.0, formaEntrega, cliente, formaPagamento);
            Produto produtoEnviado = new Produto(
                    1,
                    "produto",
                    35.0,
                    "desc",
                    "foto",
                    10,
                    recheioEnviado,
                    massaEnviada,
                    coberturaEnviada,
                    unidadeMedida,
                    tipoProduto);

            ProdutoPedido produtoPedido = new ProdutoPedido(null, 1, "Obs", produtoEnviado, personalizacaoEnviada, pedidoEnviado);

            Recheio recheio = new Recheio(1, "Chocolate", 3.50);
            Massa massa = new Massa(1, "Massa Chocolate");
            Cobertura cobertura = new Cobertura(1, "Calda de Chocolate");
            Personalizacao personalizacao = new Personalizacao(1, "Os Incríveis", recheio, massa, cobertura);

            Integer idProdutoPedido = 1;
            Mockito.when(produtoPedidoRepository.existsById(idProdutoPedido)).thenReturn(true);
            Mockito.when(produtoPedidoRepository.findById(idProdutoPedido)).thenReturn(Optional.of(produtoPedido));
            Mockito.when(produtoPedidoRepository.save(any(ProdutoPedido.class))).thenReturn(produtoPedido);

            ProdutoPedidoListagemDTO result = produtoPedidoService.updatePersonalizacao(personalizacao, idProdutoPedido);

            assertNotNull(result);
            Mockito.verify(produtoPedidoRepository, Mockito.times(1)).save(Mockito.any(ProdutoPedido.class));
        }

        @Test
        @DisplayName("6.2 - Deve lançar exceção NaoEncontradoException, quando não existir ProdutoPedido com id informado")
        void deveLancarNaoEncontradoException(){
            Integer idProdutoPedido = 1;
            Personalizacao personalizacao = new Personalizacao();
            Mockito.when(produtoPedidoRepository.existsById(idProdutoPedido)).thenReturn(false);

            assertThrows(BadRequestException.class, () -> produtoPedidoService.updatePersonalizacao(personalizacao, idProdutoPedido));
            Mockito.verify(produtoPedidoRepository, Mockito.times(0)).save(Mockito.any(ProdutoPedido.class));
        }
    }
}

