package sptech.school.apizeporteiro.service.cliente.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClienteListagemDto {
    private Integer id;
    private String email;
    private String nome;
    private String cnpj;
    private String representante;

    private List<CondominioDto> condominios;

    @Data
    public static class CondominioDto {
        private Integer id;
        private String nome;
        private String cep;
        private String logradouro;
        private String numero;
        private String bairro;
        private String cidade;

        private List<ApartamentoDto> apartamentos;

        @Data
        public static class ApartamentoDto {
            private Integer id;
            private String bloco;
            private String numAp;
            private Boolean vazio;
            private List<MoradorDto> moradores;

            @Data
            public static class MoradorDto {
                private Integer id;
                private String nome;
                private String numeroWhats1;
                private String numeroWhats2;
                private String numeroWhats3;
            }
        }
    }
}
