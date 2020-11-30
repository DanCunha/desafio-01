package br.com.desafio.mapper;

import br.com.desafio.dto.SessaoDTO;
import br.com.desafio.model.Pauta;
import br.com.desafio.model.Sessao;

import java.time.LocalDateTime;

public class SessaoMapper {

    private SessaoMapper() {
    }

    public static Sessao convertToEntity(SessaoDTO dto) {
        return Sessao.builder()
                .pauta(Pauta.builder().id(dto.getPautaId()).build())
                .dataHoraInicio(LocalDateTime.now())
                .tempoSessao(dto.getTempoSessao() > 0 ? dto.getTempoSessao() : 1)
                .build();
    }
}
