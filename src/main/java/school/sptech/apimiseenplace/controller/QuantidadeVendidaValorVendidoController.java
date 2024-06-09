package school.sptech.apimiseenplace.controller;

import jdk.javadoc.doclet.Reporter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.QuantidadeVendidaValorVendidoDto;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.QuantidadeVendidaValorVendidoMapper;
import school.sptech.apimiseenplace.service.QuantidadeVendidaValorVendidoService;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendido;

import java.util.List;

@RestController
@RequestMapping("/quantidade-vendida-valor-vendido")
@RequiredArgsConstructor
public class QuantidadeVendidaValorVendidoController {
    private final QuantidadeVendidaValorVendidoService service;

    @GetMapping
    public ResponseEntity<List<QuantidadeVendidaValorVendidoDto>> listar(){
        List<VwQuantidadeVendidaValorVendido> lista = service.findAll();
        if(lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(QuantidadeVendidaValorVendidoMapper.toDto(lista));
    }

}
