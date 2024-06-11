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
import school.sptech.apimiseenplace.entity.FormaEntrega;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.FormaEntregaRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FormaEntregaServiceTest {

    @InjectMocks
    private FormaEntregaService service;

    @Mock
    private FormaEntregaRepository repository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{

        @Test
        @DisplayName("1.1 - Deve Retornar Forma entrega cadastrado, quando formaEntrega passado corretamento")
        void deveRetornarFormaEntrega(){
            FormaEntrega formaEntregaEnviada = new FormaEntrega(null, "Retirada", Collections.emptyList());
            FormaEntrega formaEntregaRecebida = new FormaEntrega(1, "Retirada", Collections.emptyList());

            Mockito.when(repository.save(Mockito.any(FormaEntrega.class))).thenReturn(formaEntregaRecebida);

            FormaEntrega formaEntregaSalva = service.cadastrar(formaEntregaEnviada);

            Assertions.assertEquals(formaEntregaRecebida.getIdFormaEntrega(), formaEntregaSalva.getIdFormaEntrega());
            Assertions.assertEquals(formaEntregaRecebida.getFormaEntrega(), formaEntregaSalva.getFormaEntrega());

            Mockito.verify(repository, Mockito.times(1)).save(formaEntregaEnviada);
        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando formaEntrega passado como null")
        void deveThrowlarException(){
            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> service.cadastrar(null));

            Assertions.assertEquals("Forma Entrega enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(FormaEntrega.class));
        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{
        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll{
            @Test
            @DisplayName("2.1.1 - Deve Retornar Lista de FormaEntrega, quando existir")
            void deveRetornarListaFormaEntrega(){
                FormaEntrega formaEntrega = new FormaEntrega(1, "Retirada", Collections.emptyList());
                Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(formaEntrega));

                var listaFormaEntrega = service.listar();


                assertNotNull(listaFormaEntrega);
                assertEquals(1, listaFormaEntrega.size());
                assertEquals(formaEntrega.getIdFormaEntrega(), listaFormaEntrega.get(0).getIdFormaEntrega());
                assertEquals(formaEntrega.getFormaEntrega(), listaFormaEntrega.get(0).getFormaEntrega());
                Mockito.verify(repository, Mockito.times(1)).findAll();
            }

            @Test
            @DisplayName("2.1.2 - Deve Retornar Lista Vazia, quando não existir FormaEntrega")
            void deveRetornarListaVazia(){
                Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

                var listaFormaEntrega = service.listar();

                assertNotNull(listaFormaEntrega);
                assertEquals(0, listaFormaEntrega.size());
                Mockito.verify(repository, Mockito.times(1)).findAll();
            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem FindById")
        public class CenarioFindById{
            @Test
            @DisplayName("2.2.1 - Deve Retornar FormaEntrega, quando existir")
            void deveRetornarFormaEntrega() {
                FormaEntrega formaEntrega = new FormaEntrega(1, "Retirada", Collections.emptyList());
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(Optional.of(formaEntrega));

                FormaEntrega formaEntregaEncontrada = service.encontrarPorId(idBusca);

                assertEquals(idBusca, formaEntregaEncontrada.getIdFormaEntrega());
                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }

            @Test
            @DisplayName("2.2.2 - Deve Retornar NaoEncontradoException, quando não existir FormaEntrega")
            void deveRetornarNaoEncontradoException() {
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(Optional.empty());
                NaoEncontradoException exception = Assertions.assertThrows(
                        NaoEncontradoException.class,
                        () -> service.encontrarPorId(idBusca)
                );

                assertEquals("Forma Entrega não encontrado", exception.getLocalizedMessage());
                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualização")
    public class CenarioAtualizacao{
        @Test
        @DisplayName("3.1 - Deve Retornar FormaEntrega Atualizada, quando existir")
        void deveRetornarFormaEntregaAtualizada() {
            FormaEntrega formaEntrega = new FormaEntrega(1, "Retirada", Collections.emptyList());
            FormaEntrega formaEntregaAtualizada = new FormaEntrega(1, "Entrega", Collections.emptyList());

            Mockito.when(repository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(formaEntrega));

            FormaEntrega formaEntregaEncontradaa = service.encontrarPorId(Mockito.any(Integer.class));

            assertEquals(formaEntregaEncontradaa.getIdFormaEntrega(), formaEntrega.getIdFormaEntrega());
            assertEquals(formaEntregaEncontradaa.getFormaEntrega(), formaEntrega.getFormaEntrega());

            Mockito.when(repository.save(Mockito.any(FormaEntrega.class))).thenReturn(formaEntregaAtualizada);
            FormaEntrega formaEntregarRetornada = service.atualizar(Mockito.any(Integer.class), formaEntregaAtualizada);

            assertEquals(formaEntregarRetornada.getIdFormaEntrega(), formaEntregaAtualizada.getIdFormaEntrega());
            assertEquals(formaEntregarRetornada.getFormaEntrega(), formaEntregaAtualizada.getFormaEntrega());

            Mockito.verify(repository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(FormaEntrega.class));
        }

        @Test
        @DisplayName("3.2 - Deve Retornar NaoEncontradoException, quando não existir FormaEntrega com o id passado")
        void deveRetornarNaoEncontradoException() {
            FormaEntrega formaEntrega = new FormaEntrega(1, "Retirada", Collections.emptyList());
            FormaEntrega formaEntregaAtualizada = new FormaEntrega(1, "Entrega", Collections.emptyList());

            Mockito.when(repository.findById(Mockito.any(Integer.class))).thenReturn(java.util.Optional.empty());

            NaoEncontradoException exception = Assertions.assertThrows(
                    NaoEncontradoException.class,
                    () -> service.atualizar(formaEntrega.getIdFormaEntrega(),
                            formaEntregaAtualizada
                    )
            );

            assertEquals("Forma Entrega não encontrado", exception.getLocalizedMessage());
            Mockito.verify(repository, Mockito.times(1)).findById(formaEntrega.getIdFormaEntrega());
            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(FormaEntrega.class));
        }
    }

    @Nested
    @DisplayName("4 - Cenários de Exclusão")
    public class CenarioExclusao{
        @Test
        @DisplayName("4.1 - Deve Excluir FormaEntrega, quando existir com o id passado")
        void deveDeletarFormaEntrega(){
            Mockito.when(repository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = service.deletar(Mockito.any(Integer.class));

            assertEquals(retorno, "Forma de Entrega deletada com sucesso!");
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));
        }

        @Test
        @DisplayName("4.2 - Deve Retornar NaoEncontradoException, quando não existir FormaEntrega com o id passado")
        void deveRetornarNaoEncontradoException() {
            Mockito.when(repository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            NaoEncontradoException exception = Assertions.assertThrows(
                    NaoEncontradoException.class,
                    () -> service.deletar(Mockito.any(Integer.class))
            );

            assertEquals("Forma Entrega não encontrado", exception.getLocalizedMessage());
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
        }
    }
}