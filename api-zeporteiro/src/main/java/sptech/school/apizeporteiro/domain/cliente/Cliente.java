package sptech.school.apizeporteiro.domain.cliente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;
import sptech.school.apizeporteiro.domain.condominio.Condominio;

import java.util.List;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String nome;
    private String senha;
    private String cnpj;
    private String representante;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Condominio> condominios;
}
