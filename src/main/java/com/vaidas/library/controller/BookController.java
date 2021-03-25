package com.vaidas.library.controller;

import com.vaidas.library.model.Book;
import com.vaidas.library.model.BookDetails;
import com.vaidas.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("book")
public class BookController implements BookControllerSpec {

    private final BookService bookService;

    @Override
    public ResponseEntity<Book> addBook(BookDetails bookDetails) {
        log.info("Request to add a book to the library with details '{}'", bookDetails);

        Book book = bookService.addBook(bookDetails);
        log.info("Successfully added a book to the library with details '{}'", bookDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @Override
    public ResponseEntity<Book> editBook(Book updatedBook) {
        log.info("Request to add a book to the library with details '{}'", updatedBook);

        Book editedBook = bookService.editBook(updatedBook);
        log.info("Successfully added a book to the library with name '{}'", editedBook);

        return ResponseEntity.ok(editedBook);
    }

    @Override
    public ResponseEntity<List<Book>> getBooks(String name, String author, LocalDate releaseDate) {
        BookDetails bookDetails = new BookDetails(name, author, releaseDate);
        log.info("Request to retrieve books with details: {}", bookDetails);

        List<Book> books = bookService.getBooks(bookDetails);
        log.info("Successfully retrieved books with details: {}", bookDetails);

        return ResponseEntity.ok(books);
    }
}
