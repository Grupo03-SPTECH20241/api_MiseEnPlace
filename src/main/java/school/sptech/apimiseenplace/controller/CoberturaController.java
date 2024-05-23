package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.cobertura.CoberturaCriacaoDto;
import school.sptech.apimiseenplace.dto.cobertura.CoberturaListagemDto;
import school.sptech.apimiseenplace.dto.cobertura.CoberturaMapper;
import school.sptech.apimiseenplace.dto.cobertura.CoberturaSemPersonalizacaoDto;
import school.sptech.apimiseenplace.dto.recheio.RecheioCriacaoDto;
import school.sptech.apimiseenplace.dto.recheio.RecheioListagemDto;
import school.sptech.apimiseenplace.dto.recheio.RecheioMapper;
import school.sptech.apimiseenplace.dto.recheio.RecheioSemPersonalizacaoDto;
import school.sptech.apimiseenplace.entity.Cobertura;
import school.sptech.apimiseenplace.entity.Recheio;
import school.sptech.apimiseenplace.service.CoberturaService;
import school.sptech.apimiseenplace.service.PersonalizacaoService;

import java.util.List;

@RestController
@RequestMapping("/coberturas")
@RequiredArgsConstructor
public class CoberturaController {

    private final CoberturaService service;
    private final PersonalizacaoService personalizacaoService;

    @PostMapping
    public ResponseEntity<CoberturaListagemDto> criar(@RequestBody @Valid CoberturaCriacaoDto recheioCriacaoDto){
        Cobertura recheioSalvo = service.criar(recheioCriacaoDto);
        return ResponseEntity.ok().body(CoberturaMapper.toDto(recheioSalvo));
    }

    @GetMapping
    public ResponseEntity<List<CoberturaSemPersonalizacaoDto>> listar(){
        List<Cobertura> recheios = service.listar();
        if (recheios.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(CoberturaMapper.toSemPersonalizacaoDto(recheios));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoberturaListagemDto> atualizar(@PathVariable int id, @RequestBody @Valid CoberturaCriacaoDto dtoAtualizacao){
        Cobertura recheioAtualizado = service.atualizar(dtoAtualizacao, id);
        return ResponseEntity.ok(CoberturaMapper.toDto(recheioAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id){

        List<RecheioListagemDto.PersonalizacaoListagem> listaPersonalizacoes = personalizacaoService.listarWhereIdCoberturaEquals(id);

        if(!listaPersonalizacoes.isEmpty()){
            for(RecheioListagemDto.PersonalizacaoListagem p : listaPersonalizacoes){
                personalizacaoService.updateCobertura(null, p.getId());
            }
            service.deletar(id);
            return ResponseEntity.status(204).body("Cobertura Deletada com sucesso!");
        }

        service.deletar(id);
        return ResponseEntity.status(204).body("Cobertura Deletada com sucesso!");
    }

}
