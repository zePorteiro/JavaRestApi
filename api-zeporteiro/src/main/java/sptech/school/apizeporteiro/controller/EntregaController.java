package sptech.school.apizeporteiro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.domain.entrega.repository.EntregaRepository;
import sptech.school.apizeporteiro.mapper.EntregaMapper;
import sptech.school.apizeporteiro.service.entrega.EntregaService;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/entregas")
public class EntregaController {
    private final EntregaRepository entregaRepository;
    private final EntregaService entregaService;

    @PostMapping
    public ResponseEntity<EntregaListagemDto> cadastrarEntrega
            (@RequestBody @Valid EntregaCriacaoDto novaEntregaDto) {
        Entrega entrega = EntregaMapper.toEntity(novaEntregaDto);

        Entrega entregaSalva = entregaRepository.save(entrega);

        EntregaListagemDto listagemEntrega = EntregaMapper.toDto(entregaSalva);
        String tel = "+5511989208086";
        LocalDate dataEntregaLocalDate = entregaSalva.getDataRecebimentoPorteiro();

        // Converte a LocalDate para Date
        Date dataEntregaDate = Date.from(dataEntregaLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        enviarMensagemWhatsApp(tel, dataEntregaDate);

        return ResponseEntity.status(201).body(listagemEntrega);
    }

    private void enviarMensagemWhatsApp(String numeroTelefone, Date dataEntrega) {
        // Obtém a hora e o minuto da data de entrega
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoMinutesLater = now.plusMinutes(2);
        int hora = twoMinutesLater.getHour();
        int minuto = twoMinutesLater.getMinute();

        // Monta a URL da API Python
        String apiUrl = "http://localhost:8081/enviar-mensagem";

        // Monta o corpo da requisição
        String requestBody =
                String.format("{\"numero\": \"%s\", \"hora\": %d, \"minuto\": %d}",
                        numeroTelefone, hora, minuto);

        // Configura o cabeçalho da solicitação
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Faz uma requisição POST para a API Python
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(apiUrl, requestEntity, String.class);
    }

    @GetMapping
    public ResponseEntity<List<EntregaListagemDto>> listarTodasEntregas() {
        List<EntregaListagemDto> entregas = entregaService.listarTodasEntregas();
        return entregas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(entregas);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntregaListagemDto> atualizarEntrega(
            @PathVariable Integer id,
            @Valid @RequestBody EntregaCriacaoDto entregaDto
    ) {
        EntregaListagemDto entregaAtualizada = entregaService.atualizarEntrega(id, entregaDto);
        return ResponseEntity.ok(entregaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEntrega(@PathVariable Integer id) {
        entregaService.excluirEntrega(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/retirar")
    public ResponseEntity<String> registrarRetirada(@PathVariable Integer id) {
        String mensagem = entregaService.registrarRetirada(id);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<EntregaListagemDto>> listarEntregasPendentes() {
        List<EntregaListagemDto> pendentes = entregaService.listarEntregasPendentes();
        return pendentes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pendentes);
    }
}
