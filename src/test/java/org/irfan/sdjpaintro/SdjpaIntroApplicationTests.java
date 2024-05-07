package org.irfan.sdjpaintro;

import org.irfan.sdjpaintro.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SdjpaIntroApplicationTests {

	@Autowired
	BookRepository bookRepository;

	@Test
	void testBookRepo(){
		Long count= bookRepository.count();
		assertThat(count).isGreaterThan(0);
	}

	@Test
	void contextLoads() {
	}

}
