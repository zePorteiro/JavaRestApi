package sptech.school.apizeporteiro.domain.entrega;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(
        name = "entrega"
)
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tipoEntrega;
    private LocalDate dataRecebimentoPorteiro;
    private LocalDate dataRecebimentoMorador;
    private boolean recebido;
    @ManyToOne
    @JoinColumn(name = "fk_porteiro")
    private Porteiro fkPorteiro;
    @ManyToOne
    @JoinColumn(name = "fk_apartamento")
    private Apartamento fkApartamento;
}
