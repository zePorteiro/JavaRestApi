package sptech.school.apizeporteiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.service.entrega.EntregaService;
import sptech.school.apizeporteiro.service.entrega.dto.EntregaListagemDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @GetMapping("/pendentes")
    public ResponseEntity<List<EntregaListagemDto>> listarPendentes() {
        return ResponseEntity.ok(entregaService.listarPendentes());
    }

    @GetMapping("/total-ultimo-mes")
    public ResponseEntity<Long> totalEntregasUltimoMes() {
        return ResponseEntity.ok(entregaService.totalEntregasUltimoMes());
    }

    @GetMapping("/ultima")
    public ResponseEntity<Optional<EntregaListagemDto>> ultimaEntrega() {
        return ResponseEntity.ok(entregaService.ultimaEntrega());
    }

    @PutMapping("/registrar-recebida/{entregaId}")
    public ResponseEntity<EntregaListagemDto> registrarEntregaRecebida(@PathVariable Integer entregaId) {
        return ResponseEntity.ok(entregaService.registrarEntregaRecebida(entregaId));
    }
}
