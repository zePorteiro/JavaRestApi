package sptech.school.apizeporteiro.service.apartamento.dto;

import lombok.Data;

@Data
public class ApartamentoListagemDto {
    private Integer id;
    private String bloco;
    private String numAp;
    private boolean vazio;
}
