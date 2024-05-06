package sptech.school.apizeporteiro.domain.endereco;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.apizeporteiro.domain.cliente.Cliente;

@Entity
@Getter
@Setter
@Table(
        name = "endereco"
)
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente fkCliente;
}
