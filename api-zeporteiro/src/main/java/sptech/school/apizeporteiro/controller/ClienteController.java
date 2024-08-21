package sptech.school.apizeporteiro.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.apizeporteiro.domain.cliente.Cliente;
import sptech.school.apizeporteiro.domain.cliente.repository.ClienteRepository;
import sptech.school.apizeporteiro.mapper.ClienteMapper;
import sptech.school.apizeporteiro.service.cliente.ClienteService;
import sptech.school.apizeporteiro.service.cliente.autenticacao.dto.ClienteLoginDto;
import sptech.school.apizeporteiro.service.cliente.autenticacao.dto.ClienteTokenDto;
import sptech.school.apizeporteiro.service.cliente.dto.ClienteCriacaoDto;
import sptech.school.apizeporteiro.service.cliente.dto.ClienteListagemDto;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {
    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody @Valid ClienteCriacaoDto clienteCriacaoDto){
        this.clienteService.criar(clienteCriacaoDto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<ClienteTokenDto> login(@RequestBody ClienteLoginDto clienteLoginDto){
        ClienteTokenDto clienteTokenDto = this.clienteService.autenticar(clienteLoginDto);
        return ResponseEntity.status(200).body(clienteTokenDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteListagemDto> buscaPorId(@PathVariable int id) {
        Optional<Cliente> clienteOpt = clienteService.listarUmPorId(id);
        if (clienteOpt.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        ClienteListagemDto dto = ClienteMapper.toDto(clienteOpt.get());
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<ClienteListagemDto>> listar() {
        List<Cliente> clientes = clienteService.listarTodos() ;
        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<ClienteListagemDto> listaAuxiliar = ClienteMapper.toDto(clientes);
        return ResponseEntity.status(200).body(listaAuxiliar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir (@PathVariable @Valid int id){
        if (clienteRepository.existsById(id)){
            clienteService.excluir(id);
            return ok().build();
        } else {
            return notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") @Valid int id,
                                          @RequestBody @Valid Cliente clienteAlterado) {
        clienteService.atualizar(clienteAlterado, id);
        return ResponseEntity.noContent().build();
    }
}
