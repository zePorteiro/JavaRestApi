package sptech.school.apizeporteiro.service.cliente;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.apizeporteiro.confiiguration.security.jwt.GerenciadorTokenJwt;
import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.cliente.repository.ClienteRepository;
import sptech.school.apizeporteiro.mapper.ClienteMapper;
import sptech.school.apizeporteiro.service.cliente.autenticacao.dto.ClienteLoginDto;
import sptech.school.apizeporteiro.service.cliente.autenticacao.dto.ClienteTokenDto;
import sptech.school.apizeporteiro.service.cliente.dto.ClienteCriacaoDto;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ClienteService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ClienteRepository clienteRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ClienteTokenDto criar(ClienteCriacaoDto clienteCriacaoDto){
        final Cliente novoCliente = ClienteMapper.of(clienteCriacaoDto);
        String senhaCriptografada = passwordEncoder.encode(novoCliente.getSenha());
        novoCliente.setSenha(senhaCriptografada);

        this.clienteRepository.save(novoCliente);

        // Autenticar o novo cliente para gerar o token
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                clienteCriacaoDto.getEmail(), clienteCriacaoDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return ClienteMapper.of(novoCliente, token);
    }

    public ClienteTokenDto autenticar(ClienteLoginDto clienteLoginDto){

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                clienteLoginDto.getEmail(), clienteLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Cliente usuarioAutenticado =
                clienteRepository.findByEmail(clienteLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado.", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return ClienteMapper.of(usuarioAutenticado, token);
    }

    public void excluir(int id) {
        clienteRepository.deleteById(id);
    }

    public void atualizar(Cliente clienteAtualizdo, int id){
        if (!clienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Não encontrado");
        }
        clienteAtualizdo.setId(id);
        clienteRepository.save(clienteAtualizdo);
    }

    public boolean existePorId(int id) {
        return clienteRepository.existsById(id);
    }

    public Optional<Cliente> listarUmPorId(int id){
        return clienteRepository.findById(id);
    }

    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }
}