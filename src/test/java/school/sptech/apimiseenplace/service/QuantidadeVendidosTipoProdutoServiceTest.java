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
import school.sptech.apimiseenplace.repository.QuantidadeVendidosTipoProdutoRepository;
import school.sptech.apimiseenplace.view.graficoTipoProduto.VwTipoProduto;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class QuantidadeVendidosTipoProdutoServiceTest {
    @InjectMocks
    private QuantidadeVendidosTipoProdutoService service;

    @Mock
    private QuantidadeVendidosTipoProdutoRepository repository;

    @Nested
    @DisplayName("Cenario de listagem")
    public class cenarioListagem{
        @Test
        @DisplayName("Deve retornar um json com o id, tipo de produto e quantidade vendida")
        void deveRetornarLista(){
            VwTipoProduto vwTipoProduto = new VwTipoProduto(1, "Bolo", 10);
            Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(vwTipoProduto));
            assertNotNull(service.findAll());
            assertEquals(1,service.findAll().size());
            assertEquals(1, service.findAll().get(0).getId());
            assertEquals("Bolo", service.findAll().get(0).getTipoProduto());
            assertEquals(10, service.findAll().get(0).getQuantidadeVendida());
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
