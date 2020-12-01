package br.com.desafio.controller;

import br.com.desafio.dto.PautaDTO;
import br.com.desafio.dto.ResponseDTO;
import br.com.desafio.model.Pauta;
import br.com.desafio.service.PautaService;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/pauta")
@Api(value = "API Rest Pauta")
@CrossOrigin(origins = "*")
public class PautaController {

    @Autowired
    private PautaService service;

    @PostMapping
    @ApiOperation(value = "Criar nova pauta", response = Pauta.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<ResponseDTO> add(@Valid @RequestBody PautaDTO dto, UriComponentsBuilder uriBuilder) {
        try {
            Pauta entity  = service.save(dto.converter());
            URI uri = uriBuilder.path("/pauta/{id}").buildAndExpand(entity.getId()).toUri();
            return ResponseEntity.created(null).body(new ResponseDTO(entity.getId(),"Pauta criada com sucesso."));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
        }
    }

    @ApiOperation(value="Retorna uma lista de Pautas")
    @GetMapping
    public List<Pauta> listAll(){
        return service.listAll();
    }

    @ApiOperation(value="Retorna pauta por id")
    @GetMapping("/{id}")
    public ResponseEntity<Pauta> findById(@PathVariable(value="id") long id){
        Optional<Pauta> entity = service.findById(id);
        if(entity.isPresent())
            return ResponseEntity.ok().body(entity.get());
        else
            return ResponseEntity.noContent().build();
    }
}
