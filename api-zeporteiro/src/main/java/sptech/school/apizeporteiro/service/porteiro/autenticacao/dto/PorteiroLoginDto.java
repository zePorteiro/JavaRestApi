package sptech.school.apizeporteiro.service.porteiro.autenticacao.dto;

public class PorteiroLoginDto {

    private String rg;

    private String senha;

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
