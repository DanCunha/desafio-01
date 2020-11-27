package br.com.desafio.dto;

import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoDTO {

    private Long id;

    @Min(value = 1)
    private Long pautaId;

    private int tempoSessao;
}

