package sptech.school.apizeporteiro.service.entrega.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EntregaCriacaoDto {
    @NotBlank
    @Size(max = 300)
    @Size(min = 2)
    private String tipoEntrega;
    @PastOrPresent(message = "A data de recebimento do morador deve ser no passado ou no presente")
    private LocalDate dataRecebimentoPorteiro;
    private LocalDate dataRecebimentoMorador;
    private Boolean recebido;
    private String numAp;
    private Integer idPorteiro;
}

