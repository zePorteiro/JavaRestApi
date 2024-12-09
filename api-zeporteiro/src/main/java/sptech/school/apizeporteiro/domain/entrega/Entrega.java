package sptech.school.apizeporteiro.domain.entrega;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

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
    private Boolean recebido;
    private Integer condominioId;
    @ManyToOne
    @JoinColumn(name = "APARTAMENTO_ID", nullable = false)
    @JsonManagedReference
    private Apartamento apartamento;
    @ManyToOne
    @JoinColumn(name = "FK_PORTEIRO", nullable = false)
    @JsonManagedReference
    private Porteiro porteiro;


}
