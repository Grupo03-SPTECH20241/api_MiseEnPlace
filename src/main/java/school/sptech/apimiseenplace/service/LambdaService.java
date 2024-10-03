package school.sptech.apimiseenplace.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class LambdaService {
    private LambdaClient lambdaClient;

    public InvokeResponse sendToLambda(String funcao, String bucket, String arquivo, String nomeArquivo) {
        lambdaClient = LambdaClient.builder().region(Region.US_EAST_1).build();

        SdkBytes payload = SdkBytes.fromUtf8String(
                """
                        {
                        "imagem_base64" : "%s",
                        "nome_arquivo" : "%s.jpg",
                        "nome_bucket" : "%s"
                        }
                        """.formatted(arquivo, nomeArquivo,bucket)
        );

        InvokeRequest request = InvokeRequest.builder()
                .functionName(funcao)
                .payload(payload)
                .build();
        return lambdaClient.invoke(request);
    }
}
