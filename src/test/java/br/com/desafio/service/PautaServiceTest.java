package br.com.desafio.service;

import br.com.desafio.model.Associado;
import br.com.desafio.model.Pauta;
import br.com.desafio.repository.AssociadoRepository;
import br.com.desafio.repository.PautaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PautaServiceTest {

    @Mock
    PautaRepository repository;
    @InjectMocks
    PautaService service;

    @BeforeEach
    public void setUp(){

    }

    @Test
    void createPauta() {

        Pauta pauta = Pauta.builder().descricao("Pauta Teste").build();

        Mockito.when(service.save(pauta)).thenReturn(pauta);
        Pauta expectedPauta = service.save(pauta);

        assertThat(pauta).isEqualTo(expectedPauta);
    }

    @Test
    void pautaFindById() {
        Long id = 1l;
        Pauta pauta = Pauta.builder().descricao("Pauta Teste").build();

        Mockito.when(service.findById(id)).thenReturn(Optional.of(pauta));
        Optional<Pauta> expectedPauta = service.findById(id);

        assertThat(expectedPauta).isNotNull();
    }

    @Test
    void pautaListAll() throws Exception {
        List<Pauta> list = new ArrayList<>();
        list.add(Pauta.builder().descricao("Pauta Teste").build());
        list.add(Pauta.builder().descricao("Pauta Teste").build());
        list.add(Pauta.builder().descricao("Pauta Teste").build());

        Mockito.when(service.listAll()).thenReturn(list);
        List<Pauta> listExpected = service.listAll();

        Assertions.assertEquals(listExpected, list);
    }
}
