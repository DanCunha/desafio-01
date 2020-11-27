package br.com.desafio.controller;

import br.com.desafio.dto.ResponseDTO;
import br.com.desafio.dto.ResponseSessaoDTO;
import br.com.desafio.dto.SessaoDTO;
import br.com.desafio.dto.VotoDTO;
import br.com.desafio.mapper.SessaoMapper;
import br.com.desafio.mapper.VotoMapper;
import br.com.desafio.model.Associado;
import br.com.desafio.model.Sessao;
import br.com.desafio.model.Voto;
import br.com.desafio.repository.SessaoRepository;
import br.com.desafio.repository.VotoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(value = "/sessao")
@Api(value = "Sessão")
@CrossOrigin(origins = "*")
public class SessaoController {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private VotoRepository votoRepository;

    @PostMapping
    @ApiOperation(value = "Criar nova Sessão", response = Sessao.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<ResponseDTO> add(@Valid @RequestBody SessaoDTO dto) {
        try {
            Sessao sessao = SessaoMapper.convertToEntity(dto);
            Sessao entity = sessaoRepository.save(sessao);
            return ResponseEntity.created(null).body(new ResponseDTO(entity.getId(), "Sessão criada com sucesso."));
        }catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Sessão já existe"));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
        }
    }

    @PostMapping("/votacao")
    @ApiOperation(value = "Votar", response = Voto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<ResponseDTO> votacao(@Valid @RequestBody VotoDTO dto) {
        try {
            Voto voto = VotoMapper.convertToEntity(dto);
            validacaoVoto(voto);

            Voto entity = votoRepository.save(voto);
            return ResponseEntity.created(null).body(new ResponseDTO(entity.getId(), "Voto registrado com sucesso."));
        }catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Sessão já existe"));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
        }
    }

    @ApiOperation(value="Retorna lista de sessões")
    @GetMapping
    public List<Sessao> listAll(){
        return sessaoRepository.findAll();
    }

    @ApiOperation(value="Retorna sessão por id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSessaoDTO> findById(@PathVariable(value="id") long id){
        Optional<Sessao> sessao = sessaoRepository.findById(id);
        List<Voto> votoList = votoRepository.findBySessaoId(id);

        int votoSim = (int) votoList.stream().filter(v -> v.getVoto() == true).count();
        int votoNao = (int) votoList.stream().filter(v -> v.getVoto() == false).count();

        return ResponseEntity.ok().body(
                ResponseSessaoDTO.builder()
                .sessaoId(id)
                .descricaoPauta(sessao.get().getPauta().getDescricao())
                .votoNao(votoNao)
                .votoSim(votoSim)
                .total(votoNao + votoSim).build());
    }

    private void validacaoVoto(Voto voto) throws TimeoutException, NotFoundException {
        if(votoRepository.findByAssociadoIdAndSessaoId(voto.getAssociado().getId(), voto.getSessao().getId()).isPresent()){
            throw new EntityExistsException("Associado já votou.");
        }
        Optional<Sessao> sessao = sessaoRepository.findById(voto.getSessao().getId());
        if(sessao.isPresent()){
            long minutesBetween = ChronoUnit.MINUTES.between(sessao.get().getDataHoraInicio(), LocalDateTime.now());
            if(minutesBetween > sessao.get().getTempoSessao())
                throw new TimeoutException("Sessão encerrada");
        }else{
            throw new NotFoundException("Sessão não encontrada");
        }
    }
}
