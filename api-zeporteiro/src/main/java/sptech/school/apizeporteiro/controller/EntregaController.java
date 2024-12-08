package sptech.school.apizeporteiro.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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

    @GetMapping("/condominio/{condominioId}")
    public ResponseEntity<List<EntregaListagemDto>> buscarEntregasPorCondominio(@PathVariable Integer condominioId) {
        List<EntregaListagemDto> entregas = entregaService.buscarEntregasPorCondominio(condominioId);
        return ResponseEntity.ok(entregas);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<EntregaListagemDto> atualizarEntrega(@PathVariable Integer Id, @Valid @RequestBody EntregaCriacaoDto entregaDto) {
        EntregaListagemDto entrega = entregaService.atualizarEntrega(Id, entregaDto);
        return ResponseEntity.ok(entrega);
    }

    @PostMapping
    public ResponseEntity<EntregaListagemDto> cadastrarEntrega(@Valid @RequestBody EntregaCriacaoDto novaEntregaDto) {
        EntregaListagemDto entrega = entregaService.cadastrarEntrega(novaEntregaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entrega);
    }

    @GetMapping("/apartamento/{numeroApartamento}")
    public ResponseEntity<List<EntregaListagemDto>> buscarEntregasPorApartamento(@PathVariable String numeroApartamento) {
        List<EntregaListagemDto> entregas = entregaService.buscarEntregasPorApartamento(numeroApartamento);
        return ResponseEntity.ok(entregas);
    }

    @GetMapping
    public ResponseEntity<List<EntregaListagemDto>> listarEntregas() {
        List<EntregaListagemDto> entregas = entregaService.listarEntregas();
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEntrega(@PathVariable Integer id) {
        entregaService.excluirEntrega(id);
        return ResponseEntity.noContent().build();
    }
}