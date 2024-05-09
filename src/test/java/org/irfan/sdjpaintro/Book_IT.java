package org.irfan.sdjpaintro;


import lombok.extern.slf4j.Slf4j;
import org.irfan.sdjpaintro.dao.BookDAO;
import org.irfan.sdjpaintro.domain.Book;
import org.irfan.sdjpaintro.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@ComponentScan(basePackages = {"org.irfan.sdjpaintro.bootstrap", "org.irfan.sdjpaintro.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class Book_IT {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookDAO bookDAO;

    @Test
    void testMethod1() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }

    @Test
    void testMethod2() {
        Book book = bookDAO.findById(102L);
        log.info(book.toString());
    }

    @Test
    void testMethod3() {
        Book book = bookDAO.findBookByTitle("Domain Driven Design");
        log.info(book.toString());
    }

    @Test
    void testMethod4() {
        Book book = new Book("Test-title", "test-isbn", "test-publisher");

        Book savedBook = bookDAO.saveBook(book);

        assertThat(savedBook.getIsbn()).isEqualTo("test-isbn");
    }

    @Test
    void testMethod5() {
        Book book = new Book("Test-title-for-update", "test-isbn-for-update", "test-publisher-for-update");

        Book savedBook = bookDAO.saveBook(book);

        assertThat(savedBook.getIsbn()).isEqualTo("test-isbn-for-update");
        assertThat(savedBook.getTitle()).isEqualTo("Test-title-for-update");

        savedBook.setIsbn("test-update-done");
        savedBook.setTitle("test-title-done");

        Book updatedBook = bookDAO.updateBook(savedBook);


        assertThat(updatedBook.getIsbn()).isEqualTo("test-update-done");
        assertThat(updatedBook.getTitle()).isEqualTo("test-title-done");
    }

    @Test
    void testMethod6() {
        bookDAO.deleteBook(6L);
    }

}
