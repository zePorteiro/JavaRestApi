package sptech.school.apizeporteiro.domain.morador;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.entrega.Entrega;

import java.util.List;

@Entity
@Getter
@Setter
public class Morador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String numeroWhats1;
    private String numeroWhats2;
    private String numeroWhats3;

    @ManyToOne
    private Apartamento apartamento;
}
