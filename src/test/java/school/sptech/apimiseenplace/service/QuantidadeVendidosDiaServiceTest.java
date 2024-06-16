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
import school.sptech.apimiseenplace.repository.QuantidadeVendidosDiaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos.VwQuantidadeVendidosDia;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class QuantidadeVendidosDiaServiceTest {
    @InjectMocks
    private QuantidadeVendidosDiaService service;

    @Mock
    private QuantidadeVendidosDiaRepository repository;

    @Nested
    @DisplayName("Cenario de listagem")
    public class cenarioListagem{
        @Test
        @DisplayName("Deve retornar uma lista com dia 11 e quantidade 263")
        void deveRetornarListaComDiaEQuantidade(){
            VwQuantidadeVendidosDia vwQuantidadeVendidosDia = new VwQuantidadeVendidosDia(11, 263);
            Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(vwQuantidadeVendidosDia));

            assertNotNull(service.listar());
            assertEquals(1, service.listar().size());
            assertEquals(11, service.listar().get(0).getDia());
            assertEquals(263, service.listar().get(0).getQuantidadeVendida());
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
