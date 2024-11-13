package sptech.school.apizeporteiro.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.domain.entrega.Entrega;
import sptech.school.apizeporteiro.service.entrega.EntregaService;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entregas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class EntregaController {

    private final EntregaService entregaService;

    @PostMapping
    public ResponseEntity<EntregaListagemDto> cadastrarEntrega(@RequestBody EntregaCriacaoDto novaEntregaDto) {
        EntregaListagemDto entrega = entregaService.cadastrarEntrega(novaEntregaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entrega);
    }

    @GetMapping
    public ResponseEntity<List<Entrega>> listarEntregas() {
        List<Entrega> entregas = entregaService.listarEntregas();
        return ResponseEntity.ok(entregas);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<EntregaListagemDto>> listarPendentes() {
        List<EntregaListagemDto> pendentes = entregaService.listarPendentes();
        return ResponseEntity.ok(pendentes);
    }

    @GetMapping("/total-ultimo-mes")
    public ResponseEntity<Long> totalEntregasUltimoMes() {
        long total = entregaService.totalEntregasUltimoMes();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/ultima")
    public ResponseEntity<Optional<EntregaListagemDto>> ultimaEntrega() {
        Optional<EntregaListagemDto> ultimaEntrega = entregaService.ultimaEntrega();
        return ResponseEntity.ok(ultimaEntrega);
    }

    @PutMapping("/{entregaId}/recebida")
    public ResponseEntity<EntregaListagemDto> registrarEntregaRecebida(@PathVariable Integer entregaId) {
        EntregaListagemDto entregaListagemDto = entregaService.registrarEntregaRecebida(entregaId);
        return ResponseEntity.ok(entregaListagemDto);
    }

    @GetMapping("/condominio/{condominioId}/csv")
    public void gerarCsvDeEntregasPorCondominio(HttpServletResponse response, @PathVariable Integer condominioId) throws IOException, IOException {
        entregaService.gerarCsvDeEntregasPorCondominio(response, condominioId);
    }
}