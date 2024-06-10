package school.sptech.apimiseenplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidos.vw_quantidade_vendida_mes.QuantidadeVendidosListagemDto;
import school.sptech.apimiseenplace.service.QuantidadeVendidosMesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quantidade-vendidos-mes")
public class QuantidadeVendidosMesController {

    private final QuantidadeVendidosMesService quantidadeVendidosMesService;

    @GetMapping
    public ResponseEntity<List<QuantidadeVendidosListagemDto>> listar(){
        List<QuantidadeVendidosListagemDto> quantidadeVendidos = quantidadeVendidosMesService.listar();
        if (quantidadeVendidos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(quantidadeVendidos);
    }

}
