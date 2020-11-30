package br.com.desafio.dto;

import br.com.desafio.model.Associado;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoDTO {

    private Long id;

    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    @NotEmpty
    private String cpf;

    public Associado converter(){
        return new Associado(this.nome, this.cpf);
    }
}
