package br.com.desafio.service;

import br.com.desafio.model.Associado;
import br.com.desafio.repository.AssociadoRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AssociadoServiceTest {

    @Mock
    AssociadoRepository repository;
    @InjectMocks
    AssociadoService service;

    @BeforeEach
    public void setUp(){

    }

    @Test
    public void createAssociado() throws Exception {

        Associado associado = Associado.builder().nome("Daniel").cpf("52328775268").build();

        Mockito.when(service.save(associado)).thenReturn(associado);
        Associado expectedAssociado = service.save(associado);

        assertThat(associado).isEqualTo(expectedAssociado);
    }

    @Test
    public void associadoListAll() throws Exception {
        List<Associado> list = new ArrayList<>();
        list.add(Associado.builder().nome("Daniel").cpf("52328775268").build());
        list.add(Associado.builder().nome("Daniel").cpf("52328775268").build());
        list.add(Associado.builder().nome("Daniel").cpf("52328775268").build());

        Mockito.when(service.listAll()).thenReturn(list);
        List<Associado> listExpected = service.listAll();

        Assertions.assertEquals(listExpected, list);
    }

    @Test
    public void associadoFindById() throws Exception {
        Long id = 1l;
        Associado associado = Associado.builder().id(1l).nome("Daniel").cpf("52328775268").build();

        Mockito.when(service.findById(id)).thenReturn(Optional.of(associado));
        Optional<Associado> expectedAssociado = service.findById(id);

        assertThat(expectedAssociado).isNotNull();
    }

    @Test
    public void throwExceptionDueCpfInvalidCreateAssociado() throws Exception {

        Associado associado = Associado.builder().nome("Daniel").cpf("52328775111").build();

        Assertions.assertThrows(Exception.class, () -> {
            Mockito.when(service.save(associado)).thenReturn(associado);
        });
    }
}
