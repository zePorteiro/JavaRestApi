package sptech.school.apizeporteiro.service.porteiro;

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
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.domain.porteiro.repository.PorteiroRepository;
import sptech.school.apizeporteiro.mapper.ClienteMapper;
import sptech.school.apizeporteiro.mapper.CondominioMapper;
import sptech.school.apizeporteiro.mapper.PorteiroMapper;
import sptech.school.apizeporteiro.service.cliente.autenticacao.dto.ClienteLoginDto;
import sptech.school.apizeporteiro.service.cliente.autenticacao.dto.ClienteTokenDto;
import sptech.school.apizeporteiro.service.cliente.dto.ClienteCriacaoDto;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioCriacaoDto;
import sptech.school.apizeporteiro.service.porteiro.autenticacao.dto.PorteiroLoginDto;
import sptech.school.apizeporteiro.service.porteiro.autenticacao.dto.PorteiroTokenDto;
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroCriacaoDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PorteiroService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final PorteiroRepository porteiroRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    @Autowired
    private AuthenticationManager authenticationManager;

    public void criar(PorteiroCriacaoDto porteiroCriacaoDto){
        final Porteiro novoPorteiro = PorteiroMapper.of(porteiroCriacaoDto);
        String senhaCriptografada = passwordEncoder.encode(novoPorteiro.getSenha());
        novoPorteiro.setSenha(senhaCriptografada);

        this.porteiroRepository.save(novoPorteiro);
    }

    public PorteiroTokenDto autenticar(PorteiroLoginDto loginDto){

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                loginDto.getRg(), loginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Porteiro usuarioAutenticado =
                porteiroRepository.findByRg(loginDto.getRg())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Rg ou usuário não cadastrado.", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return PorteiroMapper.of(usuarioAutenticado, token);
    }

    public void excluir(int id) {
        porteiroRepository.deleteById(id);
    }

    public void atualizar(Porteiro porteiroAtualizado, int id){
        if (!porteiroRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Não encontrado");
        }
        porteiroAtualizado.setId(id);
        porteiroRepository.save(porteiroAtualizado);
    }

    public boolean existePorId(int id) {
        return porteiroRepository.existsById(id);
    }

    public Optional<Porteiro> listarUmPorId(int id){
        return porteiroRepository.findById(id);
    }

    public List<Porteiro> listarTodos(){
        return porteiroRepository.findAll();
    }
}
