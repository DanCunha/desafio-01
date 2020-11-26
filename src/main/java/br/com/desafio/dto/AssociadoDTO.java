package br.com.desafio.dto;

import br.com.desafio.model.Associado;
import br.com.desafio.model.Pauta;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoDTO {

    private Long id;

    private String nome;

    private String cpf;

    public Associado converter(){
        return new Associado(this.nome, this.cpf);
    }
}
