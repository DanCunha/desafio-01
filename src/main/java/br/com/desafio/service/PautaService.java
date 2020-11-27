package br.com.desafio.service;

import br.com.desafio.model.Pauta;
import br.com.desafio.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta save(Pauta pauta) throws Exception {
        validaPauta(pauta.getDescricao());
        return pautaRepository.save(pauta);
    }

    public List<Pauta> listAll(){
        return pautaRepository.findAll();
    }

    public Optional<Pauta> findById(Long id){
        return pautaRepository.findById(id);
    }

    private void validaPauta(String descricao) {
        if(pautaRepository.findByDescricao(descricao).isPresent())
            throw new EntityExistsException("Descrição de Pauta já existe");
    }
}
