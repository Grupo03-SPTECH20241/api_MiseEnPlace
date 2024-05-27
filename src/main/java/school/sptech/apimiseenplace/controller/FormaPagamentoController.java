package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.formaPagamento.FormaPagamentoCriacaoDto;
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

    @PostMapping
    public ResponseEntity<FormaPagamentoListagemDto> criar(@RequestBody @Valid FormaPagamentoCriacaoDto formaPagamentoListagemDto){
        FormaPagamento formaPagamento = service.criar(formaPagamentoListagemDto);
        return ResponseEntity.ok(FormaPagamentoMapper.toDto(formaPagamento));
    }

    @PutMapping("{id}")
    public ResponseEntity<FormaPagamentoListagemDto> atualizar(@PathVariable int id, @RequestBody @Valid FormaPagamentoCriacaoDto formaPagamentoCriacaoDto){
        FormaPagamento formaPagamento = service.atualizar(id, formaPagamentoCriacaoDto);
        return ResponseEntity.ok(FormaPagamentoMapper.toDto(formaPagamento));
    }

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
