package br.com.desafio.controller;

import br.com.desafio.dto.AssociadoDTO;
import br.com.desafio.dto.ResponseDTO;
import br.com.desafio.model.Associado;
import br.com.desafio.repository.AssociadoRepository;
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
@RequestMapping(value = "/associado")
@Api(value = "API Rest Associado")
@CrossOrigin(origins = "*")
public class AssociadoController {

    @Autowired
    private AssociadoRepository associadoRepository;

    @PostMapping
    @ApiOperation(value = "Criar novo Associado", response = Associado.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    public ResponseEntity<ResponseDTO> add(@Valid @RequestBody AssociadoDTO dto) {
        try {
            Associado entity = associadoRepository.save(dto.converter());
            return ResponseEntity.created(null).body(new ResponseDTO(entity.getId(), "Associado cadastrado com sucesso."));
        }catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Associado já existe"));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
        }
    }
}
