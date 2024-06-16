package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.apimiseenplace.repository.QuantidadeVendidosRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos.VwQuantidadeVendidosMes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class QuantidadeVendidosMesServiceTest {

    @InjectMocks
    private QuantidadeVendidosMesService service;

    @Mock
    private QuantidadeVendidosRepository repository;

    @Nested
    @DisplayName("Cenario de listagem")
    public class cenarioListagem{

        @Test
        @DisplayName("Deve retornar uma lista com o mes e a quantidade vendida")
        void deveRetornarLista(){
            VwQuantidadeVendidosMes vwQuantidadeVendidosMes = new VwQuantidadeVendidosMes(1, 10);
            Integer mes = 1;
            Integer quantidadeVendida = 10;

            Mockito.when(repository.findAll()).thenReturn(java.util.Collections.singletonList(vwQuantidadeVendidosMes));
            assertNotNull(service.listar());
            assertEquals(1, service.listar().size());
            assertEquals(1, service.listar().get(0).getMes());
            assertEquals(10, service.listar().get(0).getQuantidadeVendida());
        }

        @Test
        @DisplayName("Deve retornar uma lista vazia")
        void deveRetornarListaVazia(){
            assertNotNull(service.listar());
            assertEquals(0, service.listar().size());
        }

    }

}
