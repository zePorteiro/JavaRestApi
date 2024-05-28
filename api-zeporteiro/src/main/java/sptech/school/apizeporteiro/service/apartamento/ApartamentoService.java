package sptech.school.apizeporteiro.service.apartamento;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.apartamento.repository.ApartamentoRepository;
import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.cliente.repository.ClienteRepository;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.condominio.repository.CondominioRepository;
import sptech.school.apizeporteiro.mapper.ApartamentoMapper;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoCriacaoDto;
import sptech.school.apizeporteiro.service.apartamento.dto.ApartamentoListagemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApartamentoService {

    private final ApartamentoRepository apartamentoRepository;
    private final ClienteRepository clienteRepository;
    private final CondominioRepository condominioRepository;

    public List<ApartamentoListagemDto> salvarApartamentos(List<ApartamentoCriacaoDto> apartamentosDTO) {
        List<Apartamento> apartamentos = apartamentosDTO.stream()
                .map(ApartamentoMapper::toEntity)
                .collect(Collectors.toList());

        List<Apartamento> apartamentosSalvos = apartamentoRepository.saveAll(apartamentos);
        return ApartamentoMapper.toDtoList(apartamentosSalvos);
    }

    public void cadastrarApartamento(ApartamentoCriacaoDto apartamentoCriacaoDto, Integer condominioId) {
        // Obtenha o cliente autenticado
        String emailClienteAutenticado = getClienteAutenticadoEmail();
        Cliente cliente = clienteRepository.findByEmail(emailClienteAutenticado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cliente não autorizado"));

        // Verifique se o condomínio pertence ao cliente autenticado
        Condominio condominio = condominioRepository.findById(condominioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Condomínio não encontrado"));

        if (!condominio.getCliente().getId().equals(cliente.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cliente não autorizado a criar apartamentos neste condomínio");
        }

        // Converte o DTO para a entidade e associa o condomínio
        Apartamento apartamento = ApartamentoMapper.toEntity(apartamentoCriacaoDto);
        apartamento.setCondominio(condominio);

        // Salva o apartamento no repositório
        apartamentoRepository.save(apartamento);
    }

    public ApartamentoListagemDto atualizarVazio(Integer id, boolean vazio) {
        Optional<Apartamento> optionalApartamento = apartamentoRepository.findById(id);
        if (optionalApartamento.isPresent()) {
            Apartamento apartamento = optionalApartamento.get();
            apartamento.setVazio(vazio);
            Apartamento apartamentoAtualizado = apartamentoRepository.save(apartamento);
            return ApartamentoMapper.toDto(apartamentoAtualizado);
        } else {
            throw new RuntimeException("Apartamento não encontrado"); // Ajuste isso conforme suas exceções
        }
    }

    public void excluirApartamento(Integer id) {
        if (apartamentoRepository.existsById(id)) {
            apartamentoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Apartamento não encontrado"); // Ajuste isso conforme suas exceções
        }
    }

    public List<ApartamentoListagemDto.MoradorDto> listarMoradoresPorApartamento(Integer id) {
        Optional<Apartamento> optionalApartamento = apartamentoRepository.findById(id);
        if (optionalApartamento.isPresent()) {
            Apartamento apartamento = optionalApartamento.get();
            return apartamento.getMoradores().stream()
                    .map(ApartamentoMapper::toDtoMorador)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Apartamento não encontrado");
        }
    }

    public List<ApartamentoListagemDto> listarTodosApartamentos() {
        List<Apartamento> apartamentos = apartamentoRepository.findAll();
        return ApartamentoMapper.toDtoList(apartamentos);
    }

    private String getClienteAutenticadoEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
