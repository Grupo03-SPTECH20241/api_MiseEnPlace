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
import school.sptech.apimiseenplace.entity.Metas;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.MetaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MetaServiceTest {

    @InjectMocks
    private MetaService metaService;

    @Mock
    private MetaRepository metaRepository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{

        @Test
        @DisplayName("1.1 - Deve Retornar meta cadastrada, quando nome passado corretamento")
        void deveRetornarMeta(){

            Metas metaInsertion = new Metas(null, 1000.00, LocalDate.of(2024, 12, 31), LocalDate.of(2024, 1, 1));
            Metas metaEsperada = new Metas(1, 1000.00, LocalDate.of(2024, 12, 31), LocalDate.of(2024, 1, 1));

            Mockito.when(metaRepository.save(Mockito.any(Metas.class))).thenReturn(metaEsperada);

            Metas metaSalva = metaService.criarMeta(metaInsertion);

            Assertions.assertEquals(metaEsperada.getIdMeta(), metaSalva.getIdMeta());
            Assertions.assertEquals(metaEsperada.getValor(), metaSalva.getValor());
            Assertions.assertEquals(metaEsperada.getDtTermino(), metaSalva.getDtTermino());
            Assertions.assertEquals(metaEsperada.getDtInicio(), metaSalva.getDtInicio());
            Mockito.verify(metaRepository, Mockito.times(1)).save(metaInsertion);

        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando meta passada como null")
        void deveThrowlarException(){

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> metaService.criarMeta(null));

            Assertions.assertEquals("Meta enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(metaRepository, Mockito.times(0)).save(Mockito.any(Metas.class));

        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{

        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll{
            @Test
            @DisplayName("2.1.1 - Deve retornar lista de metas")
            void deveRetornarListaMetas(){

                List<Metas> metas = List.of(
                    new Metas(1, 2000.00, LocalDate.of(2025, 12, 31), LocalDate.of(2025, 1, 1)),
                    new Metas(2, 1500.00, LocalDate.of(2024, 6, 30), LocalDate.of(2024, 1, 1)),
                    new Metas(3, 3000.00, LocalDate.of(2026, 12, 31), LocalDate.of(2026, 1, 1))
                );

                Mockito.when(metaRepository.findAll()).thenReturn(metas);

                List<Metas> metasRetornadas = metaService.listarMetas();

                Assertions.assertEquals(metas.size(), metasRetornadas.size());
                Assertions.assertEquals(3, metasRetornadas.size());
                Assertions.assertFalse(metasRetornadas.isEmpty());
                Assertions.assertEquals(metas.get(0).getIdMeta(), metasRetornadas.get(0).getIdMeta());
                Assertions.assertEquals(metas.get(0).getValor(), metasRetornadas.get(0).getValor());
                Assertions.assertEquals(metas.get(0).getDtTermino(), metasRetornadas.get(0).getDtTermino());
                Assertions.assertEquals(metas.get(0).getDtInicio(), metasRetornadas.get(0).getDtInicio());
                Mockito.verify(metaRepository, Mockito.times(1)).findAll();

            }

            @Test@DisplayName("2.1.2 - Deve retornara lista vazia de metas")
            void retornaListaVazia(){

                List<Metas> metas = new ArrayList<>();

                Mockito.when(metaRepository.findAll()).thenReturn(metas);

                List<Metas> metasRetornadas = metaService.listarMetas();

                Assertions.assertTrue(metasRetornadas.isEmpty());
                Mockito.verify(metaRepository, Mockito.times(1)).findAll();

            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem por id")
        public class CenarioFindById{

            @Test
            @DisplayName("2.2.1 - Deve retornar o meta por id")
            void retornaMetaPorId(){

                Metas meta = new Metas(1, 1000.00, LocalDate.of(2024, 12, 31), LocalDate.of(2024, 1, 1));
                Integer idBusca = 1;

                Mockito.when(metaRepository.findById(idBusca)).thenReturn(Optional.of(meta));

                Metas metaEncontrada = metaService.encontrarPorId(idBusca);

                Assertions.assertEquals(idBusca, metaEncontrada.getIdMeta());
                Mockito.verify(metaRepository, Mockito.times(1)).findById(idBusca);

            }

            @Test
            @DisplayName("2.2.2 - Deve retornar NaoEncontradoException caso não exista o id")
            void retornaExceptionCasoNaoExite(){

                Integer idBusca = 100;

                Mockito.when(metaRepository.findById(idBusca)).thenReturn(Optional.empty());
                NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                        () -> metaService.encontrarPorId(idBusca));

                Assertions.assertEquals("Meta não encontrado", exception.getLocalizedMessage());

                Mockito.verify(metaRepository, Mockito.times(1)).findById(idBusca);

            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualizar")
    public class CenarioPut{

        @Test
        @DisplayName("3.1 - Deve retornar o Objeto Atualizado, quando passado a meta de atualização correto")
        void retornaObjetoAtualizado(){

            Metas metaAntiga = new Metas(null, 1000.00, LocalDate.of(2024, 12, 31), LocalDate.of(2024, 1, 1));
            Metas metaAtualizada = new Metas(1, 2000.00, LocalDate.of(2025, 12, 31), LocalDate.of(2025, 1, 1));

            Mockito.when(metaRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(metaAntiga));

            Metas metaEncontrada = metaService.encontrarPorId(Mockito.any(Integer.class));

            Assertions.assertEquals(metaEncontrada.getIdMeta(), metaAntiga.getIdMeta());
            Assertions.assertEquals(metaEncontrada.getValor(), metaAntiga.getValor());
            Assertions.assertEquals(metaEncontrada.getDtTermino(), metaAntiga.getDtTermino());
            Assertions.assertEquals(metaEncontrada.getDtInicio(), metaAntiga.getDtInicio());
            Mockito.when(metaRepository.save(Mockito.any(Metas.class))).thenReturn(metaAtualizada);

            Metas metaRetornada = metaService.atualizarMeta(Mockito.any(Integer.class),metaAtualizada);

            Assertions.assertEquals(metaRetornada.getIdMeta(), metaAtualizada.getIdMeta());
            Assertions.assertEquals(metaRetornada.getValor(), metaAtualizada.getValor());
            Assertions.assertEquals(metaRetornada.getDtTermino(), metaAtualizada.getDtTermino());
            Assertions.assertEquals(metaRetornada.getDtInicio(), metaAtualizada.getDtInicio());

            Mockito.verify(metaRepository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(metaRepository, Mockito.times(1)).save(Mockito.any(Metas.class));

        }

        @Test
        @DisplayName("3.2 - Deve throwlar BadRequestException, quando passado o id de atualização incorreto")
        void throwlaBadRequest(){

            Integer idBusca = 1;

            Mockito.when(metaRepository.findById(idBusca)).thenReturn(Optional.empty());

            NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                    () -> metaService.encontrarPorId(idBusca));

            Assertions.assertEquals("Meta não encontrado", exception.getLocalizedMessage());

            Mockito.verify(metaRepository, Mockito.times(1)).findById(idBusca);

        }
    }


    @Nested
    @DisplayName("4 - Cenários de Deletar")
    public class CenarioDelete{
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso quando id da meta passado corretamente")
        void retornaStringSucesso(){

            Mockito.when(metaRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = metaService.deletarMetaPorId(Mockito.any(Integer.class));

            Assertions.assertEquals("Meta deletado com sucesso!", retorno);
            Mockito.verify(metaRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(metaRepository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));

        }
        @Test
        @DisplayName("4.2 - Deve retornar BadRequestException quando id da meta passado incorretamente")
        void throwlaBadRequest(){

            Mockito.when(metaRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> metaService.deletarMetaPorId(Mockito.any(Integer.class)));

            Assertions.assertEquals(exception.getLocalizedMessage(), "Meta enviado de forma incorreta!");
            Mockito.verify(metaRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));

        }
    }


}