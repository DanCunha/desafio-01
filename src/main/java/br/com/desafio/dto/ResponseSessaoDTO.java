package br.com.desafio.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSessaoDTO {

    private Long sessaoId;

    private String descricaoPauta;

    private int votoSim;

    private int votoNao;

    private int total;

    private String message;

    public ResponseSessaoDTO(String message) {
        this.message = message;
    }
}
