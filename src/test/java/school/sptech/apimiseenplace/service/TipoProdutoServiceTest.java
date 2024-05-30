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
import school.sptech.apimiseenplace.entity.TipoProduto;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.TipoProdutoRepository;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class TipoProdutoServiceTest {

    @InjectMocks
    private TipoProdutoService service;

    @Mock
    private TipoProdutoRepository repository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{

        @Test
        @DisplayName("1.1 - Deve Retornar Tipo produto cadastrado, quando nome passado corretamento")
        void deveRetornarTipoProduto(){

            TipoProduto tipoProduto = new TipoProduto(null, "Salgado", Collections.emptyList());
            TipoProduto tipoProduto2 = new TipoProduto(1, "Salgado", Collections.emptyList());

            Mockito.when(repository.save(tipoProduto2)).thenReturn(tipoProduto2);

            TipoProduto tipoProdutoSalvo = service.salvar(tipoProduto);

            Assertions.assertEquals(tipoProduto2.getIdTipoProduto(), tipoProdutoSalvo.getIdTipoProduto());
            Assertions.assertEquals(tipoProduto2.getTipo(), tipoProdutoSalvo.getTipo());
            Mockito.verify(repository, Mockito.times(1)).save(tipoProduto);
        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando tipoProduto passado como null")
        void deveThrowlarException(){
            TipoProduto tipoProduto = new TipoProduto(null, null, Collections.emptyList());

            Mockito.when(repository.save(tipoProduto)).thenReturn(null);

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> service.salvar(tipoProduto));

            Assertions.assertEquals("Tipo Produto enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(repository, Mockito.times(1)).save(tipoProduto);
        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{

        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll{
            @Test
            @DisplayName("2.1.1 - Deve retornar lista de tiposProduto")
            void deveRetornarListaTipoProdutos(){

                List<TipoProduto> tiposProduto = List.of(
                        new TipoProduto(1, "Salgado", Collections.emptyList()),
                        new TipoProduto(2, "Bolo", Collections.emptyList()),
                        new TipoProduto(3, "Pirulito", Collections.emptyList())
                );

                Mockito.when(repository.findAll()).thenReturn(tiposProduto);

                List<TipoProduto> tiposProdutoRetornados = service.listar();

                Assertions.assertEquals(tiposProduto.size(), tiposProdutoRetornados.size());
                Assertions.assertEquals(3, tiposProdutoRetornados.size());
                Assertions.assertFalse(tiposProdutoRetornados.isEmpty());
                Assertions.assertEquals(tiposProduto.get(0).getIdTipoProduto(), tiposProdutoRetornados.get(0).getIdTipoProduto());
                Assertions.assertEquals(tiposProduto.get(0).getTipo(), tiposProdutoRetornados.get(0).getTipo());
                Mockito.verify(repository, Mockito.times(1)).findAll();
            }

            @Test@DisplayName("2.1.2 - Deve retornara lista vazia de tiposProduto")
            void retornaListaVazia(){
                List<TipoProduto> tiposProduto = new ArrayList<>();

                Mockito.when(repository.findAll()).thenReturn(tiposProduto);

                List<TipoProduto> tiposProdutoRetornados = service.listar();

                Assertions.assertTrue(tiposProdutoRetornados.isEmpty());
                Mockito.verify(repository, Mockito.times(1)).findAll();
            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem por id")
        public class CenarioFindById{

            @Test
            @DisplayName("2.2.1 - Deve retornar o tipoProduto por id")
            void retornaTipoProdutoPorId(){

                TipoProduto tipoProduto = new TipoProduto(1, "Salgado", Collections.emptyList());
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(Optional.of(tipoProduto));

                TipoProduto tipoProdutoEncontrado = service.buscarPorId(idBusca);

                Assertions.assertEquals(idBusca, tipoProdutoEncontrado.getIdTipoProduto());
                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }

            @Test
            @DisplayName("2.2.2 - Deve retornar NaoEncontradoException caso não exista o id")
            void retornaExceptionCasoNaoExite(){
                Integer idBusca = 100;

                Mockito.when(repository.findById(idBusca)).thenReturn(Optional.empty());
                NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                        () -> service.buscarPorId(idBusca));

                Assertions.assertEquals("Tipo Produto não encontrado", exception.getLocalizedMessage());

                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualizar")
    public class CenarioPut{

        @Test
        @DisplayName("3.1 - Deve retornar o Objeto Atualizado, quando passado o tipoProduto de atualização correto")
        void retornaObjetoAtualizado(){
            TipoProduto tipoProdutoAntigo = new TipoProduto(1, "Salgado", Collections.emptyList());
            TipoProduto tipoProdutoAtualizado = new TipoProduto(1, "Pirulito", Collections.emptyList());
            Integer idBusca = 1;

            Mockito.when(repository.findById(idBusca)).thenReturn(Optional.of(tipoProdutoAntigo));

            TipoProduto tipoProdutoEncontrado = service.buscarPorId(idBusca);

            Assertions.assertEquals(tipoProdutoEncontrado.getIdTipoProduto(), tipoProdutoAntigo.getIdTipoProduto());
            Assertions.assertEquals(tipoProdutoEncontrado.getTipo(), tipoProdutoAntigo.getTipo());
            Mockito.when(repository.save(tipoProdutoAtualizado)).thenReturn(tipoProdutoAtualizado);
            TipoProduto tipoProdutoRetornado = service.atualizar(tipoProdutoAtualizado);

            Assertions.assertEquals(tipoProdutoRetornado.getIdTipoProduto(), tipoProdutoAtualizado.getIdTipoProduto());
            Assertions.assertEquals(tipoProdutoRetornado.getTipo(), tipoProdutoAtualizado.getTipo());

            Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            Mockito.verify(repository, Mockito.times(1)).save(tipoProdutoAtualizado);
        }

        @Test
        @DisplayName("3.2 - Deve throwlar BadRequestException, quando passado o id de atualização incorreto")
        void throwlaBadRequest(){
            TipoProduto tipoProdutoAntigo = new TipoProduto(1, "Salgado", Collections.emptyList());
            Integer idBusca = 1;

            Mockito.when(repository.findById(idBusca)).thenReturn(Optional.empty());

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> service.buscarPorId(idBusca));

            Assertions.assertEquals("Id Tipo Produto enviado de forma incorreta", exception.getLocalizedMessage());

            Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
        }
    }


    @Nested
    @DisplayName("4 - Cenários de Deletar")
    public class CenarioDelete{
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso quando id do tipoProduto passado corretamente")
        void retornaStringSucesso(){
            Integer idBusca = 1;

            Mockito.when(repository.existsById(idBusca)).thenReturn(Boolean.TRUE);

            String retorno = service.deletar(idBusca);

            Assertions.assertEquals(retorno, "Tipo Produto deletado com sucesso!");
            Mockito.verify(repository, Mockito.times(1)).existsById(idBusca);
            Mockito.verify(repository, Mockito.times(1)).deleteById(idBusca);
        }
        @Test
        @DisplayName("4.2 - Deve retornar BadRequestException quando id do TipoProduto passado incorretamente")
        void throwlaBadRequest(){
            Integer idBusca = -1;

            Mockito.when(repository.existsById(idBusca)).thenReturn(Boolean.FALSE);

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> repository.deleteById(idBusca));

            Assertions.assertEquals(exception.getLocalizedMessage(), "Id Tipo Produto envido de forma incorreta");
            Mockito.verify(repository, Mockito.times(1)).existsById(idBusca);
        }
    }







}