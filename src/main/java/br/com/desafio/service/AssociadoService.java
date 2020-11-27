package br.com.desafio.service;

import br.com.desafio.config.swagger.utils.ValidateCpf;
import br.com.desafio.model.Associado;
import br.com.desafio.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository associadoRepository;

    public Associado save(Associado associado) throws Exception {
        validaAssociado(associado);
        return associadoRepository.save(associado);
    }

    public List<Associado> listAll(){
        return associadoRepository.findAll();
    }

    public Optional<Associado> findById(Long id){
        return associadoRepository.findById(id);
    }

    private void validaAssociado(Associado associado) throws Exception {
        ValidateCpf.isCPF(associado.getCpf());

        if(associadoRepository.findByCpf(associado.getCpf()).isPresent())
            throw new EntityExistsException("CPF já cadastrado.");

        if(associadoRepository.findByNome(associado.getNome()).isPresent())
            throw new EntityExistsException("Nome já cadastrado.");
    }
}

