package school.sptech.apimiseenplace.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.apimiseenplace.entity.Massa;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.MassaRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MassaServiceTest {

    @InjectMocks
    private MassaService service;

    @Mock
    private MassaRepository repository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao {
        @Test
        @DisplayName("1.1 - Deve Retornar Massa cadastrada, quando massa passada corretamente")
        void deveRetornarMassa() {
            Massa massaEnviada = new Massa(null, "Massa de Pizza", Collections.emptyList(), Collections.emptyList());
            Massa massaRecebida = new Massa(1, "Massa de Pizza", Collections.emptyList(), Collections.emptyList());

            Mockito.when(repository.save(Mockito.any(Massa.class))).thenReturn(massaRecebida);

            Massa massaSalva = service.criar(massaEnviada);

            assertEquals(massaRecebida.getIdMassa(), massaSalva.getIdMassa());
            assertEquals(massaRecebida.getNome(), massaSalva.getNome());
            assertEquals(massaRecebida.getPersonalizacoes(), massaSalva.getPersonalizacoes());
            assertEquals(massaRecebida.getProdutos(), massaSalva.getProdutos());

            Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Massa.class));
        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando massa passada como null")
        void deveThrowlarException() {
            BadRequestException exception = assertThrows(
                    BadRequestException.class,
                    () -> service.criar(null)
            );

            assertEquals("Massa enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Massa.class));
        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem {
        @Nested
        @DisplayName("2.1 - Cenarios de Listagem FindAll")
        public class CenarioListagemFindAll {
            @Test
            @DisplayName("2.1.1 - Deve retornar lista de massas, quando existir massas cadastradas")
            void deveRetornarListaMassas() {
                Massa massa = new Massa(1, "Massa de Pizza", Collections.emptyList(), Collections.emptyList());
                Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(massa));

                var listaMassa = service.listar();

                assertNotNull(listaMassa);
                assertEquals(1, listaMassa.size());
                assertEquals(massa.getIdMassa(), listaMassa.get(0).getIdMassa());
                assertEquals(massa.getNome(), listaMassa.get(0).getNome());
                assertEquals(massa.getPersonalizacoes(), listaMassa.get(0).getPersonalizacoes());
                assertEquals(massa.getProdutos(), listaMassa.get(0).getProdutos());
                Mockito.verify(repository, Mockito.times(1)).findAll();
            }

            @Test
            @DisplayName("2.1.2 - Deve retornar lista vazia de massas, quando não existir massas cadastradas")
            void deveRetornarListaVaziaMassas() {
                Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

                var listaMassa = service.listar();

                assertNotNull(listaMassa);
                assertEquals(0, listaMassa.size());
                Mockito.verify(repository, Mockito.times(1)).findAll();
            }
        }

        @Nested
        @DisplayName("2.2 - Cenarios de Listagem FindById")
        public class CenarioFindById {
            @Test
            @DisplayName("2.2.1 - Deve retornar uma massa, quando existir massa cadastrada")
            void deveRetornarMassa() {
                Massa massa = new Massa(1, "Massa de Pizza", Collections.emptyList(), Collections.emptyList());
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(Optional.of(massa));

                Massa massaEncontrada = service.encontrarPorId(idBusca);

                assertEquals(idBusca, massaEncontrada.getIdMassa());
                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }

            @Test
            @DisplayName("2.2.2 - Deve throwlar NaoEncontradoException, quando id passado nao exista")
            void deveThrowlarBadRequestException() {
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(Optional.empty());

                NaoEncontradoException exception = assertThrows(
                        NaoEncontradoException.class,
                        () -> service.encontrarPorId(idBusca)
                );

                assertEquals("Massa não encontrado", exception.getLocalizedMessage());
                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualização")
    public class CenarioAtualizacao {
        @Test
        @DisplayName("3.1 - Deve retornar Massa atualizada, quando massa passada corretamente")
        void deveRetornarMassa() {
            Massa massa = new Massa(1, "Massa de Pizza", Collections.emptyList(), Collections.emptyList());
            Massa massaAtualizada = new Massa(1, "Massa de Pão", Collections.emptyList(), Collections.emptyList());

            Mockito.when(repository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(massa));

            Massa masaEncontrada = service.encontrarPorId(Mockito.any(Integer.class));

            assertEquals(massa.getIdMassa(), masaEncontrada.getIdMassa());
            assertEquals(massa.getNome(), masaEncontrada.getNome());

            Mockito.when(repository.save(Mockito.any(Massa.class))).thenReturn(massaAtualizada);

            Massa massaRetornada = service.atualizar(massaAtualizada, Mockito.any(Integer.class));

            assertEquals(massaAtualizada.getIdMassa(), massaRetornada.getIdMassa());
            assertEquals(massaAtualizada.getNome(), massaRetornada.getNome());

            Mockito.verify(repository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Massa.class));
        }

        @Test
        @DisplayName("3.2 - Deve throwlar NaoEncontradoException, quando massa passada como null")
        void deveThrowlarException() {
            Massa massa = new Massa(1, "Massa de Pizza", Collections.emptyList(), Collections.emptyList());
            Massa massaAtualizada = new Massa(1, "Massa de Pão", Collections.emptyList(), Collections.emptyList());

            Mockito.when(repository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

            NaoEncontradoException exception = assertThrows(
                    NaoEncontradoException.class,
                    () -> service.atualizar(massaAtualizada, Mockito.any(Integer.class))
            );

            assertEquals("Massa não encontrado", exception.getLocalizedMessage());

            Mockito.verify(repository, Mockito.times(1)).findById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Massa.class));
        }
    }

    @Nested
    @DisplayName("4 - Cenários de Exclusão")
    public class CenarioExclusao {
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso quando id da Massa passado corretamente")
        void deveDeletarFormaPagamento() {
            Mockito.when(repository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = service.deletar(Mockito.any(Integer.class));

            assertEquals(retorno, "Massa deletada com sucesso!");
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));
        }

        @Test
        @DisplayName("4.2 - Deve retornar NaoEncontrado caso não exista o id")
        void deveRetornarNaoEncontradoException() {
            Mockito.when(repository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            NaoEncontradoException exception = assertThrows(
                    NaoEncontradoException.class,
                    () -> service.deletar(Mockito.any(Integer.class))
            );

            assertEquals("Massa não encontrado", exception.getLocalizedMessage());
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
        }
    }
}