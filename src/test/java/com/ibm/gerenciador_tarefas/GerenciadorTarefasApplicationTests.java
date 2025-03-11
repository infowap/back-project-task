package com.ibm.gerenciador_tarefas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.profiles.active=test")
class GerenciadorTarefasApplicationTests {

	@Test
	void contextLoads() {
		GerenciadorTarefasApplication.main(new String[] {});
	}

}
