package sptech.school.apizeporteiro.service.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class ClienteCriacaoDto {
    @Email
    private String email;
    @Size(min = 3, max = 20)
    private String nome;
    @Size(min = 6, max = 20)
    private String senha;
    @CNPJ
    private String cnpj;
    private String representante;
}
