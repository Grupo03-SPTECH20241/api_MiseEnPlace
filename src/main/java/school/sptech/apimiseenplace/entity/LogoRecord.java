package school.sptech.apimiseenplace.entity;

import org.springframework.boot.context.properties.bind.Nested;

public record LogoRecord(
        String body,
        int statusCode
) {
}



