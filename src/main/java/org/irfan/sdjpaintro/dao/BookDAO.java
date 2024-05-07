package org.irfan.sdjpaintro.dao;

import org.irfan.sdjpaintro.domain.Book;

public interface BookDAO {

   Book findById(Long Id);

   Book findBookByTitle(String title);

   Book saveBook(Book book);

   Book updateBook(Book savedBook);


   void deleteBook(Long id);
}
