package sptech.school.apizeporteiro.domain.morador;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;

@Entity
@Getter
@Setter
@Table(
        name = "morador"
)
public class Morador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Column(unique = true)
    private String numeroWhats1;

    @Column(unique = true)
    private String numeroWhats2;

    @Column(unique = true)
    private String numeroWhats3;

    @ManyToOne
    @JoinColumn(name = "fk_apartamento")
    private Apartamento fkApartamento;
}
