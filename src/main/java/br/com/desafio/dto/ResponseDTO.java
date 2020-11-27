package br.com.desafio.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private Long id;

    private String mensagem;

    public ResponseDTO(String mensagem) {
        this.mensagem = mensagem;
    }
}
