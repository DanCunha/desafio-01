package br.com.desafio.controller;

import br.com.desafio.dto.ResponseDTO;
import br.com.desafio.dto.SessaoDTO;
import br.com.desafio.dto.VotoDTO;
import br.com.desafio.mapper.SessaoMapper;
import br.com.desafio.mapper.VotoMapper;
import br.com.desafio.model.Sessao;
import br.com.desafio.model.Voto;
import br.com.desafio.repository.SessaoRepository;
import br.com.desafio.repository.VotoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

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
            Voto entity = votoRepository.save(voto);
            return ResponseEntity.created(null).body(new ResponseDTO(entity.getId(), "Voto registrado com sucesso."));
        }catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Sessão já existe"));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
        }
    }
}
