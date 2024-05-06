package sptech.school.apizeporteiro.domain.porteiro;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
        name = "porteiro"
)
public class Porteiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String rg;
    private String senha;
    @ManyToOne
    @JoinColumn(name = "fk_porteiro")
    private Porteiro fkPorteiro;
}
