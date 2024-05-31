package sptech.school.apizeporteiro.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.service.entrega.EntregaService;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaCriacaoDto;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entregas")
@RequiredArgsConstructor
public class EntregaController {

    private final EntregaService entregaService;

    @PostMapping
    public ResponseEntity<EntregaListagemDto> cadastrarEntrega(@RequestBody EntregaCriacaoDto novaEntregaDto) {
        EntregaListagemDto entregaListagemDto = entregaService.cadastrarEntrega(novaEntregaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entregaListagemDto);
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