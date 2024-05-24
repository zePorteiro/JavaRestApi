package sptech.school.apizeporteiro.domain.condominio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroListagemDto;

import java.util.List;

@Entity
@Getter
@Setter
public class Condominio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    @ManyToOne
    private Cliente cliente;
    @OneToMany(mappedBy = "condominio")
    private List<Apartamento> apartamentos;
    @OneToMany(mappedBy = "condominio")
    private List<Porteiro> porteiros;
}
