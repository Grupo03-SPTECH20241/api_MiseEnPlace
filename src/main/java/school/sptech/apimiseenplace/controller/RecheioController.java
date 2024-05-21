package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.recheio.RecheioCriacaoDto;
import school.sptech.apimiseenplace.dto.recheio.RecheioListagemDto;
import school.sptech.apimiseenplace.dto.recheio.RecheioMapper;
import school.sptech.apimiseenplace.entity.Recheio;
import school.sptech.apimiseenplace.service.RecheioService;

import java.util.List;

@RestController
@RequestMapping("/recheios")
@RequiredArgsConstructor
public class RecheioController {

    private final RecheioService service;

    @PostMapping
    public ResponseEntity<RecheioListagemDto> criar(@RequestBody @Valid RecheioCriacaoDto recheioCriacaoDto){
        Recheio recheioSalvo = service.cadastrarRecheio(recheioCriacaoDto);
        return ResponseEntity.ok().body(RecheioMapper.toDto(recheioSalvo));
    }

    @GetMapping
    public ResponseEntity<List<RecheioListagemDto>> listar(){
        List<Recheio> recheios = service.listarRecheios();
        if (recheios.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(RecheioMapper.toDto(recheios));
    }
}