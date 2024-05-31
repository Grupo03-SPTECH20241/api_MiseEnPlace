package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.personalizacao.PersonalizacaoCriacaoDto;
import school.sptech.apimiseenplace.dto.personalizacao.PersonalizacaoMapper;
import school.sptech.apimiseenplace.dto.produto_pedido.ProdutoPedidoListagemDTO;
import school.sptech.apimiseenplace.dto.recheio.RecheioListagemDto;
import school.sptech.apimiseenplace.entity.Cobertura;
import school.sptech.apimiseenplace.entity.Personalizacao;
import school.sptech.apimiseenplace.service.*;

import java.util.List;

@RestController
@RequestMapping("/personalizacoes")
@RequiredArgsConstructor
public class PersonalizacaoController {

    private final PersonalizacaoService service;
    private final CoberturaService coberturaService;
    private final MassaService massaService;
    private final RecheioService recheioService;
    private final ProdutoPedidoService produtoPedidoService;

    @GetMapping
    public ResponseEntity<List<RecheioListagemDto.PersonalizacaoListagem>> listar(){
        List<RecheioListagemDto.PersonalizacaoListagem> personalizacoes = service.listar();

        if (personalizacoes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(personalizacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecheioListagemDto.PersonalizacaoListagem> encontrarPorId(@PathVariable Integer id){
        Personalizacao personalizacao = service.encontrarPorId(id);

        if (personalizacao == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(PersonalizacaoMapper.toDtoSemPersonalizacao(personalizacao));
    }

    @PostMapping
    public ResponseEntity<RecheioListagemDto.PersonalizacaoListagem> criar(@RequestBody @Valid PersonalizacaoCriacaoDto personalizacaoDto)
    {

        if (personalizacaoDto == null) return ResponseEntity.badRequest().build();

        Personalizacao personalizacao = new Personalizacao();
        personalizacao.setTema(personalizacaoDto.getTema());
        if (personalizacaoDto.getIdCobertura() != null){
            personalizacao.setCobertura(coberturaService.encontrarPorId(personalizacaoDto.getIdCobertura()));
        }

        if (personalizacaoDto.getIdRecheio() != null){
            personalizacao.setRecheio(recheioService.encontrarPorId(personalizacaoDto.getIdRecheio()));
        }

        if (personalizacaoDto.getIdMassa() != null){
            personalizacao.setMassa(massaService.encontrarPorId(personalizacaoDto.getIdMassa()));
        }

        return ResponseEntity.ok(PersonalizacaoMapper.toDtoSemPersonalizacao(service.criar(personalizacao)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecheioListagemDto.PersonalizacaoListagem> atualizar(@PathVariable Integer id, @RequestBody PersonalizacaoCriacaoDto personalizacaoDto){
        Personalizacao personalizacao = service.encontrarPorId(id);

        if (personalizacao == null) return ResponseEntity.noContent().build();

        if(personalizacaoDto.getTema() != null && !personalizacaoDto.getTema().isEmpty() && !personalizacaoDto.getTema().isBlank())personalizacao.setTema(personalizacaoDto.getTema());

        if (personalizacaoDto.getIdCobertura() != null){
            personalizacao.setCobertura(coberturaService.encontrarPorId(personalizacaoDto.getIdCobertura()));
        }

        if (personalizacaoDto.getIdRecheio() != null){
            personalizacao.setRecheio(recheioService.encontrarPorId(personalizacaoDto.getIdRecheio()));
        }

        if (personalizacaoDto.getIdMassa() != null){
            personalizacao.setMassa(massaService.encontrarPorId(personalizacaoDto.getIdMassa()));
        }

        return ResponseEntity.ok(PersonalizacaoMapper.toDtoSemPersonalizacao(service.atualizar(personalizacao, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id){
        Personalizacao personalizacao = service.encontrarPorId(id);

        if (personalizacao != null){
            List<ProdutoPedidoListagemDTO> listaProdutoPedido = produtoPedidoService.listarWhereIdPersonalizacaoEquals(id);

            if(!listaProdutoPedido.isEmpty()){
                for (ProdutoPedidoListagemDTO p : listaProdutoPedido){
                    produtoPedidoService.updatePersonalizacao(null, p.getIdProdutoPedido());
                }
                service.deletar(id);
                return ResponseEntity.status(204).body("Personalizacao deletada com sucesso!");
            }
            service.deletar(id);
            return ResponseEntity.status(204).body("Personalizacao deletada com sucesso!");
        }
        return ResponseEntity.noContent().build();
    }
}
