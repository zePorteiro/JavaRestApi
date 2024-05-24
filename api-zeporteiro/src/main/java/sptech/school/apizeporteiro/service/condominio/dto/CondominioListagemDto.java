package sptech.school.apizeporteiro.service.condominio.dto;

import lombok.Data;

import java.util.List;

@Data
public class CondominioListagemDto {
    private Integer id;
    private String nome;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;

    private ClienteDto cliente;
    private List<ApartamentoDto> apartamentos;

    @Data
    public static class ClienteDto {
        private Integer id;
        private String email;
        private String nome;
        private String senha;
        private String cnpj;
        private String representante;
    }

    @Data
    public static class ApartamentoDto {
        private Integer id;
        private String bloco;
        private String numAp;
        private Boolean vazio;
    }
}
