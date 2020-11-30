package br.com.desafio.mapper;

import br.com.desafio.dto.VotoDTO;
import br.com.desafio.model.Associado;
import br.com.desafio.model.Sessao;
import br.com.desafio.model.Voto;

public class VotoMapper {

    private VotoMapper() {
    }

    public static Voto convertToEntity(VotoDTO dto) {
        return Voto.builder()
                .associado(Associado.builder().id(dto.getAssociadoId()).build())
                .sessao(Sessao.builder().id(dto.getSessaoId()).build())
                .votoValue(dto.getVotoValue()).build();
    }
}
