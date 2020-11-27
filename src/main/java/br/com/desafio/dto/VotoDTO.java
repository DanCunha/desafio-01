package br.com.desafio.dto;

import br.com.desafio.model.Associado;
import br.com.desafio.model.Sessao;
import lombok.*;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {

    private Long id;

    @NotNull
    private Boolean voto;

    @Min(value = 1)
    private Long sessaoId;

    @Min(value = 1)
    private Long associadoId;


}
