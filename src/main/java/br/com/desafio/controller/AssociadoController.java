package br.com.desafio.controller;

import br.com.desafio.dto.AssociadoDTO;
import br.com.desafio.dto.ResponseDTO;
import br.com.desafio.model.Associado;
import br.com.desafio.service.AssociadoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/associado")
@Api(value = "API Rest Associado")
@CrossOrigin(origins = "*")
public class AssociadoController {

    @Autowired
    private AssociadoService service;

    @PostMapping
    @ApiOperation(value = "Criar novo Associado", response = Associado.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<ResponseDTO> add(@Valid @RequestBody AssociadoDTO dto, UriComponentsBuilder uriBuilder) {
        try {
            Associado associado = service.save(dto.converter());
            URI uri = uriBuilder.path("/associado/{id}").buildAndExpand(associado.getId()).toUri();
            return ResponseEntity.created(uri).body(new ResponseDTO(associado.getId(), "Associado cadastrado com sucesso."));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
        }
    }

    @ApiOperation(value="Retorna uma lista de Associados")
    @GetMapping
    public ResponseEntity<List<Associado>> listAll(){
        return ResponseEntity.ok().body(service.listAll());
    }

    @ApiOperation(value="Retorna um associado por id")
    @GetMapping("/{id}")
    public ResponseEntity<Associado> findById(@PathVariable(value="id") Long id){
        Optional<Associado> entity = service.findById(id);
        if(entity.isPresent())
            return ResponseEntity.ok().body(entity.get());
        else
            return ResponseEntity.noContent().build();
    }

}
