package school.sptech.apimiseenplace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recheio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRecheio;
    private String nome;
    private Double preco;
}
