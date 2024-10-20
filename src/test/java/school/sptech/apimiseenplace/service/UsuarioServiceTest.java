package school.sptech.apimiseenplace.service;

import ch.qos.logback.core.testUtil.MockInitialContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.apimiseenplace.api.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.apimiseenplace.dto.usuario.*;
import school.sptech.apimiseenplace.entity.Usuario;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.ConflitoException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.UsuarioRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Nested
    @DisplayName("1 - Cenários de Autenticação")
    public class CenarioAutenticacao {
        @Test
        @DisplayName("1.1 - Deve Retornar UsuarioTokenDto, quando usuario passado corretamente")
        void deveRetornarUsuarioTokenDto() {

            UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto("test@test.com", "password");

            Usuario usuario = new Usuario();
            usuario.setEmail(usuarioLoginDto.getEmail());

            Mockito.when(repository.findByEmail(anyString())).thenReturn(Optional.of(usuario));
            Mockito.when(authenticationManager.authenticate(any())).thenReturn(Mockito.mock(Authentication.class));
            Mockito.when(gerenciadorTokenJwt.generateToken(any())).thenReturn("token");

            UsuarioTokenDto result = usuarioService.autenticar(usuarioLoginDto);

            assertEquals(usuarioLoginDto.getEmail(), result.getEmail());
            assertEquals("token", result.getToken());

            Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(any());
            Mockito.verify(repository, Mockito.times(1)).findByEmail(anyString());
            Mockito.verify(gerenciadorTokenJwt, Mockito.times(1)).generateToken(any());
        }

        @Test
        @DisplayName("1.2 - Deve throwlar ResponseStatusException, quando usuario passado como null")
        void autenticarThrowsResponseStatusException() {
            UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto("test@test.com", "password");

            Mockito.when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

            ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
                usuarioService.autenticar(usuarioLoginDto);
            });

            assertEquals(404, exception.getStatusCode().value());
            assertEquals("Email do usuário não cadastrado", exception.getReason());
            Mockito.verify(repository, Mockito.times(1)).findByEmail(anyString());
        }
    }

    @Nested
    @DisplayName("2 - Cenários de Criação")
    public class CenarioCriacao {
//        @Test
//        @DisplayName("2.1 - Deve Retornar Usuario, quando usuario passado corretamente")
//        void deveRetornarUsuario() {
//
//            UsuarioCriacaoDto usuarioCriacaoDto = new UsuarioCriacaoDto();
//            usuarioCriacaoDto.setEmail("test@test.com");
//            usuarioCriacaoDto.setSenha("password");
//
//            Usuario usuario = new Usuario();
//            usuario.setEmail(usuarioCriacaoDto.getEmail());
//
//            Mockito.when(repository.existsByEmail(anyString())).thenReturn(false);
//            Mockito.when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
//            Mockito.when(repository.save(any())).thenReturn(usuario);
//
//            Usuario result = usuarioService.criar(usuarioCriacaoDto);
//
//            assertEquals(usuarioCriacaoDto.getEmail(), result.getEmail());
//
//            Mockito.verify(repository, Mockito.times(1)).existsByEmail(anyString());
//            Mockito.verify(passwordEncoder, Mockito.times(1)).encode(anyString());
//            Mockito.verify(repository, Mockito.times(1)).save(any());
//        }

//        @Test
//        @DisplayName("2.2 - Deve throwlar ConflitoException, quando usuario passado com o email igual")
//        void deveThrowlarConflitoException() {
//            UsuarioCriacaoDto usuarioCriacaoDto = new UsuarioCriacaoDto();
//            usuarioCriacaoDto.setEmail("test@test.com");
//            usuarioCriacaoDto.setSenha("password");
//
//            Mockito.when(repository.existsByEmail(anyString())).thenReturn(true);
//
//            ConflitoException exception = assertThrows(ConflitoException.class, () -> {
//                usuarioService.criar(usuarioCriacaoDto);
//            });
//
//            assertEquals("Usuario já cadastrado!", exception.getMessage());
//        }

        @Test
        @DisplayName("2.3 - Deve throwlar BadRequestException, quando usuario passado como null")
        void deveThrowlarBadRequestException() {
            BadRequestException exception = assertThrows(BadRequestException.class,
                    () ->
                            usuarioService.criar(null)
            );

            assertEquals("Usuario enviado de forma incorreta!", exception.getLocalizedMessage());
            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Usuario.class));
        }
    }

    @Nested
    @DisplayName("3 - Cenários de Listagem")
    public class CenarioListagem {

        @Nested
        @DisplayName("3.1 - Cenários de Listagem FindAll")
        public class CenarioFindAll {
            @Test
            @DisplayName("3.1.1 - Deve Retornar Lista de UsuarioListagemDto, quando lista de usuarios não está vazia")
            void deveRetornarListaDeUsuarioListagemDto() {
                Usuario usuario = new Usuario(1, "a", "a", "a", "a", "a");
                Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(usuario));

                var listaUsuarios = usuarioService.listar();

                assertNotNull(listaUsuarios);
                assertEquals(1, listaUsuarios.size());
                assertEquals(usuario.getIdUsuario(), listaUsuarios.get(0).getIdUsuario());
                assertEquals(usuario.getNome(), listaUsuarios.get(0).getNome());
                assertEquals(usuario.getEmail(), listaUsuarios.get(0).getEmail());
                assertEquals(usuario.getCnpj(), listaUsuarios.get(0).getCnpj());
                assertEquals(usuario.getLogo(), listaUsuarios.get(0).getLogo());

                Mockito.verify(repository, Mockito.times(1)).findAll();
            }

            @Test
            @DisplayName("3.1.2 - Deve Retornar Lista Vazia, quando lista de usuarios está vazia")
            void deveRetornarListaVazia() {
                Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

                var listaUsuarios = usuarioService.listar();

                assertNotNull(listaUsuarios);
                assertEquals(0, listaUsuarios.size());
                Mockito.verify(repository, Mockito.times(1)).findAll();
            }
        }

        @Nested
        @DisplayName("3.2 - Cenários de Listagem FindById")
        public class CenarioFindById {
            @Test
            @DisplayName("3.2.1 - Deve Retornar Usuario, quando id passado corretamente")
            void deveRetornarUsuario() {
                Usuario usuario = new Usuario(1, "a", "a", "a", "a", "a");
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(Optional.of(usuario));

                Usuario result = usuarioService.encontrarPorId(idBusca);

                assertEquals(usuario.getIdUsuario(), result.getIdUsuario());
                assertEquals(usuario.getNome(), result.getNome());
                assertEquals(usuario.getEmail(), result.getEmail());
                assertEquals(usuario.getCnpj(), result.getCnpj());
                assertEquals(usuario.getLogo(), result.getLogo());

                Mockito.verify(repository, Mockito.times(1)).findById(any());
            }

            @Test
            @DisplayName("3.2.2 - Deve throwlar NaoEncontradoException, quando id passado não exista")
            void deveThrowlarNaoEncontradoException() {
                Integer idBusca = 1;

                Mockito.when(repository.findById(idBusca)).thenReturn(Optional.empty());

                NaoEncontradoException exception = assertThrows(
                        NaoEncontradoException.class,
                        () -> usuarioService.encontrarPorId(idBusca)
                );

                assertEquals("Usuario não encontrado", exception.getLocalizedMessage());
                Mockito.verify(repository, Mockito.times(1)).findById(idBusca);
            }
        }
    }

    @Nested
    @DisplayName("4 - Cenários de Atualização")
    public class CenarioAtualizacao{
        @Test
        @DisplayName("4.1 - Deve Retornar Usuario Atualizado, quando usuario passado corretamente")
        void deveRetornarUsuarioAtualizado(){
            UsuarioAtualizarDto usuarioAtualizacao = new UsuarioAtualizarDto();
            usuarioAtualizacao.setNome("test");
            usuarioAtualizacao.setEmail("test@test.com");
            usuarioAtualizacao.setCnpj("123456789");
            usuarioAtualizacao.setLogo("logo");

            Usuario usuario = new Usuario();
            usuario.setNome(usuarioAtualizacao.getNome());
            usuario.setEmail(usuarioAtualizacao.getEmail());

            Mockito.when(repository.existsById(anyInt())).thenReturn(true);
            Mockito.when(repository.findById(anyInt())).thenReturn(Optional.of(usuario));
            Mockito.when(repository.save(any())).thenReturn(usuario);

            UsuarioListagemDto result = usuarioService.atualizar(1, usuarioAtualizacao);

            assertEquals(usuarioAtualizacao.getNome(), result.getNome());
            assertEquals(usuarioAtualizacao.getEmail(), result.getEmail());

            Mockito.verify(repository, Mockito.times(1)).existsById(anyInt());
            Mockito.verify(repository, Mockito.times(1)).findById(anyInt());
            Mockito.verify(repository, Mockito.times(1)).save(any());
        }

        @Test
        @DisplayName("4.2 - Deve throwlar NaoEncontradoException, quando id passado não exista")
        void deveThrowlarNaoEncontradoException(){
            UsuarioAtualizarDto usuarioAtualizacao = new UsuarioAtualizarDto();
            usuarioAtualizacao.setNome("test");
            usuarioAtualizacao.setEmail("test@test.com");
            usuarioAtualizacao.setCnpj("123456789");
            usuarioAtualizacao.setLogo("logo");

            Mockito.when(repository.existsById(anyInt())).thenReturn(false);

            NaoEncontradoException exception = assertThrows(NaoEncontradoException.class, () -> {
                usuarioService.atualizar(1, usuarioAtualizacao);
            });

            assertEquals("Usuario Atualizacao não encontrado", exception.getMessage());

            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Usuario.class));
        }
    }

    @Nested
    @DisplayName("5 - Cenários de Atualizar Senha")
    public class CenarioAtualizarSenha{
        @Test
        @DisplayName("5.1 - Deve Retornar Mensagem de Sucesso, quando senha passada corretamente")
        void deveRetornarUsuarioListagemDto(){
            String senhaNova = "newPassword";

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(1);
            usuario.setSenha(senhaNova);

            Mockito.when(repository.existsById(anyInt())).thenReturn(true);
            Mockito.when(repository.findById(anyInt())).thenReturn(Optional.of(usuario));
            Mockito.when(repository.save(any())).thenReturn(usuario);

            String result = usuarioService.atualizarSenha(1, senhaNova);

            assertEquals("Senha Atualizada com Sucesso!", result);

            Mockito.verify(repository, Mockito.times(1)).existsById(anyInt());
            Mockito.verify(repository, Mockito.times(1)).findById(anyInt());
            Mockito.verify(repository, Mockito.times(1)).save(any());
        }

        @Test
        @DisplayName("5.2 - Deve throwlar NaoEncontradoException, quando id passado não exista")
        void deveThrowlarNaoEncontradoException() {
            String senhaNova = "newPassword";

            Mockito.when(repository.existsById(anyInt())).thenReturn(false);

            NaoEncontradoException exception = assertThrows(NaoEncontradoException.class, () -> {
                usuarioService.atualizarSenha(1, senhaNova);
            });

            assertEquals("Usuario não encontrado", exception.getMessage());
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
        }
    }

    @Nested
    @DisplayName("6 - Cenários de Deletar")
    public class CenarioDeletar{
        @Test
        @DisplayName("6.1 - Deve retornar Strign de Suceso, quando id passado corretamente")
        void deveDeletarUsuario(){
            Mockito.when(repository.existsById(Mockito.anyInt())).thenReturn(true);

            String retorno = usuarioService.deletarUsuarioPorId(Mockito.anyInt());

            assertEquals(retorno, "Usuario deletado com sucesso!");
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.any(Integer.class));
        }

        @Test
        @DisplayName("6.2 - Deve throwlar NaoEncontradoException, quando id passado não exista")
        void deveThrowlarNaoEncontradoException(){
            Mockito.when(repository.existsById(Mockito.anyInt())).thenReturn(false);

            NaoEncontradoException exception = assertThrows(NaoEncontradoException.class, () -> {
                usuarioService.deletarUsuarioPorId(Mockito.anyInt());
            });

            assertEquals("Usuario não encontrado", exception.getMessage());
            Mockito.verify(repository, Mockito.times(1)).existsById(Mockito.any(Integer.class));
            Mockito.verify(repository, Mockito.times(0)).deleteById(Mockito.any(Integer.class));
        }
    }


}