package school.sptech.crudloginsenha;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/dados")
public class DadoController {
    //singleton
    public List<Dado> listaDadosCadastrados = new ArrayList<>();
    public List<DadoResumoDTO> listaDadosDTO = new ArrayList<>();

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody Dado dado) {
        if (!usuarioCadastrado(dado)) {
            listaDadosCadastrados.add(dado);
            return ResponseEntity.status(201).body("Usuário cadastrado com sucesso!");
        }
        return ResponseEntity.status(409).body("Usuário já cadastrado");
    }

    @GetMapping
    public ResponseEntity<List<DadoResumoDTO>> listar() {

        if (listaDadosCadastrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        dadosToDadosDTO();
        return ResponseEntity.status(200).body(listaDadosDTO);
    }

    @GetMapping("/{index}")
    public ResponseEntity<DadoResumoDTO> getComIndex(@PathVariable int index) {
        if (indiceExiste(index)) {
            dadosToDadosDTO();
            return ResponseEntity.status(200).body(listaDadosDTO.get(index));
        }
        return ResponseEntity.status(400).build();
    }

    @PutMapping("/{index}")
    public ResponseEntity<String> atualizar(@RequestBody Dado dado, @PathVariable int index) {
        if (indiceExiste(index)) {
            if (!usuarioCadastrado(dado)) {
                listaDadosCadastrados.add(dado);
                return ResponseEntity.status(200).build();
            } else {
                return ResponseEntity.status(400).body("Usuário já cadastrado!");
            }
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Void> deletar(@PathVariable int index) {
        if (indiceExiste(index)) {
            listaDadosCadastrados.remove(index);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }


    private boolean indiceExiste(int index) {
        return index >= 0 && index < listaDadosCadastrados.size();
    }

    private boolean usuarioCadastrado(Dado dado) {
        for (Dado d : listaDadosCadastrados) {
            if (d.getLogin().equals(dado.getLogin())) {
                return true;
            }
        }
        return false;
    }

    public void dadosToDadosDTO() {
        listaDadosDTO = new ArrayList<>();
        listaDadosCadastrados.forEach(dado -> {
                    DadoResumoDTO dadoAtual = new DadoResumoDTO();
                    dadoAtual.setLogin(dado.getLogin());
                    dadoAtual.setSenha(Base64.getEncoder().encodeToString(dado.getSenhaCifrada()));
                    listaDadosDTO.add(dadoAtual);
                }
        );
    }
}
