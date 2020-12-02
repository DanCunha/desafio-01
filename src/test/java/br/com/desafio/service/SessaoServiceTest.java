package br.com.desafio.service;

import br.com.desafio.dto.ResponseSessaoDTO;
import br.com.desafio.exception.CpfInvalidException;
import br.com.desafio.model.Associado;
import br.com.desafio.model.Pauta;
import br.com.desafio.model.Sessao;
import br.com.desafio.model.Voto;
import br.com.desafio.repository.AssociadoRepository;
import br.com.desafio.repository.PautaRepository;
import br.com.desafio.repository.SessaoRepository;
import br.com.desafio.repository.VotoRepository;
import javassist.NotFoundException;
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

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SessaoServiceTest {

    @Mock
    SessaoRepository repository;
    @Mock
    PautaRepository pautaRepository;
    @Mock
    VotoRepository votoRepository;
    @Mock
    AssociadoRepository associadoRepository;
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

    @Test
    void sessaoListAll() {
        List<Sessao> list = new ArrayList<>();
        list.add(Sessao.builder().dataHoraInicio(LocalDateTime.now()).tempoSessao(5).pauta(new Pauta("Pauta Teste")).build());
        list.add(Sessao.builder().dataHoraInicio(LocalDateTime.now()).tempoSessao(5).pauta(new Pauta("Pauta Teste")).build());
        list.add(Sessao.builder().dataHoraInicio(LocalDateTime.now()).tempoSessao(5).pauta(new Pauta("Pauta Teste")).build());

        Mockito.when(service.listAll()).thenReturn(list);
        List<Sessao> listExpected = service.listAll();

        Assertions.assertEquals(listExpected, list);
    }

    @Test
    void sessaoFindById() throws NotFoundException {

        Long id = 1l;
        Sessao sessao = Sessao.builder().dataHoraInicio(LocalDateTime.now()).tempoSessao(5).pauta(new Pauta("Pauta Teste")).build();

        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(sessao));

        List<Voto> listVoto = new ArrayList<>();
        listVoto.add(Voto.builder().sessao(sessao).votoValue(true).associado(Associado.builder().id(1l).cpf("52328775268").nome("Daniel").build()).build());

        Mockito.when(votoRepository.findBySessaoId(1l)).thenReturn(listVoto);

        ResponseSessaoDTO responseSessaoDTO = new ResponseSessaoDTO();
        responseSessaoDTO.setSessaoId(1l);
        responseSessaoDTO.setDescricaoPauta("Pauta Teste");
        responseSessaoDTO.setVotoNao(0);
        responseSessaoDTO.setVotoSim(1);
        responseSessaoDTO.setTotal(1);

        ResponseSessaoDTO expectedResponse = service.findById(1l);

        assertThat(expectedResponse).isNotNull();
    }

    //Teste depende do retorno da api https://user-info.herokuapp.com/users/cpf que valida e invalida aleatóriamente.
/*    @Test
    void createVoto() throws Exception {

        Associado associado = Associado.builder().id(1l).nome("Daniel").cpf("52328775268").build();
        Mockito.when(associadoRepository.findById(1l)).thenReturn(Optional.of(associado));

        Pauta pauta = Pauta.builder().descricao("Pauta Teste").build();
        Mockito.when(pautaRepository.save(pauta)).thenReturn(pauta);

        Sessao sessao = Sessao.builder().id(1l).tempoSessao(5).dataHoraInicio(LocalDateTime.now()).pauta(pauta).build();
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(sessao));

        Voto voto = Voto.builder().id(1l).votoValue(true).associado(associado).sessao(sessao).build();

        Assertions.assertThrows(NotFoundException.class, () -> {
            service.votacao(voto);
        });
    }*/

    //Teste depende do retorno da api https://user-info.herokuapp.com/users/cpf que valida e invalida aleatóriamente.
/*    @Test
    void createVotoNaoHabilitado() throws Exception {

        Associado associado = Associado.builder().id(1l).nome("Daniel").cpf("94476435076").build();
        Mockito.when(associadoRepository.findById(1l)).thenReturn(Optional.of(associado));

        Pauta pauta = Pauta.builder().descricao("Pauta Teste").build();
        Mockito.when(pautaRepository.save(pauta)).thenReturn(pauta);

        Sessao sessao = Sessao.builder().id(1l).tempoSessao(5).dataHoraInicio(LocalDateTime.now()).pauta(pauta).build();
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(sessao));

        Voto voto = Voto.builder().id(1l).votoValue(true).associado(associado).sessao(sessao).build();

        Assertions.assertThrows(NotFoundException.class, () -> {
            service.votacao(voto);
        });
    }*/

    @Test
    void createVotoAssociadoNaoEncontrado() throws Exception {

        Associado associado = Associado.builder().id(1l).nome("Daniel").cpf("52328775268").build();
        Associado associado2 = Associado.builder().id(2l).nome("Bergson").cpf("52328775268").build();
        Mockito.when(associadoRepository.findById(1l)).thenReturn(Optional.of(associado));

        Pauta pauta = Pauta.builder().descricao("Pauta Teste").build();
        Mockito.when(pautaRepository.save(pauta)).thenReturn(pauta);

        Sessao sessao = Sessao.builder().id(1l).tempoSessao(5).dataHoraInicio(LocalDateTime.now()).pauta(pauta).build();
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(sessao));

        Voto voto = Voto.builder().id(1l).votoValue(true).associado(associado2).sessao(sessao).build();

        Assertions.assertThrows(NotFoundException.class, () -> {
            service.votacao(voto);
        });
    }

    @Test
    void createVotoSessaoNaoEncontrado() throws Exception {

        Associado associado = Associado.builder().id(1l).nome("Daniel").cpf("52328775268").build();
        Mockito.when(associadoRepository.findById(1l)).thenReturn(Optional.of(associado));

        Pauta pauta = Pauta.builder().descricao("Pauta Teste").build();
        Mockito.when(pautaRepository.save(pauta)).thenReturn(pauta);

        Sessao sessao = Sessao.builder().id(1l).tempoSessao(5).dataHoraInicio(LocalDateTime.now()).pauta(pauta).build();

        Voto voto = Voto.builder().id(1l).votoValue(true).associado(associado).sessao(sessao).build();

        Assertions.assertThrows(NotFoundException.class, () -> {
            service.votacao(voto);
        });

    }

    @Test
    void createVotoAssociadoJaVotou() throws Exception {

        Associado associado = Associado.builder().id(1l).nome("Daniel").cpf("52328775268").build();
        Mockito.when(associadoRepository.findById(1l)).thenReturn(Optional.of(associado));

        Pauta pauta = Pauta.builder().descricao("Pauta Teste").build();
        Mockito.when(pautaRepository.save(pauta)).thenReturn(pauta);

        Sessao sessao = Sessao.builder().id(1l).tempoSessao(5).dataHoraInicio(LocalDateTime.now()).pauta(pauta).build();
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(sessao));

        Voto voto = Voto.builder().id(1l).votoValue(true).associado(associado).sessao(sessao).build();
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(sessao));

        Mockito.when(votoRepository.findByAssociadoIdAndSessaoId(1l, 1l)).thenReturn(Optional.of(voto));

        Assertions.assertThrows(EntityExistsException.class, () -> {
            service.votacao(voto);
        });
    }
}
