package sptech.school.apizeporteiro.domain.cliente;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;
import sptech.school.apizeporteiro.domain.condominio.Condominio;

import java.util.List;

@Entity
@Getter
@Setter
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
    private List<Condominio> condominios;
}
