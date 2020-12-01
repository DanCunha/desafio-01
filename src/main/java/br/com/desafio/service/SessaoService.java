package br.com.desafio.service;

import br.com.desafio.dto.ResponseClientDTO;
import br.com.desafio.dto.ResponseSessaoDTO;
import br.com.desafio.model.Associado;
import br.com.desafio.model.Sessao;
import br.com.desafio.model.Voto;
import br.com.desafio.repository.AssociadoRepository;
import br.com.desafio.repository.PautaRepository;
import br.com.desafio.repository.SessaoRepository;
import br.com.desafio.repository.VotoRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
    @Autowired
    private AssociadoRepository associadoRepository;

    private Logger logger = LoggerFactory.getLogger(SessaoService.class);

    public Sessao save(Sessao sessao) throws NotFoundException {
        if(pautaRepository.findById(sessao.getPauta().getId()).isEmpty()){
            logger.error("Error Exception: Pauta não encontrada");
            throw new NotFoundException("Pauta não encontrada");
        }

        return sessaoRepository.save(sessao);
    }

    public List<Sessao> listAll(){
        return sessaoRepository.findAll();
    }

    public ResponseSessaoDTO findById(Long id) throws NotFoundException {
        Optional<Sessao> sessao = sessaoRepository.findById(id);

        if(sessao.isEmpty()){
            logger.error("Error Exception: Sessão não encontrada");
            throw new NotFoundException("Sessão não encontrada");
        }

        List<Voto> votoList = votoRepository.findBySessaoId(id);

        int votoSim = (int) votoList.stream().filter(v -> v.getVotoValue().equals(true)).count();
        int votoNao = (int) votoList.stream().filter(v -> v.getVotoValue().equals(false)).count();

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
        Optional<Associado> associado = associadoRepository.findById(voto.getAssociado().getId());
        if(associado.isEmpty()){
            logger.error("Error Exception: Associado não encontrado");
            throw new NotFoundException("Associado não encontrado");
        }

        if(sessao.isPresent()){
            long minutesBetween = ChronoUnit.MINUTES.between(sessao.get().getDataHoraInicio(), LocalDateTime.now());
            if(minutesBetween > sessao.get().getTempoSessao()){
                logger.error("Error Exception: Serrão encerrada");
                throw new TimeoutException("Sessão encerrada");
            }

        }else{
            logger.error("Error Exception: Sessão não encontrada");
            throw new NotFoundException("Sessão não encontrada");
        }

        if(votoRepository.findByAssociadoIdAndSessaoId(voto.getAssociado().getId(), voto.getSessao().getId()).isPresent()){
            logger.error("Error Exception: Associado já votou");
            throw new EntityExistsException("Associado já votou.");
        }

        if(cpfClient(associado.get().getCpf())){
            logger.error("Error Exception: Não habilitado para votar");
            throw new NotFoundException("Não habilitado para votar");
        }
    }

    public boolean cpfClient(String cpf) {
        Mono<ResponseClientDTO> message = WebClient.create("https://user-info.herokuapp.com").get().uri(uriBuilder -> uriBuilder.path("/users/"+cpf).build()).retrieve()
                .bodyToMono(ResponseClientDTO.class);
        ResponseClientDTO result = message.block();

        return result.getStatus().equals("UNABLE_TO_VOTE");
    }
}
