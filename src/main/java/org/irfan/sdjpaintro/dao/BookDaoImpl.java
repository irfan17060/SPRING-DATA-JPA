package org.irfan.sdjpaintro.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.irfan.sdjpaintro.domain.Book;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RequiredArgsConstructor
@Slf4j
@Service
public class BookDaoImpl implements BookDAO {

    private final DataSource source;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement ps = null;

    @Override
    public Book findById(Long Id) {


        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM book where id = ?");
            ps.setLong(1, Id);

            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();
                book.setId(Id);
                book.setTitle(resultSet.getString("title"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublisher(resultSet.getString("publisher"));
                return book;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                closeConnection(resultSet, connection, ps);

            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }

        return null;
    }

    @Override
    public Book findBookByTitle(String title) {
        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM book where title = ?");
            ps.setString(1, title);

            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublisher(resultSet.getString("publisher"));
                return book;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                closeConnection(resultSet, connection, ps);
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }

        return null;
    }

    @Override
    public Book saveBook(Book book) {
        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("INSERT INTO book (title, isbn, publisher) values (?, ?, ?)");
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getPublisher());
            ps.execute();

            Statement statement = connection.createStatement();


            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");

            if (resultSet.next()) {
                Long savedId = resultSet.getLong(1);
                return this.findById(savedId);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                closeConnection(resultSet, connection, ps);
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }

        return null;
    }

    @Override
    public Book updateBook(Book savedBook) {
        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("UPDATE book SET title = ?, isbn = ?, publisher = ? where book.id = ?");
            ps.setString(1, savedBook.getTitle());
            ps.setString(2, savedBook.getIsbn());
            ps.setString(3, savedBook.getPublisher());
            ps.setLong(4, savedBook.getId());
            ps.execute();

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                closeConnection(resultSet, connection, ps);
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }

        return this.findById(savedBook.getId());
    }

    @Override
    public void deleteBook(Long id) {
        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("DELETE from book where id = ?");
            ps.setLong(1, id);
            ps.execute();

        } catch (SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                closeConnection(null, connection, ps);
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    private void closeConnection(ResultSet resultSet, Connection connection, PreparedStatement ps) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
