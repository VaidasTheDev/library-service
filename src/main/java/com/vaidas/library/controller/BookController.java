package com.vaidas.library.controller;

import com.vaidas.library.model.Book;
import com.vaidas.library.model.BookDetails;
import com.vaidas.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController("/name")
public class BookController implements BookControllerSpec {

    private final BookService bookService;

    @Override
    public ResponseEntity<Book> addBook(BookDetails bookDetails) {
        Book book = bookService.addBook(bookDetails.getName());
        return ResponseEntity.ok(book);
    }
}
