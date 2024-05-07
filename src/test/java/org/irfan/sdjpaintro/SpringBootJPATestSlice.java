package org.irfan.sdjpaintro;

import org.irfan.sdjpaintro.domain.Book;
import org.irfan.sdjpaintro.repo.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"org.irfan.sdjpaintro.bootstrap"})
public class SpringBootJPATestSlice {

    @Autowired
    BookRepository bookRepository;

    @Order(1)
    @Test
    @Commit
    void testJPATestSlice1() {


        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
        Book testDDD = new Book("Test Design", "123", "test-publisher");
        bookRepository.save(testDDD);

        long countAfter = bookRepository.count();

        assertThat(countBefore).isLessThan(countAfter);

    }


    @Order(2)
    @Test
    void testJPATestSlice2() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(3);

    }
}
