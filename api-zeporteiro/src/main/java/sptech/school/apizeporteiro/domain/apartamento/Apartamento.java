package sptech.school.apizeporteiro.domain.apartamento;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.morador.Morador;

import java.util.List;

@Entity
@Table(name = "apartamento")
@Getter
@Setter
public class Apartamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String bloco;
    private String numAp;
    private boolean vazio;

    @ManyToOne
    private Condominio condominio;
    @OneToMany(mappedBy = "apartamento")
    private List<Morador> moradores;
    @OneToMany(mappedBy = "apartamento")
    private List<Entrega> entregas;
}