package sptech.school.apizeporteiro.service.entrega.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EntregaListagemDto {
    private String tipoEntrega;
    private LocalDate dataRecebimentoPorteiro;
    private LocalDate dataRecebimentoMorador;
    private boolean recebido;
}
