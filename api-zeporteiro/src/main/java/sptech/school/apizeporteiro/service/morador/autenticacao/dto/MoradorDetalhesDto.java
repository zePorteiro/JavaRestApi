package sptech.school.apizeporteiro.service.morador.autenticacao.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.morador.Morador;

import java.util.Collection;


public class MoradorDetalhesDto implements UserDetails {
    private final String email;
    private final String nome;
    private final String senha;

    public MoradorDetalhesDto(Morador morador) {
        this.nome = morador.getNome();
        this.email = morador.getEmail();
        this.senha = morador.getSenha();
    }

    public String getNome() {
        return nome;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
