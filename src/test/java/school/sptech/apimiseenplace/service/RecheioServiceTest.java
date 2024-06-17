package school.sptech.apimiseenplace.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.apimiseenplace.entity.Recheio;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.RecheioRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecheioServiceTest {

    @InjectMocks
    private RecheioService service;

    @Mock
    private RecheioRepository repository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{
        @Test
        @DisplayName("1.1 - Deve Retornar Recheio cadastrado, quando recheio passado corretamente")
        void deveRetornarRecheio(){
            Recheio recheioEnviado = new Recheio(null, "Recheio de Frango", 5.5, Collections.emptyList(), Collections.emptyList());
            Recheio recheioRecebido = new Recheio(1, "Recheio de Frango", 5.5, Collections.emptyList(), Collections.emptyList());

            Mockito.when(repository.save(Mockito.any(Recheio.class))).thenReturn(recheioRecebido);

            Recheio recheioSalvo = service.cadastrarRecheio(recheioEnviado);

            assertEquals(recheioRecebido.getIdRecheio(), recheioSalvo.getIdRecheio());
            assertEquals(recheioRecebido.getNome(), recheioSalvo.getNome());
            assertEquals(recheioRecebido.getPreco(), recheioSalvo.getPreco());

            Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Recheio.class));
        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando recheio passado como null")
        void deveThrowlarException(){
            BadRequestException exception = assertThrows(
                    BadRequestException.class,
                    () -> service.cadastrarRecheio(null)
            );

            assertEquals("Recheio enviado de forma incorreta!", exception.getLocalizedMessage());
            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Recheio.class));
        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{
        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioListagemFindAll{
            @Test
            @DisplayName("2.1.1 - Deve Retornar Lista de Recheios, quando existirem recheios cadastrados")
            void deveRetornarListaRecheios(){
                Recheio recheio = new Recheio(1, "Recheio de Frango", 5.5, Collections.emptyList(), Collections.emptyList());
                Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(recheio));

                var listaRecheios = service.listarRecheios();

                assertNotNull(listaRecheios);
                assertEquals(1, listaRecheios.size());
                assertEquals(recheio.getIdRecheio(), listaRecheios.get(0).getIdRecheio());
                assertEquals(recheio.getNome(), listaRecheios.get(0).getNome());
                assertEquals(recheio.getPreco(), listaRecheios.get(0).getPreco());

                Mockito.verify(repository, Mockito.times(1)).findAll();
            }

            @Test
            @DisplayName("2.1.2 - Deve Retornar Lista Vazia, quando não existirem recheios cadastrados")
            void deveRetornarListaVazia(){
                Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

                var listaRecheios = service.listarRecheios();

                assertNotNull(listaRecheios);
                assertEquals(0, listaRecheios.size());
                Mockito.verify(repository, Mockito.times(1)).findAll();
            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem FindById")
        public class CenarioFindById{
            @Test
            @DisplayName("2.2.1 - Deve Retornar Recheio, quando existir recheio cadastrado")
            void deveRetornarRecheio(){
                Recheio recheio = new Recheio(1, "Recheio de Frango", 5.5, Collections.emptyList(), Collections.emptyList());
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(Optional.of(recheio));

                Recheio recheioEncontrado = service.encontrarPorId(idBusca);

                assertEquals(idBusca, recheioEncontrado.getIdRecheio());
                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }

            @Test
            @DisplayName("2.2.2 - Deve throwlar NaoEncontradoException, quando id passado não exista")
            void deveThrowlarException(){
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(Optional.empty());

                NaoEncontradoException exception =  assertThrows(
                        NaoEncontradoException.class,
                        () -> service.encontrarPorId(idBusca)
                );

                assertEquals("Recheio não encontrado", exception.getLocalizedMessage());
                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualização")
    public class CenarioAtualizacao{
        @Test
        @DisplayName("3.1 - Deve Retornar Recheio Atualizado, quando recheio passado corretamente")
        void deveRetornarRecheio(){
            Recheio recheio = new Recheio(1, "Recheio de Frango", 5.5, Collections.emptyList(), Collections.emptyList());
            Recheio recheioAtualizado = new Recheio(1, "Recheio de Frango com Catupiry", 6.5, Collections.emptyList(), Collections.emptyList());

            Mockito.when(repository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(recheio));

            Recheio recheioEncontrado = service.encontrarPorId(Mockito.any(Integer.class));

            assertEquals(recheio.getIdRecheio(), recheioEncontrado.getIdRecheio());
            assertEquals(recheio.getNome(), recheioEncontrado.getNome());
            assertEquals(recheio.getPreco(), recheioEncontrado.getPreco());

            Mockito.when(repository.save(Mockito.any(Recheio.class))).thenReturn(recheioAtualizado);

            Recheio recheioRetornado = service.atualizarRecheio(Mockito.any(Integer.class), recheioAtualizado);

            assertEquals(recheioAtualizado.getIdRecheio(), recheioRetornado.getIdRecheio());
            assertEquals(recheioAtualizado.getNome(), recheioRetornado.getNome());
            assertEquals(recheioAtualizado.getPreco(), recheioRetornado.getPreco());

            Mockito.verify(repository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Recheio.class));
        }

        @Test
        @DisplayName("3.2 - Deve throwlar NaoEncontradoException, quando recheio passado como null")
        void deveThrowlarException(){
            Recheio recheio = new Recheio(1, "Recheio de Frango", 5.5, Collections.emptyList(), Collections.emptyList());
            Recheio recheioAtualizado = new Recheio(1, "Recheio de Frango com Catupiry", 6.5, Collections.emptyList(), Collections.emptyList());

            Mockito.when(repository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

            NaoEncontradoException exception = assertThrows(
                    NaoEncontradoException.class,
                    () -> service.atualizarRecheio(Mockito.any(Integer.class), recheioAtualizado)
            );

            assertEquals("Recheio não encontrado", exception.getLocalizedMessage());

            Mockito.verify(repository, Mockito.times(1)).findById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Recheio.class));
        }
    }

    @Nested
    @DisplayName("4 - Cenários de Exclusão")
    public class CenarioExclusao{
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso, quando id passado corretamente")
        void deveExcluirRecheio(){
            Mockito.when(repository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = service.deletarRecheioPorId(Mockito.any(Integer.class));

            assertEquals(retorno, "Recheio deletado com sucesso!");
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));
        }

        @Test
        @DisplayName("4.2 - Deve throwlar NaoEncontradoException, quando id passado não exista")
        void deveThrowlarException(){
            Mockito.when(repository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            NaoEncontradoException exception = assertThrows(
                    NaoEncontradoException.class,
                    () -> service.deletarRecheioPorId(Mockito.any(Integer.class))
            );

            assertEquals("Recheio não encontrado", exception.getLocalizedMessage());
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
        }
    }

}