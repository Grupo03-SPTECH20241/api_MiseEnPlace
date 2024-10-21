package school.sptech.apimiseenplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.apimiseenplace.entity.Produto;
import school.sptech.apimiseenplace.service.ArquivoTxtService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/txt")
@RequiredArgsConstructor
public class TxtController {
    private final ArquivoTxtService arquivoTxtService;

    @PostMapping("/export/{nomeArq}")
    public ResponseEntity<Void> gravarArquivo(@PathVariable String nomeArq) {
        arquivoTxtService.gravaArquivoTxt(nomeArq);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/import")
    public ResponseEntity<Void> lerArquivo(MultipartFile multipartFile) throws IOException {
        File file = convertToFile(multipartFile);
        arquivoTxtService.leArquivoTxt(file);
        return ResponseEntity.ok().build();
    }

    private File convertToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(convFile);
        return convFile;
    }
}
