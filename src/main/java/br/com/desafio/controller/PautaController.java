package br.com.desafio.controller;

import br.com.desafio.dto.PautaDTO;
import br.com.desafio.model.Pauta;
import br.com.desafio.repository.PautaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/child")
@Api(value = "API Rest Pauta")
@CrossOrigin(origins = "*")
public class PautaController {

    @Autowired
    private PautaRepository pautaRepository;

    @PostMapping
    @ApiOperation(value = "Criar nova pauta", response = Pauta.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<Object> add(@Valid @RequestBody PautaDTO dto, UriComponentsBuilder uriBuilder) {
        try {
            Pauta entity = pautaRepository.save(dto.converter());
            URI uri = uriBuilder.path("/child/{id}").buildAndExpand(entity.getId()).toUri();
            return ResponseEntity.created(uri).body(new PautaDTO(entity));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
