package org.irfan.sdjpaintro;

import lombok.extern.slf4j.Slf4j;
import org.irfan.sdjpaintro.dao.AuthorDao;
import org.irfan.sdjpaintro.domain.Author;
import org.irfan.sdjpaintro.repo.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("local")
@ComponentScan(basePackages = {"org.irfan.sdjpaintro.bootstrap", "org.irfan.sdjpaintro.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class Author_IT {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorDao authorDao;


    @Test
    void getAuthorByIdTest() {
        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();
    }

    @Test
    void getAuthorNameTest() {
        Author author = authorDao.findAuthorByName("Irfan", "khan");

        assertThat(author).isNotNull();
    }

    @Test
    void saveAuthorTest() {
        Author author = authorDao.saveNewAuthor(new Author("test-author-123","test-456"));

        log.info(author.getId().toString());

        assertThat(author).isNotNull();
    }


    @Test
    void updateAuthorTest() {
        Author saveNewAuthor = authorDao.saveNewAuthor(new Author("test-author-143","test-456"));

        log.info(saveNewAuthor.getId().toString());

        assertThat(saveNewAuthor).isNotNull();


        saveNewAuthor.setFirstName("updated-first-name");
        Author updatedAuthor=authorDao.updateAuthor(saveNewAuthor);

        assertThat(updatedAuthor.getFirstName()).isEqualTo("updated-first-name");
    }

    @Test
    void deleteAuthorTest(){
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        Author saved = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            authorDao.getById(saved.getId());
        });
    }
}
