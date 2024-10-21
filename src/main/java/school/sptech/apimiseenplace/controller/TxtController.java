package school.sptech.apimiseenplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @GetMapping("/export")
    public ResponseEntity<Resource> gravarArquivo() {
        File file = arquivoTxtService.gravaArquivoTxt();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=produtos-exportados.txt");

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
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
