package school.sptech.apimiseenplace.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.apimiseenplace.entity.UnidadeMedida;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.UnidadeMedidaRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UnidadeMedidaServiceTest {

    @InjectMocks
    private UnidadeMedidaService service;

    @Mock
    private UnidadeMedidaRepository repository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{

        @Test
        @DisplayName("1.1 - Deve Retornar Unidade Medida cadastrado, quando unidadeMedida passado corretamento")
        void deveRetornarUnidadeMedida(){
            UnidadeMedida unidadeMedidaEnviada = new UnidadeMedida(null, "Kg", Collections.emptyList());
            UnidadeMedida unidadeMedidaRecebida = new UnidadeMedida(1, "Kg", Collections.emptyList());

            Mockito.when(repository.save(Mockito.any(UnidadeMedida.class))).thenReturn(unidadeMedidaRecebida);

            UnidadeMedida unidadeMedidaSalva = service.criar(unidadeMedidaEnviada);

            assertEquals(unidadeMedidaRecebida.getIdUnidadeMedida(), unidadeMedidaSalva.getIdUnidadeMedida());
            assertEquals(unidadeMedidaRecebida.getUnidadeMedida(), unidadeMedidaSalva.getUnidadeMedida());

            Mockito.verify(repository, Mockito.times(1)).save(unidadeMedidaEnviada);
        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando unidadeMedida passado como null")
        void deveThrowlarException(){
            Exception exception = assertThrows(Exception.class,
                    () -> service.criar(null));

            assertEquals("Unidade Medida enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(UnidadeMedida.class));
        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem {
        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioListagemFindAll {
            @Test
            @DisplayName("2.1.1 - Deve Retornar Lista de Unidade Medida, quando existir")
            void deveRetornarListaUnidadeMedida() {
                UnidadeMedida unidadeMedida = new UnidadeMedida(1, "Kg", Collections.emptyList());
                Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(unidadeMedida));

                var listaUnidadeMedida = service.listar();

                assertNotNull(listaUnidadeMedida);
                assertEquals(1, listaUnidadeMedida.size());
                assertEquals(unidadeMedida.getIdUnidadeMedida(), listaUnidadeMedida.get(0).getIdUnidadeMedida());
                assertEquals(unidadeMedida.getUnidadeMedida(), listaUnidadeMedida.get(0).getUnidadeMedida());
                Mockito.verify(repository, Mockito.times(1)).findAll();
            }

            @Test
            @DisplayName("2.1.2 - Deve Retornar Lista Vazia, quando não existir")
            void deveRetornarListaVazia() {
                Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

                var listaUnidadeMedida = service.listar();

                assertNotNull(listaUnidadeMedida);
                assertEquals(0, listaUnidadeMedida.size());
                Mockito.verify(repository, Mockito.times(1)).findAll();
            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem FindById")
        public class CenarioListagemFindById {
            @Test
            @DisplayName("2.2.1 - Deve Retornar Unidade Medida, quando existir")
            void deveRetornarUnidadeMedida() {
                UnidadeMedida unidadeMedida = new UnidadeMedida(1, "Kg", Collections.emptyList());

                Mockito.when(repository.findById(1)).thenReturn(Optional.of(unidadeMedida));

                var unidadeMedidaEncontrada = service.buscarPorId(1);

                assertNotNull(unidadeMedidaEncontrada);
                assertEquals(unidadeMedida.getIdUnidadeMedida(), unidadeMedidaEncontrada.getIdUnidadeMedida());
                assertEquals(unidadeMedida.getUnidadeMedida(), unidadeMedidaEncontrada.getUnidadeMedida());
                Mockito.verify(repository, Mockito.times(1)).findById(1);
            }

            @Test
            @DisplayName("2.2.2 - Deve Retornar null, quando não existir unidade medida")
            void deveRetornarNull() {
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(Optional.empty());

                NaoEncontradoException exception = assertThrows(NaoEncontradoException.class,
                        () -> service.buscarPorId(idBusca));

                assertEquals("Unidade Medida não encontrado", exception.getLocalizedMessage());
                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualização")
    public class CenarioAtualizacao {
        @Test
        @DisplayName("3.1 - Deve Retornar Unidade Medida Atualizada, quando existir")
        void deveRetornarUnidadeMedidaAtualizada() {
            UnidadeMedida unidadeMedida = new UnidadeMedida(1, "Kg", Collections.emptyList());
            UnidadeMedida unidadeMedidaAtualizada = new UnidadeMedida(1, "g", Collections.emptyList());

            Mockito.when(repository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(unidadeMedida));

            UnidadeMedida unidadeMedidaEncontrada = service.buscarPorId(Mockito.any(Integer.class));

            assertEquals(unidadeMedidaEncontrada.getIdUnidadeMedida(), unidadeMedidaEncontrada.getIdUnidadeMedida());
            assertEquals(unidadeMedidaEncontrada.getUnidadeMedida(), unidadeMedidaEncontrada.getUnidadeMedida());

            Mockito.when(repository.save(Mockito.any(UnidadeMedida.class))).thenReturn(unidadeMedidaAtualizada);
            UnidadeMedida unidadeMedidaRetornada = service.atualizar(Mockito.any(Integer.class), unidadeMedidaAtualizada);

            assertEquals(unidadeMedidaAtualizada.getIdUnidadeMedida(), unidadeMedidaRetornada.getIdUnidadeMedida());
            assertEquals(unidadeMedidaAtualizada.getUnidadeMedida(), unidadeMedidaRetornada.getUnidadeMedida());

            Mockito.verify(repository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(UnidadeMedida.class));
        }

        @Test
        @DisplayName("3.2 - Deve Retornar NaoEncontradoException, quando não existir unidade medida com o id passado")
        void deveRetornarNaoEncontradoException() {
            UnidadeMedida unidadeMedida = new UnidadeMedida(1, "Kg", Collections.emptyList());
            UnidadeMedida unidadeMedidaAtualizada = new UnidadeMedida(1, "Kg", Collections.emptyList());

            Mockito.when(repository.findById(1)).thenReturn(Optional.empty());

            NaoEncontradoException exception = assertThrows(NaoEncontradoException.class,
                    () -> service.atualizar(1, unidadeMedidaAtualizada));

            assertEquals("Unidade Medida não encontrado", exception.getLocalizedMessage());
            Mockito.verify(repository, Mockito.times(1)).findById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(UnidadeMedida.class));
        }
    }

    @Nested
    @DisplayName("4 - Cenários de Exclusão")
    public class CenarioExclusao {
        @Test
        @DisplayName("4.1 - Deve Retornar String, quando existir unidade medida")
        void deveDeletarUnidadeMedida() {
            Mockito.when(repository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = service.deletar(Mockito.any(Integer.class));

            assertEquals("Unidade Medida deletada com sucesso!", retorno);
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));
        }

        @Test
        @DisplayName("4.2 - Deve Retornar NaoEncontradoException, quando não existir unidade medida com o id passado")
        void deveRetornarNaoEncontradoException() {
            Mockito.when(repository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            NaoEncontradoException exception = assertThrows(NaoEncontradoException.class,
                    () -> service.deletar(Mockito.any(Integer.class)));

            assertEquals("Unidade de Medida não encontrado", exception.getLocalizedMessage());
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
        }
    }
}