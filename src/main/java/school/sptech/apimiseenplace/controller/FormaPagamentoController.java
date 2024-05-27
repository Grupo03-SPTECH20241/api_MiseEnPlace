package school.sptech.apimiseenplace.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.formaPagamento.FormaPagamentoListagemDto;
import school.sptech.apimiseenplace.dto.formaPagamento.FormaPagamentoMapper;
import school.sptech.apimiseenplace.entity.FormaPagamento;
import school.sptech.apimiseenplace.service.FormaPagamentoService;

import java.util.List;

@RestController
@RequestMapping("/forma-pagamento")
@RequiredArgsConstructor
public class FormaPagamentoController {
    private final FormaPagamentoService service;

    @GetMapping
    public  ResponseEntity<List<FormaPagamentoListagemDto>> listar(){
        List<FormaPagamento> formasPagamento = service.listar();
        if(formasPagamento.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(FormaPagamentoMapper.toDto(formasPagamento));
    }

    @GetMapping("{id}")
    public ResponseEntity<FormaPagamentoListagemDto> buscarPorId(@PathVariable int id){
        FormaPagamento formaPagamento = service.buscarPorId(id);

        return ResponseEntity.ok(FormaPagamentoMapper.toDto(formaPagamento));
    }
}
