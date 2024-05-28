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
import sptech.school.apizeporteiro.domain.condominio.repository.CondominioRepository;
import sptech.school.apizeporteiro.domain.entrega.repository.EntregaRepository;
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
import sptech.school.apizeporteiro.service.porteiro.dto.PorteiroListagemDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PorteiroService {
    private final PorteiroRepository porteiroRepository;
    private final CondominioRepository condominioRepository;
    private final EntregaRepository entregaRepository;

    public PorteiroListagemDto criar(PorteiroCriacaoDto porteiroCriacaoDto) {
        Condominio condominio = condominioRepository.findById(porteiroCriacaoDto.getCondominioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Condomínio não encontrado"));

        Porteiro porteiro = PorteiroMapper.toEntity(porteiroCriacaoDto);
        porteiro.setCondominio(condominio);

        porteiroRepository.save(porteiro);

        return PorteiroMapper.toDto(porteiro);
    }

    public List<PorteiroListagemDto> listarPorCondominio(Integer condominioId) {
        List<Porteiro> porteiros = porteiroRepository.findByCondominioId(condominioId);
        return porteiros.stream()
                .map(PorteiroMapper::toDto)
                .collect(Collectors.toList());
    }

    public PorteiroListagemDto atualizar(Integer id, PorteiroCriacaoDto porteiroCriacaoDto) {
        Porteiro porteiro = porteiroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Porteiro não encontrado"));

        Condominio condominio = condominioRepository.findById(porteiroCriacaoDto.getCondominioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Condomínio não encontrado"));

        porteiro.setNome(porteiroCriacaoDto.getNome());
        porteiro.setRg(porteiroCriacaoDto.getRg());
        porteiro.setSenha(porteiroCriacaoDto.getSenha());
        porteiro.setCondominio(condominio);

        porteiroRepository.save(porteiro);

        return PorteiroMapper.toDto(porteiro);
    }

    public void deletar(Integer id) {
        if (!porteiroRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Porteiro não encontrado");
        }
        porteiroRepository.deleteById(id);
    }

    public List<PorteiroListagemDto.EntregaDto> listarEntregasPorPorteiro(Integer porteiroId) {
        Porteiro porteiro = porteiroRepository.findById(porteiroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Porteiro não encontrado"));

        return PorteiroMapper.toEntregaDto(porteiro.getEntregas());
    }
}

