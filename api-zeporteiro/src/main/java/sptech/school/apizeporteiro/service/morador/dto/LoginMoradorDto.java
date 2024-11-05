package sptech.school.apizeporteiro.service.morador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginMoradorDto {

    @NotBlank(message = "Email não pode estar em branco")
    private String email;

    @Size(min = 5, message = "Senha deve ser maior que ou igual à 5")
    private String senha;

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}