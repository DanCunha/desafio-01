package br.com.desafio.service;

import br.com.desafio.model.Pauta;
import br.com.desafio.model.Sessao;
import br.com.desafio.repository.PautaRepository;
import br.com.desafio.repository.SessaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SessaoServiceTest {

    @Mock
    SessaoRepository repository;
    @Mock
    PautaRepository pautaRepository;
    @InjectMocks
    SessaoService service;
    @InjectMocks
    PautaService pautaService;

    @BeforeEach
    public void setUp(){

    }

    @Test
    void createSessao() throws Exception {

        Pauta pauta = Pauta.builder().id(1l).descricao("Pauta Teste").build();
        Mockito.when(pautaRepository.findById(1l)).thenReturn(Optional.of(pauta));
        Optional<Pauta> exPauta = pautaRepository.findById(1l);

        Sessao sessao = Sessao.builder().tempoSessao(5).dataHoraInicio(LocalDateTime.now()).pauta(exPauta.get()).build();
        Mockito.when(service.save(sessao)).thenReturn(sessao);

        Sessao expectedSessao = service.save(sessao);

        assertThat(sessao).isEqualTo(expectedSessao);
    }
}
