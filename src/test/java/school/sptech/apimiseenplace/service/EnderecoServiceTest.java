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
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.EnderecoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class CenarioCriacao{

        @Test
        @DisplayName("1.1 - Deve Retornar endereço cadastrado, quando nome passado corretamento")
        void deveRetornarEndereco(){

            Endereco enderecoInsertion = new Endereco(null, "Rua das Flores", "Apto 101", "12345-678", 100);
            Endereco enderecoEsperado = new Endereco(null, "Rua das Flores", "Apto 101", "12345-678", 100);

            Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(enderecoEsperado);

            Endereco enderecoSalvo = enderecoService.cadastrar(enderecoInsertion);

            Assertions.assertEquals(enderecoEsperado.getIdEndereco(), enderecoSalvo.getIdEndereco());
            Assertions.assertEquals(enderecoEsperado.getLogradouro(), enderecoSalvo.getLogradouro());
            Assertions.assertEquals(enderecoEsperado.getComplemento(), enderecoSalvo.getComplemento());
            Assertions.assertEquals(enderecoEsperado.getCep(), enderecoSalvo.getCep());
            Assertions.assertEquals(enderecoEsperado.getNumero(), enderecoSalvo.getNumero());
            Mockito.verify(enderecoRepository, Mockito.times(1)).save(enderecoInsertion);

        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando endereço passado como null")
        void deveThrowlarException(){

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> enderecoService.cadastrar(null));

            Assertions.assertEquals("Endereço enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(enderecoRepository, Mockito.times(0)).save(Mockito.any(Endereco.class));

        }
    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{

        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll{
            @Test
            @DisplayName("2.1.1 - Deve retornar lista de endereços")
            void deveRetornarListaEndereco(){

                List<Endereco> enderecos = List.of(
                        new Endereco(1, "Avenida Brasil", "Casa", "23456-789", 200),
                        new Endereco(2, "Praça da Liberdade", "Bloco B", "34567-890", 300),
                        new Endereco(3, "Rua do Sol", "Sala 202", "45678-901", 400)
                );

                Mockito.when(enderecoRepository.findAll()).thenReturn(enderecos);

                List<Endereco> enderecosRetornados = enderecoService.listar();

                Assertions.assertEquals(enderecos.size(), enderecosRetornados.size());
                Assertions.assertEquals(3, enderecosRetornados.size());
                Assertions.assertFalse(enderecosRetornados.isEmpty());
                Assertions.assertEquals(enderecos.get(0).getIdEndereco(), enderecosRetornados.get(0).getIdEndereco());
                Assertions.assertEquals(enderecos.get(0).getLogradouro(), enderecosRetornados.get(0).getLogradouro());
                Assertions.assertEquals(enderecos.get(0).getComplemento(), enderecosRetornados.get(0).getComplemento());
                Assertions.assertEquals(enderecos.get(0).getCep(), enderecosRetornados.get(0).getCep());
                Assertions.assertEquals(enderecos.get(0).getNumero(), enderecosRetornados.get(0).getNumero());
                Mockito.verify(enderecoRepository, Mockito.times(1)).findAll();

            }

            @Test@DisplayName("2.1.2 - Deve retornara lista vazia de endereços")
            void retornaListaVazia(){

                List<Endereco> enderecos = new ArrayList<>();

                Mockito.when(enderecoRepository.findAll()).thenReturn(enderecos);

                List<Endereco> enderecosRetornados = enderecoService.listar();

                Assertions.assertTrue(enderecosRetornados.isEmpty());
                Mockito.verify(enderecoRepository, Mockito.times(1)).findAll();

            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem por id")
        public class CenarioFindById{

            @Test
            @DisplayName("2.2.1 - Deve retornar o endereço por id")
            void retornaEndereçoPorId(){

                Endereco endereco = new Endereco(1, "Rua das Flores", "Apto 101", "12345-678", 100);
                Integer idBusca = 1;

                Mockito.when(enderecoRepository.findById(idBusca)).thenReturn(Optional.of(endereco));

                Endereco enderecoEncontrado = enderecoService.encontrarPorId(idBusca);

                Assertions.assertEquals(idBusca, enderecoEncontrado.getIdEndereco());
                Mockito.verify(enderecoRepository, Mockito.times(1)).findById(idBusca);

            }

            @Test
            @DisplayName("2.2.2 - Deve retornar NaoEncontradoException caso não exista o id")
            void retornaExceptionCasoNaoExite(){

                Integer idBusca = 100;

                Mockito.when(enderecoRepository.findById(idBusca)).thenReturn(Optional.empty());
                NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                        () -> enderecoService.encontrarPorId(idBusca));

                Assertions.assertEquals("Endereço não encontrado", exception.getLocalizedMessage());

                Mockito.verify(enderecoRepository, Mockito.times(1)).findById(idBusca);

            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualizar")
    public class CenarioPut{

        @Test
        @DisplayName("3.1 - Deve retornar o Objeto Atualizado, quando passado o endereço de atualização correto")
        void retornaObjetoAtualizado(){

            Endereco enderecoAntigo = new Endereco(null, "Rua das Flores", "Apto 101", "12345-678", 100);
            Endereco enderecoAtualizado = new Endereco(2, "Praça da Liberdade", "Bloco B", "34567-890", 300);

            Mockito.when(enderecoRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(enderecoAntigo));

            Endereco enderecoEncontrado = enderecoService.encontrarPorId(Mockito.any(Integer.class));

            Assertions.assertEquals(enderecoEncontrado.getIdEndereco(), enderecoAntigo.getIdEndereco());
            Assertions.assertEquals(enderecoEncontrado.getLogradouro(), enderecoAntigo.getLogradouro());
            Assertions.assertEquals(enderecoEncontrado.getComplemento(), enderecoAntigo.getComplemento());
            Assertions.assertEquals(enderecoEncontrado.getCep(), enderecoAntigo.getCep());
            Assertions.assertEquals(enderecoEncontrado.getNumero(), enderecoAntigo.getNumero());
            Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(enderecoAtualizado);

            Endereco enderecoRetornado = enderecoService.atualizar(Mockito.any(Integer.class),enderecoAtualizado);

            Assertions.assertEquals(enderecoRetornado.getIdEndereco(), enderecoAtualizado.getIdEndereco());
            Assertions.assertEquals(enderecoRetornado.getLogradouro(), enderecoAtualizado.getLogradouro());
            Assertions.assertEquals(enderecoRetornado.getComplemento(), enderecoAtualizado.getComplemento());
            Assertions.assertEquals(enderecoRetornado.getCep(), enderecoAtualizado.getCep());
            Assertions.assertEquals(enderecoRetornado.getNumero(), enderecoAtualizado.getNumero());

            Mockito.verify(enderecoRepository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(enderecoRepository, Mockito.times(1)).save(Mockito.any(Endereco.class));

        }

        @Test
        @DisplayName("3.2 - Deve throwlar BadRequestException, quando passado o id de atualização incorreto")
        void throwlaBadRequest(){

            Integer idBusca = 1;

            Mockito.when(enderecoRepository.findById(idBusca)).thenReturn(Optional.empty());

            NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                    () -> enderecoService.encontrarPorId(idBusca));

            Assertions.assertEquals("Endereço não encontrado", exception.getLocalizedMessage());

            Mockito.verify(enderecoRepository, Mockito.times(1)).findById(idBusca);

        }
    }


    @Nested
    @DisplayName("4 - Cenários de Deletar")
    public class CenarioDelete{
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso quando id do endereço passado corretamente")
        void retornaStringSucesso(){

            Mockito.when(enderecoRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = enderecoService.deletar(Mockito.any(Integer.class));

            Assertions.assertEquals(retorno, "Endereço deletado com sucesso!");
            Mockito.verify(enderecoRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(enderecoRepository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));

        }
        @Test
        @DisplayName("4.2 - Deve retornar BadRequestException quando id do endereço passado incorretamente")
        void throwlaBadRequest(){

            Mockito.when(enderecoRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> enderecoService.deletar(Mockito.any(Integer.class)));

            Assertions.assertEquals(exception.getLocalizedMessage(), "Endereço enviado de forma incorreta!");
            Mockito.verify(enderecoRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));

        }
    }


}