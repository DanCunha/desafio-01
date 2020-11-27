package br.com.desafio.service;

import br.com.desafio.dto.ResponseSessaoDTO;
import br.com.desafio.model.Sessao;
import br.com.desafio.model.Voto;
import br.com.desafio.repository.PautaRepository;
import br.com.desafio.repository.SessaoRepository;
import br.com.desafio.repository.VotoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private PautaRepository pautaRepository;
    @Autowired
    private VotoRepository votoRepository;

    public Sessao save(Sessao sessao) throws NotFoundException {
        if(pautaRepository.findById(sessao.getPauta().getId()).isPresent())
            throw new NotFoundException("Pauta não encontrada");

        return sessaoRepository.save(sessao);
    }

    public List<Sessao> listAll(){
        return sessaoRepository.findAll();
    }

    public ResponseSessaoDTO findById(Long id) throws NotFoundException {
        Optional<Sessao> sessao = sessaoRepository.findById(id);

        if(sessao.isEmpty())
            throw new NotFoundException("Sessão não encontrada");

        List<Voto> votoList = votoRepository.findBySessaoId(id);

        int votoSim = (int) votoList.stream().filter(v -> v.getVoto() == true).count();
        int votoNao = (int) votoList.stream().filter(v -> v.getVoto() == false).count();

        return ResponseSessaoDTO.builder()
                        .sessaoId(id)
                        .descricaoPauta(sessao.get().getPauta().getDescricao())
                        .votoNao(votoNao)
                        .votoSim(votoSim)
                        .total(votoNao + votoSim).build();
    }

    public Voto votacao(Voto voto) throws TimeoutException, NotFoundException {
        validacaoVoto(voto);
        return votoRepository.save(voto);
    }

    private void validacaoVoto(Voto voto) throws TimeoutException, NotFoundException {
        Optional<Sessao> sessao = sessaoRepository.findById(voto.getSessao().getId());
        if(sessao.isPresent()){
            long minutesBetween = ChronoUnit.MINUTES.between(sessao.get().getDataHoraInicio(), LocalDateTime.now());
            if(minutesBetween > sessao.get().getTempoSessao())
                throw new TimeoutException("Sessão encerrada");
        }else{
            throw new NotFoundException("Sessão não encontrada");
        }

        if(votoRepository.findByAssociadoIdAndSessaoId(voto.getAssociado().getId(), voto.getSessao().getId()).isPresent()){
            throw new EntityExistsException("Associado já votou.");
        }
    }
}
