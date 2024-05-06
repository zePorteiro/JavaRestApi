package sptech.school.apizeporteiro.service.cliente.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.cliente.repository.ClienteRepository;
import sptech.school.apizeporteiro.service.cliente.autenticacao.dto.ClienteDetalhesDto;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private ClienteRepository clienteRepository;

    // Método da interface implementada
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(username);

        if (clienteOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("Usuário %s não encontrado", username));
        }

        return new ClienteDetalhesDto(clienteOpt.get());
    }
}
