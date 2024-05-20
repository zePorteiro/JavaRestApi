package sptech.school.apizeporteiro.domain.cliente;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
        name = "cliente"
)
//@AttributeOverrides({
//        @AttributeOverride(name = "id", column = @Column(name = "athlete_id")),
//        @AttributeOverride(name = "firstName", column = @Column(name = "first_name", nullable = false)),
//        @AttributeOverride(name = "lastName", column = @Column(name = "last_name", nullable = false)),
//        @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date", nullable = false)),
//        @AttributeOverride(name = "phone", column = @Column(name = "phone", nullable = true)),
//        @AttributeOverride(name = "picture", column = @Column(name = "picture", nullable = true))
//})
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String nome;
    private String senha;
    private String cnpj;
    private String representante;
}
