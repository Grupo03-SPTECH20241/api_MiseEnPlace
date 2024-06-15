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
import school.sptech.apimiseenplace.repository.QuantidadeVendidaValorVendidoRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendido;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class QuantidadeVendidaValorVendidoServiceTest {
    @InjectMocks
    private QuantidadeVendidaValorVendidoService service;

    @Mock
    private QuantidadeVendidaValorVendidoRepository repository;
    @Nested
    @DisplayName("Cenario de listagem")
    public class cenarioListagem{
        @Test
        @DisplayName("Deve retornar uma lista com um Json de bolo de baunilha")
        void deveRetornarLista(){
            VwQuantidadeVendidaValorVendido vwQuantidadeVendidaValorVendido = new VwQuantidadeVendidaValorVendido("Bolo de Baunilha", 1, 10, 100.0);
            Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(vwQuantidadeVendidaValorVendido));
            assertNotNull(service.findAll());
            assertEquals(1, service.findAll().size());
            assertEquals("Bolo de Baunilha", service.findAll().get(0).getNome());
            assertEquals(1, service.findAll().get(0).getMes());
            assertEquals(10, service.findAll().get(0).getQuantidadeVendida());
            assertEquals(100.0, service.findAll().get(0).getValorVendido());

        }

        @Test
        @DisplayName("Deve retornar uma lista vazia")
        void deveRetornarListaVazia(){
            Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());
            assertNotNull(service.findAll());
            assertEquals(0, service.findAll().size());
        }



    }






}
