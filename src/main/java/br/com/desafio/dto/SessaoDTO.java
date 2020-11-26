package br.com.desafio.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoDTO {

    private Long id;

    private Long pautaId;
}

