package br.com.desafio.controller;

import br.com.desafio.dto.PautaDTO;
import br.com.desafio.dto.ResponseDTO;
import br.com.desafio.model.Associado;
import br.com.desafio.model.Pauta;
import br.com.desafio.repository.PautaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/pauta")
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
    public ResponseEntity<ResponseDTO> add(@Valid @RequestBody PautaDTO dto) {
        try {
            Pauta entity = pautaRepository.save(dto.converter());
            return ResponseEntity.created(null).body(new ResponseDTO(entity.getId(),"Pauta criada com sucesso."));
        }catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Descrição de Pauta já existe"));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
        }
    }

    @ApiOperation(value="Retorna uma lista de Pautas")
    @GetMapping
    public List<Pauta> listAll(){
        return pautaRepository.findAll();
    }

    @ApiOperation(value="Retorna pauta por id")
    @GetMapping("/{id}")
    public List<Pauta> findById(@PathVariable(value="id") long id){
        return pautaRepository.findAll();
    }
}
