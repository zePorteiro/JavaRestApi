package sptech.school.apizeporteiro.service.entrega.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EntregaCriacaoDto {
    @NotBlank(message = "O tipo de entrega é obrigatório")
    @Size(min = 2, max = 300, message = "O tipo de entrega deve ter entre 2 e 300 caracteres")
    private String tipoEntrega;
    @PastOrPresent(message = "A data de recebimento deve ser no passado ou presente")
    private LocalDate dataRecebimentoPorteiro;
    private LocalDate dataRecebimentoMorador;
    private Boolean recebido;
    @NotBlank(message = "O número do apartamento é obrigatório")
    private String numAp;
    private String porteiroNome;
}
