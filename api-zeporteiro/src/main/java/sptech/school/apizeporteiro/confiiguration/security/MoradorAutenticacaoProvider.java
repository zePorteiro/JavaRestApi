package sptech.school.apizeporteiro.confiiguration.security;



import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.apizeporteiro.service.morador.autenticacao.MoradorAutenticacaoService;


public class MoradorAutenticacaoProvider implements AuthenticationProvider {
    private final MoradorAutenticacaoService moradorAutenticacaoService;
    private final PasswordEncoder passwordEncoder;

    public MoradorAutenticacaoProvider(
            MoradorAutenticacaoService moradorAutenticacaoService,
            PasswordEncoder passwordEncoder) {
        this.moradorAutenticacaoService = moradorAutenticacaoService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(final Authentication authentication)
            throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        UserDetails userDetails = this.moradorAutenticacaoService
                .loadUserByUsername(username);

        if (this.passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Usuário ou senha inválidos.");
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
