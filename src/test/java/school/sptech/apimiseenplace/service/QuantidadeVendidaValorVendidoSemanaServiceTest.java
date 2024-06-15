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
import school.sptech.apimiseenplace.repository.QuantidadeVendidaValorVendidoSemanaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendidoSemana;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class QuantidadeVendidaValorVendidoSemanaServiceTest {
    @InjectMocks
    private QuantidadeVendidaValorVendidoSemanaService service;

    @Mock
    private QuantidadeVendidaValorVendidoSemanaRepository repository;

    @Nested
    @DisplayName("Cenario de listagem")
    public class cenarioListagem{
     @Test
     @DisplayName("Deve retornar uma lista com um bolo de chocolate")
        void deveRetornarLista(){
            VwQuantidadeVendidaValorVendidoSemana vwQuantidadeVendidaValorVendidoSemana = new VwQuantidadeVendidaValorVendidoSemana("Bolo de Chocolate", LocalDate.of(2024,06,15), 10, 100.0);
            Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(vwQuantidadeVendidaValorVendidoSemana));
            assertNotNull(service.listar());
            assertEquals(1, service.listar().size());
            assertEquals("Bolo de Chocolate", service.listar().get(0).getNome());
            assertEquals(LocalDate.of(2024,06,15), service.listar().get(0).getDia());
            assertEquals(10, service.listar().get(0).getQuantidadeVendida());
            assertEquals(100.0, service.listar().get(0).getValorVendido());
        }
    }
}
