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
import school.sptech.apimiseenplace.entity.Cobertura;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.CoberturaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CoberturaServiceTest {

    @InjectMocks
    private CoberturaService coberturaService;

    @Mock
    private CoberturaRepository coberturaRepository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{

        @Test
        @DisplayName("1.1 - Deve Retornar cobertura cadastrada, quando nome passado corretamento")
        void deveRetornarCobertura(){

            Cobertura coberturaInsertion = new Cobertura(null, "Cobertura de Chocolate");
            Cobertura coberturaEsperada = new Cobertura(1, "Cobertura de Chocolate");

            Mockito.when(coberturaRepository.save(Mockito.any(Cobertura.class))).thenReturn(coberturaEsperada);

            Cobertura coberturaSalva = coberturaService.criar(coberturaInsertion);

            Assertions.assertEquals(coberturaEsperada.getIdCobertura(), coberturaSalva.getIdCobertura());
            Assertions.assertEquals(coberturaEsperada.getNome(), coberturaSalva.getNome());
            Mockito.verify(coberturaRepository, Mockito.times(1)).save(coberturaInsertion);

        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando cobertura passado como null")
        void deveThrowlarException(){

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> coberturaService.criar(null));

            Assertions.assertEquals("Cobertura enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(coberturaRepository, Mockito.times(0)).save(Mockito.any(Cobertura.class));

        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{

        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll{
            @Test
            @DisplayName("2.1.1 - Deve retornar lista de coberturas")
            void deveRetornarListaCobertura(){

                List<Cobertura> coberturas = List.of(
                        new Cobertura(1, "Cobertura de Morango"),
                        new Cobertura(2, "Cobertura de Caramelo"),
                        new Cobertura(3, "Cobertura de Baunilha")
                );

                Mockito.when(coberturaRepository.findAll()).thenReturn(coberturas);

                List<Cobertura> coberturasRetornadas = coberturaService.listar();

                Assertions.assertEquals(coberturas.size(), coberturasRetornadas.size());
                Assertions.assertEquals(3, coberturasRetornadas.size());
                Assertions.assertFalse(coberturasRetornadas.isEmpty());
                Assertions.assertEquals(coberturas.get(0).getIdCobertura(), coberturasRetornadas.get(0).getIdCobertura());
                Assertions.assertEquals(coberturas.get(0).getNome(), coberturasRetornadas.get(0).getNome());
                Mockito.verify(coberturaRepository, Mockito.times(1)).findAll();

            }

            @Test@DisplayName("2.1.2 - Deve retornara lista vazia de coberturas")
            void retornaListaVazia(){

                List<Cobertura> coberturas = new ArrayList<>();

                Mockito.when(coberturaRepository.findAll()).thenReturn(coberturas);

                List<Cobertura> coberturasRetornadas = coberturaService.listar();

                Assertions.assertTrue(coberturasRetornadas.isEmpty());
                Mockito.verify(coberturaRepository, Mockito.times(1)).findAll();

            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem por id")
        public class CenarioFindById{

            @Test
            @DisplayName("2.2.1 - Deve retornar a cobertura por id")
            void retornaCoberturaPorId(){

                Cobertura cobertura = new Cobertura(1, "Cobertura de Chocolate");
                Integer idBusca = 1;

                Mockito.when(coberturaRepository.findById(idBusca)).thenReturn(Optional.of(cobertura));

                Cobertura coberturaEncontrada = coberturaService.encontrarPorId(idBusca);

                Assertions.assertEquals(idBusca, coberturaEncontrada.getIdCobertura());
                Mockito.verify(coberturaRepository, Mockito.times(1)).findById(idBusca);

            }

            @Test
            @DisplayName("2.2.2 - Deve retornar NaoEncontradoException caso não exista o id")
            void retornaExceptionCasoNaoExite(){

                Integer idBusca = 100;

                Mockito.when(coberturaRepository.findById(idBusca)).thenReturn(Optional.empty());
                NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                        () -> coberturaService.encontrarPorId(idBusca));

                Assertions.assertEquals("Cobertura não encontrado", exception.getLocalizedMessage());

                Mockito.verify(coberturaRepository, Mockito.times(1)).findById(idBusca);

            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualizar")
    public class CenarioPut{

        @Test
        @DisplayName("3.1 - Deve retornar o Objeto Atualizado, quando passado a cobertura de atualização correto")
        void retornaObjetoAtualizado(){

            Cobertura coberturaAntiga = new Cobertura(1, "Cobertura de Chocolate");
            Cobertura coberturaAtualizada = new Cobertura(2, "Cobertura de Caramelo");

            Mockito.when(coberturaRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(coberturaAntiga));

            Cobertura coberturaEncontrada = coberturaService.encontrarPorId(Mockito.any(Integer.class));

            Assertions.assertEquals(coberturaEncontrada.getIdCobertura(), coberturaAntiga.getIdCobertura());
            Assertions.assertEquals(coberturaEncontrada.getNome(), coberturaAntiga.getNome());
            Mockito.when(coberturaRepository.save(Mockito.any(Cobertura.class))).thenReturn(coberturaAtualizada);

            Cobertura coberturaRetornada = coberturaService.atualizar(Mockito.any(Integer.class), coberturaAtualizada);

            Assertions.assertEquals(coberturaRetornada.getIdCobertura(), coberturaAtualizada.getIdCobertura());
            Assertions.assertEquals(coberturaRetornada.getNome(), coberturaAtualizada.getNome());

            Mockito.verify(coberturaRepository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(coberturaRepository, Mockito.times(1)).save(Mockito.any(Cobertura.class));

        }

        @Test
        @DisplayName("3.2 - Deve throwlar BadRequestException, quando passado o id de atualização incorreto")
        void throwlaBadRequest(){

            Integer idBusca = 1;

            Mockito.when(coberturaRepository.findById(idBusca)).thenReturn(Optional.empty());

            NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                    () -> coberturaService.encontrarPorId(idBusca));

            Assertions.assertEquals("Cobertura não encontrado", exception.getLocalizedMessage());

            Mockito.verify(coberturaRepository, Mockito.times(1)).findById(idBusca);

        }
    }


    @Nested
    @DisplayName("4 - Cenários de Deletar")
    public class CenarioDelete{
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso quando id da cobertura passado corretamente")
        void retornaStringSucesso(){

            Mockito.when(coberturaRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = coberturaService.deletar(Mockito.any(Integer.class));

            Assertions.assertEquals(retorno, "Cobertura deletado com sucesso!");
            Mockito.verify(coberturaRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(coberturaRepository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));

        }
        @Test
        @DisplayName("4.2 - Deve retornar BadRequestException quando id da cobertura passado incorretamente")
        void throwlaBadRequest(){

            Mockito.when(coberturaRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> coberturaService.deletar(Mockito.any(Integer.class)));

            Assertions.assertEquals(exception.getLocalizedMessage(), "Cobertura enviado de forma incorreta!");
            Mockito.verify(coberturaRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));

        }
    }


}