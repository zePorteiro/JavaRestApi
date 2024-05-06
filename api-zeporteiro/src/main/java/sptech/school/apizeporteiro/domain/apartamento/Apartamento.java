package sptech.school.apizeporteiro.domain.apartamento;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.apizeporteiro.domain.cliente.Cliente;

@Entity
@Table(
        name = "apartamento"
)
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
    @JoinColumn(name = "fk_cliente")
    private Cliente fkCliente;
}
