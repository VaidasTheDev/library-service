package com.vaidas.library.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vaidas.library.model.Book;
import com.vaidas.library.model.BookDetails;
import com.vaidas.library.model.messages.BookValidationMessages;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

@Validated
public interface BookControllerSpec {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Book> addBook(@RequestBody @Valid BookDetails bookDetails);

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Book> editBook(@RequestBody @Valid Book book);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Book>> getBooks(
            @RequestParam(required = false)
            @Null
            @Min(value = 1, message = BookValidationMessages.NAME_TOO_SHORT)
                    String name,
            @RequestParam(required = false)
            @Null
            @Min(value = 1, message = BookValidationMessages.AUTHOR_TOO_SHORT)
                    String author,
            @RequestParam(required = false)
            @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
                    Date releaseDate
    );
}
