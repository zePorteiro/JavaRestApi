package sptech.school.apizeporteiro.service.morador.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class CadastroMoradorDto {

    private String nome;
    @Email
    private String email;

    private String senha;
    @CPF
    private String cpf;
    @Min(8)
    private String cep;
}
