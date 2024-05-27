package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.metas.MetaCriacaoDto;
import school.sptech.apimiseenplace.dto.metas.MetaListagemDto;
import school.sptech.apimiseenplace.dto.metas.MetaMapper;
import school.sptech.apimiseenplace.entity.Metas;
import school.sptech.apimiseenplace.service.MetaService;

import java.util.List;

@RestController
@RequestMapping("/metas")
@RequiredArgsConstructor
public class MetaController {

    private final MetaService metaService;

    @PostMapping
    public ResponseEntity<MetaListagemDto> criarMeta(@RequestBody @Valid MetaCriacaoDto metaCriacaoDto){
        Metas metaSalva =  metaService.criarMeta(metaCriacaoDto);
        return ResponseEntity.ok().body(MetaMapper.toMetaListagemDto(metaSalva));
    }


    @GetMapping
    public ResponseEntity<List<MetaListagemDto>> listarMetas(){
        List<Metas> metas = metaService.listarMetas();
        if (metas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(MetaMapper.toMetaListagemDto(metas));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaListagemDto> atualizarMeta(@PathVariable int id, @RequestBody @Valid MetaCriacaoDto dtoAtualizacao){
        Metas metaAtualizada = metaService.atualizarMeta(id, dtoAtualizacao);

        if (metaAtualizada == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(MetaMapper.toMetaListagemDto(metaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarMeta(@PathVariable int id){
        metaService.deletarMetaPorId(id);
        return ResponseEntity.ok("Meta deletada com sucesso");
    }























}
