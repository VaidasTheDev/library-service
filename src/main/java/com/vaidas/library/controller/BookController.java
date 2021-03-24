package com.vaidas.library.controller;

import com.vaidas.library.model.Book;
import com.vaidas.library.model.BookDetails;
import com.vaidas.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("book")
public class BookController implements BookControllerSpec {

    private final BookService bookService;

    @Override
    public ResponseEntity<Book> addBook(BookDetails bookDetails) {
        log.info("Request to add a book to the library with name '{}'", bookDetails.getName());

        Book book = bookService.addBook(bookDetails.getName(), bookDetails.getAuthor(), bookDetails.getReleaseDate());
        log.info("Successfully added a book to the library with name '{}'", book.getName());

        return ResponseEntity.ok(book);
    }
}
