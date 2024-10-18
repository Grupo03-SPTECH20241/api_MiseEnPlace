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
import school.sptech.apimiseenplace.repository.PedidoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private FormaEntregaService formaEntregaService;
    @Mock
    private ClienteService clienteService;
    @Mock
    private FormaPagamentoService formaPagamentoService;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{

//        @Test
//        @DisplayName("1.1 - Deve Retornar pedido cadastrado, quando nome passado corretamento")
//        void deveRetornarPedido(){
//
//            FormaEntrega formaEntregaInsertion = new FormaEntrega(1, "Motoboy", null);
//            FormaPagamento formaPagamentoInsertion = new FormaPagamento(1, "PIX", null);
//            Cliente clienteInsertion = new Cliente(1, "Leandro", "11996570337", LocalDate.parse("2002-04-28"));
//            Pedido pedidoInsertion = new Pedido(null, LocalDate.parse("2024-04-28"), 200.0, '1', 100.0, formaEntregaInsertion, clienteInsertion, formaPagamentoInsertion);
//
//            Pedido pedidoEsperado = new Pedido(1, LocalDate.parse("2024-04-28"), 200.0, '1', 100.0, formaEntregaInsertion, clienteInsertion, formaPagamentoInsertion);
//
//            Mockito.when(formaEntregaService.encontrarPorId(1)).thenReturn(formaEntregaInsertion);
//            Mockito.when(clienteService.encontrarPorId(1)).thenReturn(clienteInsertion);
//            Mockito.when(formaPagamentoService.encontrarPorId(1)).thenReturn(formaPagamentoInsertion);
//            Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class))).thenReturn(pedidoEsperado);
//
//            Pedido pedidoSalvo = pedidoService.cadastrar(pedidoInsertion, 1, 1, 1);
//
//            Assertions.assertEquals(pedidoEsperado.getIdPedido(), pedidoSalvo.getIdPedido());
//            Assertions.assertEquals(pedidoEsperado.getDtPedido(), pedidoSalvo.getDtPedido());
//            Assertions.assertEquals(pedidoEsperado.getVlPedido(), pedidoSalvo.getVlPedido());
//            Assertions.assertEquals(pedidoEsperado.getStatus(), pedidoSalvo.getStatus());
//            Assertions.assertEquals(pedidoEsperado.getValorSinal(), pedidoSalvo.getValorSinal());
//            Assertions.assertEquals(pedidoEsperado.getFormaEntrega(), pedidoSalvo.getFormaEntrega());
//            Assertions.assertEquals(pedidoEsperado.getCliente(), pedidoSalvo.getCliente());
//            Assertions.assertEquals(pedidoEsperado.getFormaPagamento(), pedidoSalvo.getFormaPagamento());
//            Mockito.verify(pedidoRepository, Mockito.times(1)).save(pedidoInsertion);
//
//        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando pedido passado como null")
        void deveThrowlarException(){

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> pedidoService.cadastrar(null, null, null, null));

            Assertions.assertEquals("Pedido enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(pedidoRepository, Mockito.times(0)).save(Mockito.any(Pedido.class));

        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{

        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll{
//            @Test
//            @DisplayName("2.1.1 - Deve retornar lista de pedidos")
//            void deveRetornarListaPedido(){
//
//                FormaEntrega formaEntregaInsertion = new FormaEntrega(1, "Motoboy", null);
//                FormaPagamento formaPagamentoInsertion = new FormaPagamento(1, "PIX", null);
//                Cliente clienteInsertion = new Cliente(1, "Leandro", "11996570337", LocalDate.parse("2002-04-28"));
//
//                List<Pedido> pedidos = List.of(
//                        new Pedido(1, LocalDate.parse("2024-04-28"), 200.0, '1', 100.0, formaEntregaInsertion, clienteInsertion, formaPagamentoInsertion),
//                        new Pedido(2, LocalDate.parse("2024-06-20"), 140.0, '2', 70.0, formaEntregaInsertion, clienteInsertion, formaPagamentoInsertion),
//                        new Pedido(3, LocalDate.parse("2024-08-12"), 120.0, '1', 60.0, formaEntregaInsertion, clienteInsertion, formaPagamentoInsertion)
//                );
//
//                Mockito.when(pedidoRepository.findAll()).thenReturn(pedidos);
//
//                List<Pedido> pedidosRetornados = pedidoService.listar();
//
//                Assertions.assertEquals(pedidos.size(), pedidosRetornados.size());
//                Assertions.assertEquals(3, pedidosRetornados.size());
//                Assertions.assertFalse(pedidosRetornados.isEmpty());
//                Assertions.assertEquals(pedidos.get(0).getIdPedido(), pedidosRetornados.get(0).getIdPedido());
//                Assertions.assertEquals(pedidos.get(0).getDtPedido(), pedidosRetornados.get(0).getDtPedido());
//                Assertions.assertEquals(pedidos.get(0).getVlPedido(), pedidosRetornados.get(0).getVlPedido());
//                Assertions.assertEquals(pedidos.get(0).getStatus(), pedidosRetornados.get(0).getStatus());
//                Assertions.assertEquals(pedidos.get(0).getValorSinal(), pedidosRetornados.get(0).getValorSinal());
//                Assertions.assertEquals(pedidos.get(0).getFormaEntrega(), pedidosRetornados.get(0).getFormaEntrega());
//                Assertions.assertEquals(pedidos.get(0).getCliente(), pedidosRetornados.get(0).getCliente());
//                Assertions.assertEquals(pedidos.get(0).getFormaPagamento(), pedidosRetornados.get(0).getFormaPagamento());
//                Mockito.verify(pedidoRepository, Mockito.times(1)).findAll();
//
//            }

            @Test@DisplayName("2.1.2 - Deve retornara lista vazia de pedidos")
            void retornaListaVazia(){

                List<Pedido> pedidos = new ArrayList<>();

                Mockito.when(pedidoRepository.findAll()).thenReturn(pedidos);

                List<Pedido> pedidosRetornados = pedidoService.listar();

                Assertions.assertTrue(pedidosRetornados.isEmpty());
                Mockito.verify(pedidoRepository, Mockito.times(1)).findAll();

            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem por id")
        public class CenarioFindById{

//            @Test
//            @DisplayName("2.2.1 - Deve retornar o pedido por id")
//            void retornaPedidoPorId(){
//
//                FormaEntrega formaEntregaInsertion = new FormaEntrega(1, "Motoboy", null);
//                FormaPagamento formaPagamentoInsertion = new FormaPagamento(1, "PIX", null);
//                Cliente clienteInsertion = new Cliente(1, "Leandro", "11996570337", LocalDate.parse("2002-04-28"));
//                Pedido pedido = new Pedido(1, LocalDate.parse("2024-04-28"), 200.0, '1', 100.0, formaEntregaInsertion, clienteInsertion, formaPagamentoInsertion);
//
//                Integer idBusca = 1;
//
//                Mockito.when(pedidoRepository.findById(idBusca)).thenReturn(Optional.of(pedido));
//
//                Pedido pedidoEncontrado = pedidoService.encontrarPorId(idBusca);
//
//                Assertions.assertEquals(idBusca, pedidoEncontrado.getIdPedido());
//                Mockito.verify(pedidoRepository, Mockito.times(1)).findById(idBusca);
//
//            }

            @Test
            @DisplayName("2.2.2 - Deve retornar NaoEncontradoException caso não exista o id")
            void retornaExceptionCasoNaoExite(){

                Integer idBusca = 100;

                Mockito.when(pedidoRepository.findById(idBusca)).thenReturn(Optional.empty());
                NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                        () -> pedidoService.encontrarPorId(idBusca));

                Assertions.assertEquals("Pedido não encontrado", exception.getLocalizedMessage());

                Mockito.verify(pedidoRepository, Mockito.times(1)).findById(idBusca);

            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualizar")
    public class CenarioPut{

//        @Test
//        @DisplayName("3.1 - Deve retornar o Objeto Atualizado, quando passado o pedido de atualização correto")
//        void retornaObjetoAtualizado(){
//
//            FormaEntrega formaEntregaAntigo = new FormaEntrega(1, "Motoboy", null);
//            FormaPagamento formaPagamentoAntigo = new FormaPagamento(1, "PIX", null);
//            Cliente clienteAntigo = new Cliente(1, "Leandro", "11996570337", LocalDate.parse("2002-04-28"));
//            Pedido pedidoAntigo = new Pedido(1, LocalDate.parse("2024-04-28"), 200.0, '1', 100.0, formaEntregaAntigo, clienteAntigo, formaPagamentoAntigo);
//
//            FormaEntrega formaEntregaAtualizado = new FormaEntrega(2, "Retirada", null);
//            FormaPagamento formaPagamentoAtualizado = new FormaPagamento(2, "TED", null);
//            Cliente clienteAtualizado = new Cliente(2, "Gustavo", "1137814523", LocalDate.parse("2004-06-17"));
//            Pedido pedidoAtualizado = new Pedido(1, LocalDate.parse("2024-04-30"), 220.0, '2', 100.0, formaEntregaAtualizado, clienteAtualizado, formaPagamentoAtualizado);
//
//            Mockito.when(pedidoRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(pedidoAntigo));
//
//            Pedido PedidoEncontrado = pedidoService.encontrarPorId(Mockito.any(Integer.class));
//
//            Assertions.assertEquals(PedidoEncontrado.getIdPedido(), pedidoAntigo.getIdPedido());
//            Assertions.assertEquals(PedidoEncontrado.getDtPedido(), pedidoAntigo.getDtPedido());
//            Assertions.assertEquals(PedidoEncontrado.getVlPedido(), pedidoAntigo.getVlPedido());
//            Assertions.assertEquals(PedidoEncontrado.getStatus(), pedidoAntigo.getStatus());
//            Assertions.assertEquals(PedidoEncontrado.getValorSinal(), pedidoAntigo.getValorSinal());
//            Assertions.assertEquals(PedidoEncontrado.getFormaEntrega(), pedidoAntigo.getFormaEntrega());
//            Assertions.assertEquals(PedidoEncontrado.getCliente(), pedidoAntigo.getCliente());
//            Assertions.assertEquals(PedidoEncontrado.getFormaPagamento(), pedidoAntigo.getFormaPagamento());
//
//            Mockito.when(formaEntregaService.encontrarPorId(2)).thenReturn(formaEntregaAtualizado);
//            Mockito.when(clienteService.encontrarPorId(2)).thenReturn(clienteAtualizado);
//            Mockito.when(formaPagamentoService.encontrarPorId(2)).thenReturn(formaPagamentoAtualizado);
//            Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class))).thenReturn(pedidoAtualizado);
//
//            Pedido pedidoRetornado = pedidoService.atualizar(Mockito.any(Integer.class),pedidoAtualizado, 2, 2, 2);
//
//            Assertions.assertEquals(pedidoRetornado.getIdPedido(), pedidoAtualizado.getIdPedido());
//            Assertions.assertEquals(pedidoRetornado.getDtPedido(), pedidoAtualizado.getDtPedido());
//            Assertions.assertEquals(pedidoRetornado.getVlPedido(), pedidoAtualizado.getVlPedido());
//            Assertions.assertEquals(pedidoRetornado.getStatus(), pedidoAtualizado.getStatus());
//            Assertions.assertEquals(pedidoRetornado.getValorSinal(), pedidoAtualizado.getValorSinal());
//            Assertions.assertEquals(pedidoRetornado.getFormaEntrega(), pedidoAtualizado.getFormaEntrega());
//            Assertions.assertEquals(pedidoRetornado.getCliente(), pedidoAtualizado.getCliente());
//            Assertions.assertEquals(pedidoRetornado.getFormaPagamento(), pedidoAtualizado.getFormaPagamento());
//
//            Mockito.verify(pedidoRepository, Mockito.times(2)).findById(Mockito.any(Integer.class));
//            Mockito.verify(pedidoRepository, Mockito.times(1)).save(Mockito.any(Pedido.class));
//
//        }

        @Test
        @DisplayName("3.2 - Deve throwlar BadRequestException, quando passado o id de atualização incorreto")
        void throwlaBadRequest(){

            Integer idBusca = 1;

            Mockito.when(pedidoRepository.findById(idBusca)).thenReturn(Optional.empty());

            NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                    () -> pedidoService.encontrarPorId(idBusca));

            Assertions.assertEquals("Pedido não encontrado", exception.getLocalizedMessage());

            Mockito.verify(pedidoRepository, Mockito.times(1)).findById(idBusca);

        }
    }


    @Nested
    @DisplayName("4 - Cenários de Deletar")
    public class CenarioDelete{
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso quando id do pedido passado corretamente")
        void retornaStringSucesso(){

            Mockito.when(pedidoRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = pedidoService.deletar(Mockito.any(Integer.class));

            Assertions.assertEquals("Pedido deletado com sucesso!", retorno);
            Mockito.verify(pedidoRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(pedidoRepository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));

        }
        @Test
        @DisplayName("4.2 - Deve retornar BadRequestException quando id do pedido passado incorretamente")
        void throwlaBadRequest(){

            Mockito.when(pedidoRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> pedidoService.deletar(Mockito.any(Integer.class)));

            Assertions.assertEquals(exception.getLocalizedMessage(), "Pedido enviado de forma incorreta!");
            Mockito.verify(pedidoRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));

        }
    }


}