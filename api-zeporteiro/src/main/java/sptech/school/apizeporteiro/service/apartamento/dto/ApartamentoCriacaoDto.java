package sptech.school.apizeporteiro.service.apartamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApartamentoCriacaoDto {
    @NotBlank
    private String bloco;
    @NotBlank
    private String numAp;
    @NotNull
    private Boolean vazio;
    @NotNull
    private Integer condominioId;

    public Boolean getVazio(){
        return vazio;
    }

    public void setVazio(Boolean vazio){
        this.vazio = vazio;
    }
}
