package br.com.desafio.dto;

import br.com.desafio.model.Pauta;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDTO {

    private Long id;

    private String descricao;

    public PautaDTO(Pauta pauta) {
        this.id = pauta.getId();
        this.descricao = pauta.getDescricao();
    }

    public Pauta converter(){
        return new Pauta(this.descricao);
    }
}
