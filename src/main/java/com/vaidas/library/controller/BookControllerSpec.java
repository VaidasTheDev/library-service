package com.vaidas.library.controller;


import com.vaidas.library.model.Book;
import com.vaidas.library.model.BookDetails;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookControllerSpec {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Book> addBook(@RequestBody BookDetails bookDetails);
}
