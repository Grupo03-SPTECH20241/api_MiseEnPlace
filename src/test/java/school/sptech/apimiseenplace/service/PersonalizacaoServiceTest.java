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
import school.sptech.apimiseenplace.repository.PersonalizacaoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PersonalizacaoServiceTest {

    @InjectMocks
    private PersonalizacaoService personalizacaoService;

    @Mock
    private PersonalizacaoRepository personalizacaoRepository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{

        @Test
        @DisplayName("1.1 - Deve Retornar personalização cadastrada, quando nome passado corretamento")
        void deveRetornarPersonalizacao(){

            Recheio recheioInsertion = new Recheio(1, "Chocolate", 3.50);
            Massa massaInsertion = new Massa(1, "Massa Chocolate");
            Cobertura coberturaInsertion = new Cobertura(1, "Calda de Chocolate");
            Personalizacao personalizacaoInsertion = new Personalizacao(null, "Os Incríveis",recheioInsertion, massaInsertion, coberturaInsertion);

            Personalizacao personalizacaoEsperada = new Personalizacao(1, "Os Incríveis", recheioInsertion, massaInsertion, coberturaInsertion);

            Mockito.when(personalizacaoRepository.save(Mockito.any(Personalizacao.class))).thenReturn(personalizacaoEsperada);

            Personalizacao personalizacaoSalva = personalizacaoService.criar(personalizacaoInsertion);

            Assertions.assertEquals(personalizacaoEsperada.getIdPersonalizacao(), personalizacaoSalva.getIdPersonalizacao());
            Assertions.assertEquals(personalizacaoEsperada.getRecheio(), personalizacaoSalva.getRecheio());
            Assertions.assertEquals(personalizacaoEsperada.getMassa(), personalizacaoSalva.getMassa());
            Assertions.assertEquals(personalizacaoEsperada.getCobertura(), personalizacaoSalva.getCobertura());
            Mockito.verify(personalizacaoRepository, Mockito.times(1)).save(personalizacaoInsertion);

        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando personalização passada como null")
        void deveThrowlarException(){

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> personalizacaoService.criar(null));

            Assertions.assertEquals("Personalização enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(personalizacaoRepository, Mockito.times(0)).save(Mockito.any(Personalizacao.class));

        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{

        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll{
            @Test
            @DisplayName("2.1.1 - Deve retornar lista de personalizações")
            void deveRetornarListaPersonalizacao(){

                Recheio recheioInsertion = new Recheio(1, "Chocolate", 3.50);
                Massa massaInsertion = new Massa(1, "Massa Chocolate");
                Cobertura coberturaInsertion = new Cobertura(1, "Calda de Chocolate");

                List<Personalizacao> personalizacaos = List.of(
                        new Personalizacao(1, "Os Incríveis", recheioInsertion, massaInsertion, coberturaInsertion),
                        new Personalizacao(2, "Carros", recheioInsertion, massaInsertion, coberturaInsertion),
                        new Personalizacao(3, "Barbie", recheioInsertion, massaInsertion, coberturaInsertion)
                );

                Mockito.when(personalizacaoRepository.findAll()).thenReturn(personalizacaos);

                List<Personalizacao> personalizacoesRetornadas = personalizacaoService.listar();

                Assertions.assertEquals(personalizacaos.size(), personalizacoesRetornadas.size());
                Assertions.assertEquals(3, personalizacoesRetornadas.size());
                Assertions.assertFalse(personalizacoesRetornadas.isEmpty());
                Assertions.assertEquals(personalizacaos.get(0).getIdPersonalizacao(), personalizacoesRetornadas.get(0).getIdPersonalizacao());
                Assertions.assertEquals(personalizacaos.get(0).getRecheio(), personalizacoesRetornadas.get(0).getRecheio());
                Assertions.assertEquals(personalizacaos.get(0).getMassa(), personalizacoesRetornadas.get(0).getMassa());
                Assertions.assertEquals(personalizacaos.get(0).getCobertura(), personalizacoesRetornadas.get(0).getCobertura());
                Mockito.verify(personalizacaoRepository, Mockito.times(1)).findAll();

            }

            @Test@DisplayName("2.1.2 - Deve retornara lista vazia de personalizações")
            void retornaListaVazia(){

                List<Personalizacao> personalizacoes = new ArrayList<>();

                Mockito.when(personalizacaoRepository.findAll()).thenReturn(personalizacoes);

                List<Personalizacao> personalizacoesRetornadas = personalizacaoService.listar();

                Assertions.assertTrue(personalizacoesRetornadas.isEmpty());
                Mockito.verify(personalizacaoRepository, Mockito.times(1)).findAll();

            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem por id")
        public class CenarioFindById{

            @Test
            @DisplayName("2.2.1 - Deve retornar o personalização por id")
            void retornaPersonalizacaoPorId(){

                Recheio recheioInsertion = new Recheio(1, "Chocolate", 3.50);
                Massa massaInsertion = new Massa(1, "Massa Chocolate");
                Cobertura coberturaInsertion = new Cobertura(1, "Calda de Chocolate");
                Personalizacao personalizacao = new Personalizacao(1, "Os Incríveis",recheioInsertion, massaInsertion, coberturaInsertion);
                Integer idBusca = 1;

                Mockito.when(personalizacaoRepository.findById(idBusca)).thenReturn(Optional.of(personalizacao));

                Personalizacao personalizacaoEncontrada = personalizacaoService.encontrarPorId(idBusca);

                Assertions.assertEquals(idBusca, personalizacaoEncontrada.getIdPersonalizacao());
                Mockito.verify(personalizacaoRepository, Mockito.times(1)).findById(idBusca);

            }

            @Test
            @DisplayName("2.2.2 - Deve retornar NaoEncontradoException caso não exista o id")
            void retornaExceptionCasoNaoExite(){

                Integer idBusca = 100;

                Mockito.when(personalizacaoRepository.findById(idBusca)).thenReturn(Optional.empty());
                NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                        () -> personalizacaoService.encontrarPorId(idBusca));

                Assertions.assertEquals("Personalização não encontrado", exception.getLocalizedMessage());

                Mockito.verify(personalizacaoRepository, Mockito.times(1)).findById(idBusca);

            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualizar")
    public class CenarioPut{

        @Test
        @DisplayName("3.1 - Deve retornar o Objeto Atualizado, quando passado o personalização de atualização correto")
        void retornaObjetoAtualizado(){

            Recheio recheioAntigo = new Recheio(1, "Chocolate", 3.50);
            Massa massaAntigo = new Massa(1, "Massa Chocolate");
            Cobertura coberturaAntigo = new Cobertura(1, "Calda de Chocolate");
            Personalizacao personalizacaoAntiga = new Personalizacao(1, "Os Incríveis",recheioAntigo, massaAntigo, coberturaAntigo);

            Recheio recheioAtualizado = new Recheio(2, "Morango", 4.00);
            Massa massaAtualizado = new Massa(2, "Massa Ninho");
            Cobertura coberturaAtualizado = new Cobertura(2, "Granulado");
            Personalizacao personalizacaoAtualizada = new Personalizacao(1, "Carros",recheioAtualizado, massaAtualizado, coberturaAtualizado);

            Mockito.when(personalizacaoRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(personalizacaoAntiga));

            Personalizacao personalizacaoEncontrada = personalizacaoService.encontrarPorId(Mockito.any(Integer.class));

            Assertions.assertEquals(personalizacaoEncontrada.getIdPersonalizacao(), personalizacaoAntiga.getIdPersonalizacao());
            Assertions.assertEquals(personalizacaoEncontrada.getRecheio(), personalizacaoAntiga.getRecheio());
            Assertions.assertEquals(personalizacaoEncontrada.getMassa(), personalizacaoAntiga.getMassa());
            Assertions.assertEquals(personalizacaoEncontrada.getCobertura(), personalizacaoAntiga.getCobertura());
            Mockito.when(personalizacaoRepository.save(Mockito.any(Personalizacao.class))).thenReturn(personalizacaoAtualizada);
            Personalizacao personalizacaoRetornada = personalizacaoService.atualizar(personalizacaoAtualizada, Mockito.any(Integer.class));

            Assertions.assertEquals(personalizacaoRetornada.getIdPersonalizacao(), personalizacaoAtualizada.getIdPersonalizacao());
            Assertions.assertEquals(personalizacaoRetornada.getRecheio(), personalizacaoAtualizada.getRecheio());
            Assertions.assertEquals(personalizacaoRetornada.getMassa(), personalizacaoAtualizada.getMassa());
            Assertions.assertEquals(personalizacaoRetornada.getCobertura(), personalizacaoAtualizada.getCobertura());

            Mockito.verify(personalizacaoRepository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(personalizacaoRepository, Mockito.times(1)).save(Mockito.any(Personalizacao.class));

        }

        @Test
        @DisplayName("3.2 - Deve throwlar BadRequestException, quando passado o id de atualização incorreto")
        void throwlaBadRequest(){

            Integer idBusca = 1;

            Mockito.when(personalizacaoRepository.findById(idBusca)).thenReturn(Optional.empty());

            NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                    () -> personalizacaoService.encontrarPorId(idBusca));

            Assertions.assertEquals("Personalização não encontrado", exception.getLocalizedMessage());

            Mockito.verify(personalizacaoRepository, Mockito.times(1)).findById(idBusca);

        }
    }


    @Nested
    @DisplayName("4 - Cenários de Deletar")
    public class CenarioDelete{
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso quando id da personalização passado corretamente")
        void retornaStringSucesso(){

            Mockito.when(personalizacaoRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = personalizacaoService.deletar(Mockito.any(Integer.class));

            Assertions.assertEquals("Personalização deletado com sucesso!", retorno);
            Mockito.verify(personalizacaoRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(personalizacaoRepository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));

        }
        @Test
        @DisplayName("4.2 - Deve retornar BadRequestException quando id da personalização passado incorretamente")
        void throwlaBadRequest(){

            Mockito.when(personalizacaoRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> personalizacaoService.deletar(Mockito.any(Integer.class)));

            Assertions.assertEquals(exception.getLocalizedMessage(), "Personalizacao enviado de forma incorreta!");
            Mockito.verify(personalizacaoRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));

        }
    }


}