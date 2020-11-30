package br.com.desafio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Desafio01ApplicationTests {

	@Test
	void contextLoads() {
		String teste = "teste";
		assertThat(teste).isEqualTo(teste);
	}

}
