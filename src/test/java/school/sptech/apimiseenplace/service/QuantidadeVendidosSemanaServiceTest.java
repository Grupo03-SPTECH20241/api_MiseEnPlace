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
import school.sptech.apimiseenplace.repository.QuantidadeVendidosSemanaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos.VwQuantidadeVendidosSemana;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class QuantidadeVendidosSemanaServiceTest {
    @InjectMocks
    private QuantidadeVendidosSemanaService service;

    @Mock
    private QuantidadeVendidosSemanaRepository repository;

    @Nested
    @DisplayName("Cenario de listagem")
    public class cenarioListagem{

        @Test
        @DisplayName("Deve retornar uma lista com a data e a quantidade vendida")
        void deveRetornarLista(){
            VwQuantidadeVendidosSemana vwQuantidadeVendidosSemana = new VwQuantidadeVendidosSemana(LocalDate.of(2024, 06, 15), 10);
            Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(vwQuantidadeVendidosSemana));
            assertNotNull(service.listar());
            assertEquals(1, service.listar().size());
            assertEquals(LocalDate.of(2024, 06, 15), service.listar().get(0).getDia());
        }

        @Test
        @DisplayName("Deve retornar uma lista vazia")
        void deveRetornarListaVazia(){
            Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());
            assertNotNull(service.listar());
            assertEquals(0, service.listar().size());
        }

    }
}
