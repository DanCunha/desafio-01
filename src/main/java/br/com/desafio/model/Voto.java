package br.com.desafio.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean votoValue;

    @ManyToOne
    private Sessao sessao;

    @ManyToOne
    private Associado associado;
}
