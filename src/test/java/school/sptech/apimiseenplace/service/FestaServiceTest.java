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
import school.sptech.apimiseenplace.entity.Endereco;
import school.sptech.apimiseenplace.entity.Festa;
import school.sptech.apimiseenplace.entity.Pedido;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.EnderecoRepository;
import school.sptech.apimiseenplace.repository.FestaRepository;
import school.sptech.apimiseenplace.repository.PedidoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FestaServiceTest {

    @InjectMocks
    private FestaService festaService;

    @Mock
    private PedidoService pedidoService;
    @Mock
    private EnderecoService enderecoService;
    @Mock
    private FestaRepository festaRepository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{

        @Test
        @DisplayName("1.1 - Deve Retornar festa cadastrada, quando nome passado corretamento")
        void deveRetornarFesta(){

            Endereco enderecoInsertion = new Endereco(1, "Rua das Flores", "Apto 101", "12345-678", 100);
            Pedido pedidoInsertion = new Pedido(1, LocalDate.of(2024, 1, 15), 150.00, 'A', 50.00, null, null, null);
            Festa festaInsertion = new Festa(null, pedidoInsertion, enderecoInsertion);

            Festa festaEsperada = new Festa(1, pedidoInsertion, enderecoInsertion);

            Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(festaEsperada);
            Mockito.when(pedidoService.encontrarPorId(1)).thenReturn(pedidoInsertion);
            Mockito.when(enderecoService.encontrarPorId(1)).thenReturn(enderecoInsertion);

            Festa festaSalva = festaService.cadastrar(festaInsertion, 1, 1);

            Assertions.assertEquals(festaEsperada.getIdFesta(), festaSalva.getIdFesta());
            Assertions.assertEquals(festaEsperada.getPedido(), festaSalva.getPedido());
            Assertions.assertEquals(festaEsperada.getEndereco(), festaSalva.getEndereco());
            Mockito.verify(festaRepository, Mockito.times(1)).save(festaInsertion);

        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando festa passada como null")
        void deveThrowlarException(){

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> festaService.cadastrar(null, null, null));

            Assertions.assertEquals("Festa enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(festaRepository, Mockito.times(0)).save(Mockito.any(Festa.class));

        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{

        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll{
            @Test
            @DisplayName("2.1.1 - Deve retornar lista de festas")
            void deveRetornarListaFesta(){

                Endereco enderecoInsertion = new Endereco(1, "Rua das Flores", "Apto 101", "12345-678", 100);
                Pedido pedidoInsertion = new Pedido(1, LocalDate.of(2024, 1, 15), 150.00, 'A', 50.00, null, null, null);

                List<Festa> festas = List.of(
                        new Festa(1, pedidoInsertion, enderecoInsertion),
                        new Festa(2, pedidoInsertion, enderecoInsertion),
                        new Festa(3, pedidoInsertion, enderecoInsertion)
                );

                Mockito.when(festaRepository.findAll()).thenReturn(festas);

                List<Festa> festasRetornadas = festaService.listar();

                Assertions.assertEquals(festas.size(), festasRetornadas.size());
                Assertions.assertEquals(3, festasRetornadas.size());
                Assertions.assertFalse(festasRetornadas.isEmpty());
                Assertions.assertEquals(festas.get(0).getIdFesta(), festasRetornadas.get(0).getIdFesta());
                Assertions.assertEquals(festas.get(0).getPedido(), festasRetornadas.get(0).getPedido());
                Assertions.assertEquals(festas.get(0).getEndereco(), festasRetornadas.get(0).getEndereco());
                Mockito.verify(festaRepository, Mockito.times(1)).findAll();

            }

            @Test@DisplayName("2.1.2 - Deve retornara lista vazia de festas")
            void retornaListaVazia(){

                List<Festa> festas = new ArrayList<>();

                Mockito.when(festaRepository.findAll()).thenReturn(festas);

                List<Festa> festasRetornadas = festaService.listar();

                Assertions.assertTrue(festasRetornadas.isEmpty());
                Mockito.verify(festaRepository, Mockito.times(1)).findAll();

            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem por id")
        public class CenarioFindById{

            @Test
            @DisplayName("2.2.1 - Deve retornar o festa por id")
            void retornaFestaPorId(){

                Endereco enderecoInsertion = new Endereco(1, "Rua das Flores", "Apto 101", "12345-678", 100);
                Pedido pedidoInsertion = new Pedido(1, LocalDate.of(2024, 1, 15), 150.00, 'A', 50.00, null, null, null);
                Festa festa = new Festa(1, pedidoInsertion, enderecoInsertion);
                Integer idBusca = 1;

                Mockito.when(festaRepository.findById(idBusca)).thenReturn(Optional.of(festa));

                Festa festaEncontrada = festaService.encontrarPorId(idBusca);

                Assertions.assertEquals(idBusca, festaEncontrada.getIdFesta());
                Mockito.verify(festaRepository, Mockito.times(1)).findById(idBusca);

            }

            @Test
            @DisplayName("2.2.2 - Deve retornar NaoEncontradoException caso não exista o id")
            void retornaExceptionCasoNaoExite(){

                Integer idBusca = 100;

                Mockito.when(festaRepository.findById(idBusca)).thenReturn(Optional.empty());
                NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                        () -> festaService.encontrarPorId(idBusca));

                Assertions.assertEquals("Festa não encontrado", exception.getLocalizedMessage());

                Mockito.verify(festaRepository, Mockito.times(1)).findById(idBusca);

            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualizar")
    public class CenarioPut{

        @Test
        @DisplayName("3.1 - Deve retornar o Objeto Atualizado, quando passado a festa de atualização correto")
        void retornaObjetoAtualizado(){

            Endereco enderecoAntigo = new Endereco(1, "Rua das Flores", "Apto 101", "12345-678", 100);
            Pedido pedidoAntigo = new Pedido(1, LocalDate.of(2024, 1, 15), 150.00, 'A', 50.00, null, null, null);
            Festa festaAntiga = new Festa(1, pedidoAntigo, enderecoAntigo);

            Endereco enderecoAtualizado = new Endereco(2, "Praça da Liberdade", "Bloco B", "34567-890", 300);
            Pedido pedidoAtualizado = new Pedido(2, LocalDate.of(2024, 2, 20), 200.00, 'B', 75.00, null, null, null);
            Festa festaAtualizada = new Festa(1, pedidoAtualizado, enderecoAtualizado);

            Mockito.when(festaRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(festaAntiga));

            Festa festaEncontrada = festaService.encontrarPorId(Mockito.any(Integer.class));

            Assertions.assertEquals(festaEncontrada.getIdFesta(), festaAntiga.getIdFesta());
            Assertions.assertEquals(festaEncontrada.getPedido(), festaAntiga.getPedido());
            Assertions.assertEquals(festaEncontrada.getEndereco(), festaAntiga.getEndereco());

            Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(festaAtualizada);
            Mockito.when(pedidoService.encontrarPorId(Mockito.any(Integer.class))).thenReturn(pedidoAtualizado);
            Mockito.when(enderecoService.encontrarPorId(Mockito.any(Integer.class))).thenReturn(enderecoAtualizado);

            Festa festaRetornada = festaService.atualizar(Mockito.any(Integer.class),festaAtualizada, 2, 2);

            Assertions.assertEquals(festaRetornada.getIdFesta(), festaAtualizada.getIdFesta());
            Assertions.assertEquals(festaRetornada.getPedido(), festaAtualizada.getPedido());
            Assertions.assertEquals(festaRetornada.getEndereco(), festaAtualizada.getEndereco());

            Mockito.verify(festaRepository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(festaRepository, Mockito.times(1)).save(Mockito.any(Festa.class));

        }

        @Test
        @DisplayName("3.2 - Deve throwlar BadRequestException, quando passado o id de atualização incorreto")
        void throwlaBadRequest(){

            Integer idBusca = 1;

            Mockito.when(festaRepository.findById(idBusca)).thenReturn(Optional.empty());

            NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                    () -> festaService.encontrarPorId(idBusca));

            Assertions.assertEquals("Festa não encontrado", exception.getLocalizedMessage());

            Mockito.verify(festaRepository, Mockito.times(1)).findById(idBusca);

        }
    }


    @Nested
    @DisplayName("4 - Cenários de Deletar")
    public class CenarioDelete{
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso quando id da festa passado corretamente")
        void retornaStringSucesso(){

            Mockito.when(festaRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = festaService.deletar(Mockito.any(Integer.class));

            Assertions.assertEquals("Festa deletado com sucesso!", retorno);
            Mockito.verify(festaRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(festaRepository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));

        }
        @Test
        @DisplayName("4.2 - Deve retornar BadRequestException quando id da festa passado incorretamente")
        void throwlaBadRequest(){

            Mockito.when(festaRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> festaService.deletar(Mockito.any(Integer.class)));

            Assertions.assertEquals(exception.getLocalizedMessage(), "Festa enviado de forma incorreta!");
            Mockito.verify(festaRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));

        }
    }


}