package school.sptech.crudloginsenha;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/dados")
public class DadoController {
    //singleton
    public List<Dado> listaDados= new ArrayList();

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody Dado dado){
        if (!usuarioCadastrado(dado)) {
           listaDados.add(dado);
           return ResponseEntity.status(201).body("Usuário cadastrado com sucesso!");
        }
        return ResponseEntity.status(400).body("Usuário já cadastrado");
    }

    @GetMapping
    public ResponseEntity<List<Dado>> listar() {
        if (listaDados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaDados);
    }

    @PutMapping("/{index}")
    public ResponseEntity<String> atualizar(@RequestBody Dado dado, @PathVariable int index) {
        if(indiceExiste(index)){
            if (!usuarioCadastrado(dado)) {
                listaDados.add(dado);
                return ResponseEntity.status(200).build();
            }else{
                return ResponseEntity.status(400).body("Usuário já cadastrado!");
            }
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Void> deletar(@PathVariable int index){
        if(indiceExiste(index)){
            listaDados.remove(index);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }


    private boolean indiceExiste(int index){
        return index >= 0 && index < listaDados.size();
    }

    private boolean usuarioCadastrado(Dado dado){
        for (Dado d : listaDados){
            if(d.getLogin().equals(dado.getLogin()) ){
                return true;
            }
        }
        return false;
    }


}
