package sptech.school.apizeporteiro.domain.porteiro;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sptech.school.apizeporteiro.controller.CondominioController;
import sptech.school.apizeporteiro.domain.condominio.Condominio;

@Entity
@Table(
        name = "porteiro"
)
@Getter
@Setter
public class Porteiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String rg;
    private String senha;
    @ManyToOne
    private Condominio condominio;
}
