package org.irfan.sdjpaintro.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.irfan.sdjpaintro.domain.Author;
import org.irfan.sdjpaintro.domain.Book;
import org.irfan.sdjpaintro.repo.AuthorRepository;
import org.irfan.sdjpaintro.repo.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
@Profile({"test", "default"})
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void run(String... args) throws Exception {

        addBookData();
        addAuthorData();

    }


    public void addBookData() {
        bookRepository.deleteAll();

        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse");
        Book savedDDD = bookRepository.save(bookDDD);

        log.info("Id : {}", savedDDD.getId());

        Book bookSIA = new Book("Spring In Action", "452", "Oriely");
        Book savedSIA = bookRepository.save(bookSIA);

        log.info("Id : {}", savedSIA.getId());

        bookRepository.findAll().forEach(book -> {
            log.info("Book Id: {}", book.getId());
            log.info("Book title: {}", book.getTitle());
        });
    }


    public void addAuthorData() {

        authorRepository.deleteAll();

        Author author1 = new Author("Irfan", "khan");
        Author savedAuthor1 = authorRepository.save(author1);

        log.info("Id : {}", savedAuthor1.getId());


        Author author2 = new Author("test", "author");
        Author savedAuthor2 = authorRepository.save(author2);

        log.info("Id : {}", savedAuthor2.getId());

        authorRepository.findAll().forEach(author -> {
            log.info("Author Id: {}", author.getId());
            log.info("Author firstName: {}", author.getFirstName());
            log.info("Author lastName: {}", author.getLastName());
        });
    }
}
