package school.sptech.apimiseenplace.poc.pilha;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pilha")
public class PilhaBreadcrumbsController {

    private final PilhaBreadcrumbs pilhaBreadcrumbs = new PilhaBreadcrumbs();

    @PostMapping("/{pagina}")
    public ResponseEntity<String[]> adicionarPagina(@PathVariable String pagina) {
        pilhaBreadcrumbs.push(pagina);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<String[]> listar() {
        if (pilhaBreadcrumbs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pilhaBreadcrumbs.getPilha());
    }

    @DeleteMapping
    public ResponseEntity<Void> voltar() {
        this.pilhaBreadcrumbs.pop();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/limpar")
    public ResponseEntity<Void> limparpilha() {
        this.pilhaBreadcrumbs.limpar();
        return ResponseEntity.ok().build();
    }

}
