package sptech.school.apizeporteiro.service.entrega;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.apartamento.repository.ApartamentoRepository;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.entrega.repository.EntregaRepository;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.mapper.EntregaMapper;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final ApartamentoRepository apartamentoRepository;

    public EntregaListagemDto cadastrarEntrega(EntregaCriacaoDto novaEntregaDto) {
        Entrega entrega = EntregaMapper.toEntity(novaEntregaDto);

        Entrega entregaSalva = entregaRepository.save(entrega);
        EntregaListagemDto listagemEntrega = EntregaMapper.toDto(entregaSalva);

        Integer apartamentoId = novaEntregaDto.getApartamentoId();
        Optional<Apartamento> optionalApartamento = apartamentoRepository.findById(apartamentoId);
        if (optionalApartamento.isPresent()) {
            Apartamento apartamento = optionalApartamento.get();
            Morador morador = apartamento.getMoradores().get(0);  // Supondo que o primeiro morador seja o principal
            String numeroWhatsApp = morador.getNumeroWhats1();
            LocalDate dataEntregaLocalDate = entregaSalva.getDataRecebimentoPorteiro();
            Date dataEntregaDate = Date.from(dataEntregaLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            enviarMensagemWhatsApp(numeroWhatsApp, dataEntregaDate);
        }

        return listagemEntrega;
    }

    private void enviarMensagemWhatsApp(String numeroTelefone, Date dataEntrega) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoMinutesLater = now.plusMinutes(2);
        int hora = twoMinutesLater.getHour();
        int minuto = twoMinutesLater.getMinute();

        String apiUrl = "http://localhost:8081/enviar-mensagem";
        String requestBody = String.format("{\"numero\": \"%s\", \"hora\": %d, \"minuto\": %d}",
                numeroTelefone, hora, minuto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(apiUrl, requestEntity, String.class);
    }

    public List<EntregaListagemDto> listarPendentes() {
        List<Entrega> entregas = entregaRepository.findByRecebidoFalse();
        return entregas.stream()
                .map(EntregaMapper::toDto)
                .collect(Collectors.toList());
    }

    public long totalEntregasUltimoMes() {
        LocalDate umMesAtras = LocalDate.now().minusMonths(1);
        return entregaRepository.countByDataRecebimentoMoradorAfter(umMesAtras);
    }

    public Optional<EntregaListagemDto> ultimaEntrega() {
        Optional<Entrega> entrega = entregaRepository.findTopByOrderByDataRecebimentoMoradorDesc();
        return entrega.map(EntregaMapper::toDto);
    }

    public EntregaListagemDto registrarEntregaRecebida(Integer entregaId) {
        Optional<Entrega> optionalEntrega = entregaRepository.findById(entregaId);
        if (optionalEntrega.isPresent()) {
            Entrega entrega = optionalEntrega.get();
            entrega.setRecebido(true);
            entrega.setDataRecebimentoMorador(LocalDate.now());
            entrega = entregaRepository.save(entrega);
            return EntregaMapper.toDto(entrega);
        } else {
            throw new RuntimeException("Entrega não encontrada"); // Ajuste isso conforme suas exceções
        }
    }
}
