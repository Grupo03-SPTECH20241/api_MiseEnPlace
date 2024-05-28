package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.cobertura.CoberturaMapper;
import school.sptech.apimiseenplace.dto.massa.MassaCriacaoDto;
import school.sptech.apimiseenplace.dto.massa.MassaListagemDto;
import school.sptech.apimiseenplace.dto.massa.MassaMapper;
import school.sptech.apimiseenplace.dto.massa.MassaSemPersonalizacaoDto;
import school.sptech.apimiseenplace.dto.recheio.RecheioListagemDto;
import school.sptech.apimiseenplace.entity.Cobertura;
import school.sptech.apimiseenplace.entity.Massa;
import school.sptech.apimiseenplace.service.MassaService;
import school.sptech.apimiseenplace.service.PersonalizacaoService;

import java.util.List;

@RestController
@RequestMapping("/massas")
@RequiredArgsConstructor
public class MassaController {

    private final MassaService service;
    private final PersonalizacaoService personalizacaoService;

    @PostMapping
    public ResponseEntity<MassaListagemDto> criar(@RequestBody @Valid MassaCriacaoDto massaCriacaoDto){
        Massa massaSalva = service.criar(massaCriacaoDto);
        return ResponseEntity.ok(MassaMapper.toDto(massaSalva));
    }

    @GetMapping
    public ResponseEntity<List<MassaSemPersonalizacaoDto>> listar(){
        List<Massa> massas = service.listar();
        if(massas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(MassaMapper.toSemPersonalizacaoDto(massas));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MassaListagemDto> atualizar(@PathVariable int id, @RequestBody @Valid MassaCriacaoDto massaCriacaoDto){
        Massa massaAtualizada = service.atualizar(massaCriacaoDto, id);
        return ResponseEntity.ok(MassaMapper.toDto(massaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id){

        Massa massa = service.encontrarPorId(id);

        if(massa != null){
            List<RecheioListagemDto.PersonalizacaoListagem> listaPersonalizacoes = personalizacaoService.listarWhereIdMassaEquals(id);

            if(!listaPersonalizacoes.isEmpty()){
                for (RecheioListagemDto.PersonalizacaoListagem p : listaPersonalizacoes){
                    personalizacaoService.updateMassa(null, p.getId());
                }
                service.deletar(id);
                return ResponseEntity.status(204).body("Massa deletada com Sucesso!");
            }

            service.deletar(id);
            return ResponseEntity.status(204).body("Massa deletada com Sucesso!");
        }

        return ResponseEntity.noContent().build();
    }

}
