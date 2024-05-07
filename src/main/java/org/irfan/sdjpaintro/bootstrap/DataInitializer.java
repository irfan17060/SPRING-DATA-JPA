package org.irfan.sdjpaintro.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.irfan.sdjpaintro.domain.Book;
import org.irfan.sdjpaintro.repo.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
@Profile({"test","default"})
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {

        bookRepository.deleteAll();

        log.info("started....");
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
}
