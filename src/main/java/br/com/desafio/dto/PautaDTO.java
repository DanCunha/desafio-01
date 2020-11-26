package br.com.desafio.dto;

import br.com.desafio.model.Pauta;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDTO {

    private Long id;

    @NotNull
    private String descricao;

    public Pauta converter(){
        return new Pauta(this.descricao);
    }
}
