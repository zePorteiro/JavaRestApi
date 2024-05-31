package sptech.school.apizeporteiro.service.entrega;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.apizeporteiro.domain.apartamento.Apartamento;
import sptech.school.apizeporteiro.domain.apartamento.repository.ApartamentoRepository;
import sptech.school.apizeporteiro.domain.condominio.Condominio;
import sptech.school.apizeporteiro.domain.condominio.repository.CondominioRepository;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.entrega.repository.EntregaRepository;
import sptech.school.apizeporteiro.domain.morador.Morador;
import sptech.school.apizeporteiro.mapper.EntregaMapper;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.LinkedList;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final CondominioRepository condominioRepository;

    public EntregaListagemDto cadastrarEntrega(EntregaCriacaoDto novaEntregaDto) {
        Entrega entrega = EntregaMapper.toEntity(novaEntregaDto);

        Entrega entregaSalva = entregaRepository.save(entrega);
        EntregaListagemDto listagemEntrega = EntregaMapper.toDto(entregaSalva);

        Integer fkApartamento = novaEntregaDto.getFkApartamento();
        Optional<Apartamento> optionalApartamento = apartamentoRepository.findById(fkApartamento);
        if (optionalApartamento.isPresent()) {
            Apartamento apartamento = optionalApartamento.get();
            List<Morador> moradores = apartamento.getMoradores();

            if (!moradores.isEmpty()) {
                Morador morador = moradores.get(0);

                // Criando a fila de números de WhatsApp
                Queue<String> numerosWhatsApp = new LinkedList<>();
                if (morador.getNumeroWhats1() != null) numerosWhatsApp.add(morador.getNumeroWhats1());
                if (morador.getNumeroWhats2() != null) numerosWhatsApp.add(morador.getNumeroWhats2());
                if (morador.getNumeroWhats3() != null) numerosWhatsApp.add(morador.getNumeroWhats3());

                LocalDate dataEntregaLocalDate = entregaSalva.getDataRecebimentoPorteiro();
                Date dataEntregaDate = Date.from(dataEntregaLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                // Processando a fila de números de WhatsApp
                while (!numerosWhatsApp.isEmpty()) {
                    String numeroWhatsApp = numerosWhatsApp.poll();
                    enviarMensagemWhatsApp(numeroWhatsApp, dataEntregaDate);
                }
            }
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
