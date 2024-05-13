package sptech.school.apizeporteiro.service.entrega;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.entrega.repository.EntregaRepository;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntregaService {
    private final EntregaRepository entregaRepository;

    public List<EntregaListagemDto> listarTodasEntregas() {
        return entregaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public EntregaListagemDto atualizarEntrega(Integer id, EntregaCriacaoDto entregaDto) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Entrega não encontrada."));


        entrega.setDataRecebimentoPorteiro(entregaDto.getDataRecebimentoPorteiro());
        return convertToDto(entregaRepository.save(entrega));
    }

    public void excluirEntrega(Integer id) {
        entregaRepository.deleteById(id);
    }

    public String registrarRetirada(Integer id) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Entrega não encontrada."));


        entrega.setDataRecebimentoMorador(LocalDate.now());
        entregaRepository.save(entrega);
        return "Entrega retirada com sucesso.";
    }

//    public List<EntregaListagemDto> listarEntregasPendentes() {
//        return entregaRepository.findByRecebidoFalse().stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }

    private EntregaListagemDto convertToDto(Entrega entrega) {
        EntregaListagemDto dto = new EntregaListagemDto();
        dto.setDataRecebimentoPorteiro(entrega.getDataRecebimentoPorteiro());
        dto.setDataRecebimentoMorador(entrega.getDataRecebimentoMorador());
        return dto;
    }
}
