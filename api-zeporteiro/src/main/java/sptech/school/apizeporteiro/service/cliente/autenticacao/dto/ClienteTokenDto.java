package sptech.school.apizeporteiro.service.cliente.autenticacao.dto;

public class ClienteTokenDto {
    private Integer userId;
    private String nome;
    private String email;
    private String token;

    private Integer condominioid;

    public Integer getCondominioid() {
        return condominioid;
    }

    public void setCondominioid(Integer condominioid) {
        this.condominioid = condominioid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
