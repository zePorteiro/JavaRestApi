package sptech.school.apizeporteiro.domain.porteiro;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sptech.school.apizeporteiro.controller.CondominioController;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.entrega.Entrega;

import java.util.ArrayList;
import java.util.List;

@Entity
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
    @OneToMany(mappedBy = "porteiro")
    @JsonBackReference
    private List<Entrega> entregas = new ArrayList<>();
}
