package br.com.desafio.dto;

import br.com.desafio.model.Associado;
import br.com.desafio.model.Sessao;
import lombok.*;

import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {

    private Long id;

    private Boolean voto;

    private Long sessaoId;

    private Long associadoId;


}
