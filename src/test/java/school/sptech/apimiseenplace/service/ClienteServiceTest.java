package school.sptech.apimiseenplace.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.apimiseenplace.entity.Cliente;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.ClienteRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Nested
    @DisplayName("1 - Cenários de Criação")
    public class cenarioCriacao {

        @Test
        @DisplayName("1.1 - Deve retornar Cliente cadastrado, quando parâmetros passados corretamente")
        void deveRetornarCliente() {

            Cliente clienteInsertion = new Cliente(null, "Leandro", "11996570337", LocalDate.parse("2002-04-28"));
            Cliente clienteEsperado = new Cliente(1, "Leandro", "11996570337", LocalDate.parse("2002-04-28"));

            Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(clienteEsperado);

            Cliente clienteSalvo = clienteService.cadastrarCliente(clienteInsertion);

            Assertions.assertEquals(clienteEsperado.getIdCliente(), clienteSalvo.getIdCliente());
            Assertions.assertEquals(clienteEsperado.getNome(), clienteSalvo.getNome());
            Assertions.assertEquals(clienteEsperado.getNumero(), clienteSalvo.getNumero());
            Assertions.assertEquals(clienteEsperado.getDtAniversario(), clienteSalvo.getDtAniversario());
            Mockito.verify(clienteRepository, Mockito.times(1)).save(clienteInsertion);

        }

        @Test
        @DisplayName("1.2 - Deve throwlar BadRequestException, quando Cliente passado como null")
        void deveThrowlarException() {

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> clienteService.cadastrarCliente(null));

            Assertions.assertEquals("Cliente enviado de forma incorreta!", exception.getLocalizedMessage());

            Mockito.verify(clienteRepository, Mockito.times(0)).save(Mockito.any(Cliente.class));

        }

    }

    @Nested
    @DisplayName("2 - Cenários de Listagem")
    public class CenarioListagem{

        @Nested
        @DisplayName("2.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll{
            @Test
            @DisplayName("2.1.1 - Deve retornar lista de clientes")
            void deveRetornarListaCliente(){
                List<Cliente> clientes = List.of(
                        new Cliente(1, "João Silva", "123456789", LocalDate.of(1990, 1, 15)),
                        new Cliente(2, "Maria Oliveira", "987654321", LocalDate.of(1985, 5, 20)),
                        new Cliente(3, "Carlos Souza", "456789123", LocalDate.of(1978, 8, 30))
                );

                Mockito.when(clienteRepository.findAll()).thenReturn(clientes);

                List<Cliente> clientesRetornados = clienteService.listarClientes();

                Assertions.assertEquals(clientes.size(), clientesRetornados.size());
                Assertions.assertEquals(3, clientesRetornados.size());
                Assertions.assertFalse(clientesRetornados.isEmpty());
                Assertions.assertEquals(clientes.get(0).getIdCliente(), clientesRetornados.get(0).getIdCliente());
                Assertions.assertEquals(clientes.get(0).getNome(), clientesRetornados.get(0).getNome());
                Assertions.assertEquals(clientes.get(0).getNumero(), clientesRetornados.get(0).getNumero());
                Assertions.assertEquals(clientes.get(0).getDtAniversario(), clientesRetornados.get(0).getDtAniversario());
                Mockito.verify(clienteRepository, Mockito.times(1)).findAll();

            }

            @Test@DisplayName("2.1.2 - Deve retornara lista vazia de clientes")
            void retornaListaVazia(){
                List<Cliente> clientes = new ArrayList<>();

                Mockito.when(clienteRepository.findAll()).thenReturn(clientes);

                List<Cliente> clientesRetornados = clienteService.listarClientes();

                Assertions.assertTrue(clientesRetornados.isEmpty());
                Mockito.verify(clienteRepository, Mockito.times(1)).findAll();
            }
        }

        @Nested
        @DisplayName("2.2 - Cenários de Listagem por id")
        public class CenarioFindById{

            @Test
            @DisplayName("2.2.1 - Deve retornar o cliente por id")
            void retornaClientePorId(){

                Cliente cliente = new Cliente(1, "Leandro", "11996570337", LocalDate.parse("2002-04-28"));
                Integer idBusca = 1;

                Mockito.when(clienteRepository.findById(idBusca)).thenReturn(Optional.of(cliente));

                Cliente clienteEncontrado = clienteService.encontrarPorId(idBusca);

                Assertions.assertEquals(idBusca, clienteEncontrado.getIdCliente());
                Mockito.verify(clienteRepository, Mockito.times(1)).findById(idBusca);

            }

            @Test
            @DisplayName("2.2.2 - Deve retornar NaoEncontradoException caso não exista o id")
            void retornaExceptionCasoNaoExite(){

                Integer idBusca = 100;

                Mockito.when(clienteRepository.findById(idBusca)).thenReturn(Optional.empty());
                NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                        () -> clienteService.encontrarPorId(idBusca));

                Assertions.assertEquals("Cliente não encontrado", exception.getLocalizedMessage());

                Mockito.verify(clienteRepository, Mockito.times(1)).findById(idBusca);

            }
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Atualizar")
    public class CenarioPut{

        @Test
        @DisplayName("3.1 - Deve retornar o Objeto Atualizado, quando passado o cliente de atualização correto")
        void retornaObjetoAtualizado(){

            Cliente clienteAntigo = new Cliente(null, "Leandro", "11996570337", LocalDate.parse("2002-04-28"));
            Cliente clienteAtualizado = new Cliente(1, "João Silva", "123456789", LocalDate.of(1990, 1, 15));

            Mockito.when(clienteRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(clienteAntigo));

            Cliente clienteEncontrado = clienteService.encontrarPorId(Mockito.any(Integer.class));

            Assertions.assertEquals(clienteEncontrado.getIdCliente(), clienteAntigo.getIdCliente());
            Assertions.assertEquals(clienteEncontrado.getNome(), clienteAntigo.getNome());
            Assertions.assertEquals(clienteEncontrado.getNumero(), clienteAntigo.getNumero());
            Assertions.assertEquals(clienteEncontrado.getDtAniversario(), clienteAntigo.getDtAniversario());
            Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(clienteAntigo);

            Cliente clienteRetornado = clienteService.atualizarCliente(Mockito.any(Integer.class),clienteAntigo);

            Assertions.assertEquals(clienteRetornado.getIdCliente(), clienteAntigo.getIdCliente());
            Assertions.assertEquals(clienteRetornado.getNome(), clienteAntigo.getNome());
            Assertions.assertEquals(clienteRetornado.getNumero(), clienteAntigo.getNumero());
            Assertions.assertEquals(clienteRetornado.getDtAniversario(), clienteAntigo.getDtAniversario());

            Mockito.verify(clienteRepository, Mockito.times(2)).findById(Mockito.any(Integer.class));
            Mockito.verify(clienteRepository, Mockito.times(1)).save(Mockito.any(Cliente.class));

        }

        @Test
        @DisplayName("3.2 - Deve throwlar BadRequestException, quando passado o id de atualização incorreto")
        void throwlaBadRequest(){

            Integer idBusca = 1;

            Mockito.when(clienteRepository.findById(idBusca)).thenReturn(Optional.empty());

            NaoEncontradoException exception = Assertions.assertThrows(NaoEncontradoException.class,
                    () -> clienteService.encontrarPorId(idBusca));

            Assertions.assertEquals("Cliente não encontrado", exception.getLocalizedMessage());

            Mockito.verify(clienteRepository, Mockito.times(1)).findById(idBusca);

        }
    }


    @Nested
    @DisplayName("4 - Cenários de Deletar")
    public class CenarioDelete{
        @Test
        @DisplayName("4.1 - Deve retornar String de sucesso quando id do cliente passado corretamente")
        void retornaStringSucesso(){

            Mockito.when(clienteRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);

            String retorno = clienteService.deletarCliente(Mockito.any(Integer.class));

            Assertions.assertEquals(retorno, "Cliente deletado com sucesso!");
            Mockito.verify(clienteRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(clienteRepository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));

        }
        @Test
        @DisplayName("4.2 - Deve retornar BadRequestException quando id do cliente passado incorretamente")
        void throwlaBadRequest(){

            Mockito.when(clienteRepository.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.FALSE);

            BadRequestException exception = Assertions.assertThrows(BadRequestException.class,
                    () -> clienteService.deletarCliente(Mockito.any(Integer.class)));

            Assertions.assertEquals(exception.getLocalizedMessage(), "Cliente enviado de forma incorreta!");
            Mockito.verify(clienteRepository, Mockito.times(1)).existsById(Mockito.any(Integer.class));

        }
    }

}