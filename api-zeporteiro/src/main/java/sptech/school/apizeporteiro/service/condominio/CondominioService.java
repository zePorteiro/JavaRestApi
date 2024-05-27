package sptech.school.apizeporteiro.service.condominio;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.cliente.repository.ClienteRepository;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.condominio.repository.CondominioRepository;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.mapper.CondominioMapper;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioCriacaoDto;
import sptech.school.apizeporteiro.service.condominio.dto.CondominioListagemDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.util.List;
import java.util.NoSuchElementException;

import static sptech.school.apizeporteiro.mapper.CondominioMapper.toDto;

@Service
@RequiredArgsConstructor
public class CondominioService {
    private final CondominioRepository condominioRepository;
    private final ClienteRepository clienteRepository;

    public void criar(CondominioCriacaoDto condominioCriacaoDto) {
        Condominio condominio = CondominioMapper.toEntity(condominioCriacaoDto);

        Cliente cliente = clienteRepository.findById(condominioCriacaoDto.getFkCliente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        condominio.setCliente(cliente);

        condominioRepository.save(condominio);
    }

    public void atualizar(CondominioCriacaoDto condominioCriacaoDto, int id) {
        Condominio condominioExistente = condominioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Condomínio não encontrado"));

        Condominio condominioAtualizado = CondominioMapper.toEntity(condominioCriacaoDto);
        condominioAtualizado.setId(id);
        condominioAtualizado.setCliente(condominioExistente.getCliente());

        condominioRepository.save(condominioAtualizado);
    }

    public void excluir(int id) {
        if (!condominioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Condomínio não encontrado");
        }
        condominioRepository.deleteById(id);
    }

    public CondominioListagemDto listarUmPorId(int id) {
        Condominio condominio = condominioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Condomínio não encontrado"));

        return CondominioMapper.toDto(condominio);
    }

    public List<CondominioListagemDto> listarTodos() {
        List<Condominio> condominios = condominioRepository.findAll();
        return CondominioMapper.toDto(condominios);
    }
}

