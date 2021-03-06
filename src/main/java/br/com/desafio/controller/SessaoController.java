package br.com.desafio.controller;

import br.com.desafio.dto.ResponseDTO;
import br.com.desafio.dto.ResponseSessaoDTO;
import br.com.desafio.dto.SessaoDTO;
import br.com.desafio.dto.VotoDTO;
import br.com.desafio.mapper.SessaoMapper;
import br.com.desafio.mapper.VotoMapper;
import br.com.desafio.model.Sessao;
import br.com.desafio.model.Voto;
import br.com.desafio.repository.VotoRepository;
import br.com.desafio.service.SessaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/sessao")
@Api(value = "Sessão")
@CrossOrigin(origins = "*")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private VotoRepository votoRepository;

    @PostMapping
    @ApiOperation(value = "Criar nova Sessão", response = Sessao.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<ResponseDTO> add(@Valid @RequestBody SessaoDTO dto, UriComponentsBuilder uriBuilder) {
        try {
            Sessao sessao = SessaoMapper.convertToEntity(dto);
            Sessao entity = sessaoService.save(sessao);
            URI uri = uriBuilder.path("/sessao/{id}").buildAndExpand(entity.getId()).toUri();
            return ResponseEntity.created(uri).body(new ResponseDTO(entity.getId(), "Sessão criada com sucesso."));
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
    public ResponseEntity<ResponseDTO> votacao(@Valid @RequestBody VotoDTO dto, UriComponentsBuilder uriBuilder) {
        try {
            Voto entity = sessaoService.votacao(VotoMapper.convertToEntity(dto));
            URI uri = uriBuilder.path("/sessao/{id}").buildAndExpand(entity.getId()).toUri();
            return ResponseEntity.created(uri).body(new ResponseDTO(entity.getId(), "Voto registrado com sucesso."));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
        }
    }

    @ApiOperation(value="Retorna lista de sessões")
    @GetMapping
    public List<Sessao> listAll(){
        return sessaoService.listAll();
    }

    @ApiOperation(value="Retorna resultados da Sessão por id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSessaoDTO> findById(@PathVariable(value="id") Long id){
        try{
            return ResponseEntity.ok().body(sessaoService.findById(id));
        }catch (NotFoundException e){
            return ResponseEntity.badRequest().body(new ResponseSessaoDTO("Sessão não encontrada"));
        }
    }
}
