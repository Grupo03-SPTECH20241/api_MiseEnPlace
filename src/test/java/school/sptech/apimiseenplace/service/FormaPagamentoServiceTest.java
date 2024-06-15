package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.apimiseenplace.entity.FormaPagamento;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.FormaPagamentoRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class FormaPagamentoServiceTest {

    @InjectMocks
    private FormaPagamentoService service;

    @Mock
    private FormaPagamentoRepository repository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao {
        @Test
        @DisplayName("1.1 - Deve Retornar Forma pagamento cadastrado, quando formaPagamento passado corretamento")
        void deveRetornarFormaPagamento() {
            FormaPagamento formaPagamentoEnviada = new FormaPagamento(null, "Dinheiro", Collections.emptyList());
            FormaPagamento formaPagamentoRecebida = new FormaPagamento(1, "Dinheiro", Collections.emptyList());

            Mockito.when(repository.save(Mockito.any(FormaPagamento.class))).thenReturn(formaPagamentoRecebida);

            FormaPagamento formaPagamentoSalva = service.criar(formaPagamentoEnviada);

            Assertions.assertEquals(formaPagamentoRecebida.getIdFormaPagamento(), formaPagamentoSalva.getIdFormaPagamento());
            Assertions.assertEquals(formaPagamentoRecebida.getFormaPagamento(), formaPagamentoSalva.getFormaPagamento());

            Mockito.verify(repository, Mockito.times(1)).save(formaPagamentoEnviada);
        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando formaPagamento passado como null")
        void deveThrowlarException() {
            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> service.criar(null));

            Assertions.assertEquals("Forma de Pagamento enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(FormaPagamento.class));
        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem {
        @Nested
        @DisplayName("2.1 - Cenaŕios de Listagem FindAll")
        public class CenarioListagemFindAll {
            @Test
            @DisplayName("2.1.1 - Deve retornar uma lista de FormaPagamento")
            void deveRetornarListaFormaPagamento() {
                FormaPagamento formaPagamento = new FormaPagamento(1, "Dinheiro", Collections.emptyList());
                Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(formaPagamento));

                var listaFormaPagamento = service.listar();

                assertNotNull(listaFormaPagamento);
                assertEquals(1, listaFormaPagamento.size());
                assertEquals(formaPagamento.getIdFormaPagamento(), listaFormaPagamento.get(0).getIdFormaPagamento());
                assertEquals(formaPagamento.getFormaPagamento(), listaFormaPagamento.get(0).getFormaPagamento());
                Mockito.verify(repository, Mockito.times(1)).findAll();
            }

            @Test
            @DisplayName("2.1.2 - Deve retornar uma lista vazia de FormaPagamento")
            void deveRetornarListaVaziaFormaPagamento() {
                Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

                var listaFormaPagamento = service.listar();

                assertNotNull(listaFormaPagamento);
                assertEquals(0, listaFormaPagamento.size());
            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem por id")
        public class CenarioFindById {
            @Test
            @DisplayName("2.2.1 - Deve retornar uma FormaPagamento")
            void deveRetornarFormaPagamento() {
                FormaPagamento formaPagamento = new FormaPagamento(1, "Dinheiro", Collections.emptyList());
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(java.util.Optional.of(formaPagamento));

                FormaPagamento formaPagamentoEncontrado = service.buscarPorId(idBusca);

                Assertions.assertEquals(idBusca, formaPagamentoEncontrado.getIdFormaPagamento());
                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }

            @Test
            @DisplayName("2.2.2 - Deve retornar NaoEncontradoException caso nao exista o id")
            void deveRetornarNaoEncontradoException() {
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(java.util.Optional.empty());

                NaoEncontradoException exception = Assertions.assertThrows(
                        NaoEncontradoException.class,
                        () -> service.buscarPorId(idBusca)
                );
                Assertions.assertEquals("Forma de Pagamento não encontrado", exception.getLocalizedMessage());
                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualização")
    public class CenarioAtualizacao {
        @Test
        @DisplayName("3.1 - Deve atualizar uma FormaPagamento")
        void deveAtualizarFormaPagamento() {
            FormaPagamento formaPagamento = new FormaPagamento(1, "Dinheiro", Collections.emptyList());
            FormaPagamento formaPagamentoAtualizada = new FormaPagamento(1, "Cartão", Collections.emptyList());

            Mockito.when(repository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(formaPagamento));

            FormaPagamento formaPagamentoEncontrada = service.buscarPorId(Mockito.any(Integer.class));

            Assertions.assertEquals(formaPagamentoEncontrada.getIdFormaPagamento(), formaPagamento.getIdFormaPagamento());
            Assertions.assertEquals(formaPagamentoEncontrada.getFormaPagamento(), formaPagamento.getFormaPagamento());

            Mockito.when(repository.save(Mockito.any(FormaPagamento.class))).thenReturn(formaPagamentoAtualizada);
            FormaPagamento formaPagamentoRetornada = service.atualizar(Mockito.any(Integer.class), formaPagamentoAtualizada);

            Assertions.assertEquals(formaPagamentoRetornada.getIdFormaPagamento(), formaPagamentoAtualizada.getIdFormaPagamento());
            Assertions.assertEquals(formaPagamentoRetornada.getFormaPagamento(), formaPagamentoAtualizada.getFormaPagamento());

            Mockito.verify(repository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(FormaPagamento.class));
        }

        @Test
        @DisplayName("3.2 - Deve retornar NaoEncontradoException caso não exista o id")
        void deveRetornarNaoEncontradoException() {
            FormaPagamento formaPagamento = new FormaPagamento(1, "Dinheiro", Collections.emptyList());
            FormaPagamento formaPagamentoAtualizada = new FormaPagamento(1, "Cartão", Collections.emptyList());

            Mockito.when(repository.findById(Mockito.any(Integer.class))).thenReturn(java.util.Optional.empty());

            NaoEncontradoException exception = Assertions.assertThrows(
                    NaoEncontradoException.class,
                    () -> service.atualizar(formaPagamento.getIdFormaPagamento(),
                            formaPagamentoAtualizada
                    )
            );

            Assertions.assertEquals("Forma de Pagamento não encontrado", exception.getLocalizedMessage());

            Mockito.verify(repository, Mockito.times(1)).findById(formaPagamento.getIdFormaPagamento());
            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(FormaPagamento.class));
        }
    }

    @Nested
    @DisplayName("4 - Cenários de Deleção")
    public class CenarioDelecao {
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso quando id da formaPagamento passado corretamente")
        void deveDeletarFormaPagamento() {
            Mockito.when(repository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = service.deletar(Mockito.any(Integer.class));

            Assertions.assertEquals(retorno, "Forma de Pagamento deletada com sucesso!");
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));
        }

        @Test
        @DisplayName("4.2 - Deve retornar NaoEncontrado caso não exista o id")
        void deveRetornarNaoEncontradoException() {
            Mockito.when(repository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            NaoEncontradoException exception = Assertions.assertThrows(
                    NaoEncontradoException.class,
                    () -> service.deletar(Mockito.any(Integer.class))
            );

            Assertions.assertEquals("Forma de Pagamento não encontrado", exception.getLocalizedMessage());
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
        }
    }
}
