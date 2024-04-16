package school.sptech.crudloginsenha;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crudloginsenha.dto.DadoResumoDTO;
import school.sptech.crudloginsenha.entity.Dado;

import java.util.ArrayList;
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
        if(!emailCorreto(dado.getLogin())){
            return ResponseEntity.status(400).body("Email inválido");
        }
        if(!telefoneCorreto(dado.getTelefone())){
            return ResponseEntity.status(400).body("Telefone Invalido(Precisa ter 11 caracteres)");
        }
        if (!usuarioCadastrado(dado)) {
            listaDadosCadastrados.add(dado);
            return ResponseEntity.status(201).body("Usuário cadastrado com sucesso!");
        }
        return ResponseEntity.status(409).body("Usuário já cadastrado");
    }

    @PostMapping("/lista")
    public ResponseEntity<String> cadastrarLista(@RequestBody List<Dado> dados) {
        if (dados.isEmpty()) {
            return ResponseEntity.status(400).body("Lista de cadastro vazia");
        }
        for (int i = 0; i < dados.size(); i++) {
            if(!emailCorreto(dados.get(i).getLogin())){
                return ResponseEntity.status(400).body("Email inválido");
            }
            if(!telefoneCorreto(dados.get(i).getTelefone())){
                return ResponseEntity.status(400).body("Telefone Invalido(Precisa ter 11 caracteres)");
            }
            if (!usuarioCadastrado(dados.get(i))) {
                listaDadosCadastrados.add(dados.get(i));
            } else {
                return ResponseEntity.status(409).body("Usuário já cadastrado");
            }
        }
        return ResponseEntity.status(204).body("Usuários cadastrados");
    }

    private boolean telefoneCorreto(String telefone){
        return telefone.length() == 11;
    }


    private boolean emailCorreto(String email){
        return email.contains("@");
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

    @GetMapping("/get-ordenado-login")
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
            while (j >= 0 && v[j].getLogin().compareToIgnoreCase(x.getLogin()) > 0){
                v[j+1] = v[j];
                j = j-1;
            }
            v[j+1] = x;
        }
        return ResponseEntity.status(200).body(v);
    }

    @GetMapping("/get-ordenado-nome")
    public ResponseEntity<DadoResumoDTO[]> ordenarSelection(){
        if(listaDadosCadastrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        dadosToDadosDTO();
        DadoResumoDTO[] v = listaDadosDTO.toArray(new DadoResumoDTO[0]);

        for (int i = 0; i < v.length; i++) {
            for (int j = i+ 1; j < v.length; j++) {
                if(v[j].getNome().compareTo(v[i].getNome()) < 0){
                    DadoResumoDTO aux = v[j];
                    v[j] = v[i];
                    v[i] = aux;
                }
            }
        }
        return ResponseEntity.status(200).body(v);
    }

    @GetMapping("/get-ordenado-telefone")
    public ResponseEntity<DadoResumoDTO[]> ordenarBubble(){
        if(listaDadosCadastrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        dadosToDadosDTO();
        DadoResumoDTO[] v = listaDadosDTO.toArray(new DadoResumoDTO[0]);

        for (int i = 0; i < v.length; i++) {
            for (int j = 1; j < v.length; j++) {
                if(v[j-1].getTelefone().compareTo(v[i].getTelefone()) > 0){
                    DadoResumoDTO aux = v[j - 1];
                    v[j -1] = v[i];
                    v[i] = aux;
                }
            }
        }
        return ResponseEntity.status(200).body(v);
    }

    @GetMapping("/get-login-quicksort")
    public ResponseEntity<DadoResumoDTO[]> loginQuickSort() {
        DadoResumoDTO[] v = new DadoResumoDTO[0];
        return ordenarLoginQuicksort(v, 0, v.length);
    }

    public ResponseEntity<DadoResumoDTO[]> ordenarLoginQuicksort(
            DadoResumoDTO[] v,
            int indInicio,
            int indFim
    ) {
        if (v.length == 0) {
            dadosToDadosDTO();
            v = listaDadosDTO.toArray(new DadoResumoDTO[0]);
            indInicio = 0;
            indFim = v.length;
            if (v.length == 0) {
                return ResponseEntity.status(204).body(v);
            }
        }
        int i, j;
        String pivo;

        i = indInicio;
        j = indFim - 1;
        pivo = v[(indInicio + indFim) / 2].getLogin();

        while (i <= j) {
            while (i < indFim && v[i].getLogin().compareTo(pivo) < 0) {
                i = i + 1;
            }
            while (j > indInicio && v[j].getLogin().compareTo(pivo) > 0) {
                j = j - 1;
            }
            if (i <= j) {
                DadoResumoDTO temp = v[i];
                v[i] = v[j];
                v[j] = temp;
                i = i + 1;
                j = j - 1;
            }
        }

        if (indInicio < j) {
            ordenarLoginQuicksort(v, indInicio, j);
        }
        if (i < indFim) {
            ordenarLoginQuicksort(v, i, indFim);
        }

        return ResponseEntity.status(200).body(v);
    }

    @GetMapping("/get-nome-quicksort")
    public ResponseEntity<DadoResumoDTO[]> nomeQuicksort() {
        DadoResumoDTO[] v = new DadoResumoDTO[0];
        return ordenarNomeQuicksort(v, 0, v.length);
    }

    public ResponseEntity<DadoResumoDTO[]> ordenarNomeQuicksort(
            DadoResumoDTO[] v,
            int indInicio,
            int indFim
    ) {
        if (v.length == 0) {
            dadosToDadosDTO();
            v = listaDadosDTO.toArray(new DadoResumoDTO[0]);
            indInicio = 0;
            indFim = v.length;
            if (v.length == 0) {
                return ResponseEntity.status(204).body(v);
            }
        }
        int i, j;
        String pivo;
        i = indInicio;
        j = indFim - 1;
        pivo = v[(indInicio + indFim) / 2].getNome();
        while (i <= j) {
            while (i < indFim && v[i].getNome().compareTo(pivo) < 0) {
                i = i + 1;
            }
            while (j > indInicio && v[j].getNome().compareTo(pivo) > 0) {
                j = j - 1;
            }
            if (i <= j) {
                DadoResumoDTO temp = v[i];
                v[i] = v[j];
                v[j] = temp;
                i = i + 1;
                j = j - 1;
            }
        }
        if (indInicio < j) {
            ordenarNomeQuicksort(v, indInicio, j);
        }
        if (i < indFim) {
            ordenarNomeQuicksort(v, i, indFim);
        }
        return ResponseEntity.status(200).body(v);
    }

    @GetMapping("/get-telefone-quicksort")
    public ResponseEntity<DadoResumoDTO[]> telefoneQuicksort() {
        DadoResumoDTO[] v = new DadoResumoDTO[0];
        return ordenarTelefoneQuicksort(v, 0, v.length);
    }

    public ResponseEntity<DadoResumoDTO[]> ordenarTelefoneQuicksort(
            DadoResumoDTO[] v,
            int indInicio,
            int indFim
    ) {
        if (v.length == 0) {
            dadosToDadosDTO();
            v = listaDadosDTO.toArray(new DadoResumoDTO[0]);
            indInicio = 0;
            indFim = v.length;
            if (v.length == 0) {
                return ResponseEntity.status(204).body(v);
            }
        }
        int i, j;
        String pivo;
        i = indInicio;
        j = indFim - 1;
        pivo = v[(indInicio + indFim) / 2].getTelefone();
        while (i <= j) {
            while (i < indFim && v[i].getTelefone().compareTo(pivo) < 0) {
                i = i + 1;
            }
            while (j > indInicio && v[j].getTelefone().compareTo(pivo) > 0) {
                j = j - 1;
            }
            if (i <= j) {
                DadoResumoDTO temp = v[i];
                v[i] = v[j];
                v[j] = temp;
                i = i + 1;
                j = j - 1;
            }
        }
        if (indInicio < j) {
            ordenarNomeQuicksort(v, indInicio, j);
        }
        if (i < indFim) {
            ordenarNomeQuicksort(v, i, indFim);
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