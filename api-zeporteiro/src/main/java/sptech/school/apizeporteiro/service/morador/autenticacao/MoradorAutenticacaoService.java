package sptech.school.apizeporteiro.service.morador.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.domain.morador.repository.MoradorRepository;
import sptech.school.apizeporteiro.service.morador.autenticacao.dto.MoradorDetalhesDto;

import java.util.Optional;

@Service("moradorAutenticacaoService") // Nome explícito para o bean
public class MoradorAutenticacaoService implements UserDetailsService {

    private final MoradorRepository moradorRepository;

    @Autowired
    public MoradorAutenticacaoService(MoradorRepository moradorRepository) {
        this.moradorRepository = moradorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return moradorRepository.findByEmail(username)
                .map(MoradorDetalhesDto::new)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Usuário com email '%s' não encontrado", username)
                ));
    }
}
