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

@RestController
@RequestMapping("/metas")
@RequiredArgsConstructor
public class MetaController {

    private final MetaService metaService;

    @PostMapping
    public ResponseEntity<MetaListagemDto> criarMeta(@RequestBody @Valid MetaCriacaoDto metaCriacaoDto){
        Metas metas = MetaMapper.toEntity(metaCriacaoDto);
        Metas metaSalva =  metaService.criarMeta(metas);
        return ResponseEntity.ok().body(MetaMapper.toMetaListagemDto(metaSalva));
    }


    @GetMapping
    public ResponseEntity<MetaListagemDto> listarMetas(){
        Metas metas = metaService.encontrarPorId(1);
        MetaListagemDto dto = MetaMapper.toMetaListagemDto(metas);
        dto.setValorRealizado(metaService.AcompanhamentoMeta());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/in-range/{inRange}")
    public ResponseEntity<MetaListagemDto> atualizarMeta(@PathVariable int id, @PathVariable boolean inRange, @RequestBody @Valid MetaCriacaoDto dtoAtualizacao){
        Metas meta = MetaMapper.toEntity(dtoAtualizacao);

        Metas metaAtualizada;

        if (inRange) {
            metaAtualizada = metaService.atualizarMetaInRange(id, meta);
        } else {
            metaAtualizada = metaService.atualizarMetaNotInRange(id, meta);
        }

        if (metaAtualizada == null){
            return ResponseEntity.noContent().build();
        }

        MetaListagemDto metaListagemDto = MetaMapper.toMetaListagemDto(metaAtualizada);
        metaListagemDto.setValorRealizado(metaService.AcompanhamentoMeta());

        return ResponseEntity.ok(metaListagemDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarMeta(@PathVariable int id){
        metaService.deletarMetaPorId(id);
        return ResponseEntity.ok("Meta deletada com sucesso");
    }























}
