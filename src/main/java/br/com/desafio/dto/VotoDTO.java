package br.com.desafio.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {

    private Long id;

    @NotNull
    private Boolean votoValue;

    @Min(value = 1)
    private Long sessaoId;

    @Min(value = 1)
    private Long associadoId;


}
