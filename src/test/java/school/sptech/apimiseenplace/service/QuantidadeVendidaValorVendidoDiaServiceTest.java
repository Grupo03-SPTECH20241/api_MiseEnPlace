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
import school.sptech.apimiseenplace.repository.QuantidadeVendidaValorVendidoDiaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendidoDia;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class QuantidadeVendidaValorVendidoDiaServiceTest {

    @InjectMocks
    private VwQuantidadeVendidaValorVendidoDiaService service;

    @Mock
    private QuantidadeVendidaValorVendidoDiaRepository repository;
    @Nested
    @DisplayName("Cenario de listagem")
    public class cenarioListagem{

        @Test
        @DisplayName("Deve retornar uma lista com um Json de bolo de chocolate")
        void deveRetornarLista(){
            VwQuantidadeVendidaValorVendidoDia vwQuantidadeVendidaValorVendidoDia = new VwQuantidadeVendidaValorVendidoDia("Bolo de Chocolate", LocalDate.of(2024,06,15), 10, 100.0);
            String nome = "Bolo de Chocolate";
            Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(vwQuantidadeVendidaValorVendidoDia));
            assertNotNull(service.listar());
            assertEquals(1, service.listar().size());
            assertEquals("Bolo de Chocolate", service.listar().get(0).getNome());
            assertEquals(LocalDate.of(2024,06,15), service.listar().get(0).getDia());
            assertEquals(10, service.listar().get(0).getQuantidadeVendida());
            assertEquals(100.0, service.listar().get(0).getValorVendido());
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
