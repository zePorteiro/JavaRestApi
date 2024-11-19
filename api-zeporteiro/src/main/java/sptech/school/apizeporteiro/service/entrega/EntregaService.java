package sptech.school.apizeporteiro.service.entrega;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.apartamento.repository.ApartamentoRepository;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.condominio.repository.CondominioRepository;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.entrega.repository.EntregaRepository;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;
import sptech.school.apizeporteiro.domain.porteiro.repository.PorteiroRepository;
import sptech.school.apizeporteiro.mapper.EntregaMapper;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final CondominioRepository condominioRepository;
    private final PorteiroRepository porteiroRepository;
    private final RestTemplate restTemplate;


    public EntregaListagemDto cadastrarEntrega(EntregaCriacaoDto novaEntregaDto) {
        Optional<Apartamento> optionalApartamento = apartamentoRepository.findByNumAp(novaEntregaDto.getNumAp());

        if (optionalApartamento.isEmpty()) {
            throw new EntityNotFoundException(
                    "Apartamento não encontrado com o número: " + novaEntregaDto.getNumAp());
        }

        Optional<Porteiro> optionalPorteiro = porteiroRepository.findById(novaEntregaDto.getIdPorteiro());

        if (optionalPorteiro.isEmpty()) {
            throw new EntityNotFoundException(
                    "Porteiro não encontrado com o ID: " + novaEntregaDto.getIdPorteiro());
        }

        Entrega entrega = EntregaMapper.toEntity(novaEntregaDto);

        entrega.setApartamento(optionalApartamento.get());
        entrega.setPorteiro(optionalPorteiro.get());

        Entrega entregaSalva = entregaRepository.save(entrega);

        return EntregaMapper.toDto(entregaSalva);
    }

    public List<Entrega> listarEntregas() {
        return entregaRepository.findAll();
    }

    public List<EntregaListagemDto> buscarEntregasPorApartamento(String numeroApartamento) {
        List<Entrega> entregas = entregaRepository.findByApartamentoNumAp(numeroApartamento);
        return EntregaMapper.toListDto(entregas);
    }

    public void enviarMensagemWhatsApp(String numeroTelefone, Date dataEntrega) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoMinutesLater = now.plusMinutes(2);
        int hora = twoMinutesLater.getHour();
        int minuto = twoMinutesLater.getMinute();

        String apiUrl = "http://192.168.0.19:8081/enviar-mensagem";
        String requestBody = String.format("{\"numero\": \"%s\", \"hora\": %d, \"minuto\": %d}",
                numeroTelefone, hora, minuto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            restTemplate.postForEntity(apiUrl, requestEntity, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Falha ao enviar mensagem: " + e.getMessage(), e);
        }
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
            throw new RuntimeException("Entrega não encontrada");
        }
    }

    public void gerarCsvDeEntregasPorCondominio(HttpServletResponse response, Integer condominioId) throws IOException, IOException {
        Condominio condominio = condominioRepository.findById(condominioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Condomínio não encontrado"));

        List<Apartamento> apartamentos = condominio.getApartamentos();
        List<Entrega> entregas = apartamentos.stream()
                .flatMap(apartamento -> apartamento.getEntregas().stream())
                .collect(Collectors.toList());

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=entregas.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID, Tipo Entrega, Data Recebimento Porteiro, Data Recebimento Morador, Apartamento");

        for (Entrega entrega : entregas) {
            writer.println(String.format("%d, %s, %s, %s, %d",
                    entrega.getId(),
                    entrega.getTipoEntrega(),
                    entrega.getDataRecebimentoPorteiro(),
                    entrega.getDataRecebimentoMorador() != null ? entrega.getDataRecebimentoMorador() : "",
                    entrega.getApartamento().getId()));
        }

        writer.flush();
        writer.close();
    }
}
