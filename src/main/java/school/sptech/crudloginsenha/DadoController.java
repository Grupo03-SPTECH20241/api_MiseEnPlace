package school.sptech.crudloginsenha;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/dados")
public class DadoController{
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
    public ResponseEntity<DadoResumoDTO> atualizar(@RequestBody Dado dado, @PathVariable int index) {
        if (indiceExiste(index)) {
            listaDadosCadastrados.set(index,dado);
            dadosToDadosDTO();
            return ResponseEntity.status(200).body(listaDadosDTO.get(index));
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

    @GetMapping("/getOrdenadoInsertion")
    public ResponseEntity<DadoResumoDTO[]> ordenarInsertion(){
        if (listaDadosCadastrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        dadosToDadosDTO();
        int j;
        DadoResumoDTO x;
        DadoResumoDTO[] v = listaDadosDTO.toArray(new DadoResumoDTO[0]);
        for (int i = 1; i < v.length; i++) {
            x = v[i];
            j = i -1;
            while (j >= 0 && v[j].getLogin().compareTo(x.getLogin()) > 0){
                v[j+1] = v[j];
                j = j-1;
            }
            v[j+1] = x;
        }
        return ResponseEntity.status(200).body(v);
    }

    @GetMapping("/getOrdenadoSelection")
    public ResponseEntity<DadoResumoDTO[]> ordenarSelection(){
        if(listaDadosCadastrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        dadosToDadosDTO();
        DadoResumoDTO[] v = listaDadosDTO.toArray(new DadoResumoDTO[0]);

        for (int i = 0; i < v.length; i++) {
            for (int j = i+ 1; j < v.length; j++) {
                if(v[j].getLogin().compareTo(v[i].getLogin()) < 0){
                    DadoResumoDTO aux = v[j];
                    v[j] = v[i];
                    v[i] = aux;
                }
            }
        }
        return ResponseEntity.status(200).body(v);
    }

    @GetMapping("/getOrdenadoBubble")
    public ResponseEntity<DadoResumoDTO[]> ordenarBubble(){
        if(listaDadosCadastrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        dadosToDadosDTO();
        DadoResumoDTO[] v = listaDadosDTO.toArray(new DadoResumoDTO[0]);

        for (int i = 0; i < v.length; i++) {
            for (int j = 1; j < v.length - 1; j++) {
                if(v[j-1].getLogin().compareTo(v[i].getLogin()) > 0){
                    DadoResumoDTO aux = v[j - 1];
                    v[j -1] = v[i];
                    v[i] = aux;
                }
            }
        }
        return ResponseEntity.status(200).body(v);
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
                    dadoAtual.setNome(dado.getNome());
                    dadoAtual.setTelefone(dado.getTelefone());
                    dadoAtual.setSenha(Base64.getEncoder().encodeToString(dado.getSenhaCifrada()));
                    listaDadosDTO.add(dadoAtual);
                }
        );
    }

}
