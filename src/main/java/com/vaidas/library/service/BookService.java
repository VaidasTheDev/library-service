package com.vaidas.library.service;

import com.vaidas.library.model.Book;
import com.vaidas.library.model.BookStatus;
import com.vaidas.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book addBook(String name, String author, Date releaseDate) {
        log.info("Attempt to add a new book '{}'", name);

        Optional<Book> bookOptional = bookRepository.findBookByNameAndAuthorAndReleaseDate(name, author, releaseDate);
        if (bookOptional.isPresent()) {
            log.error("A book with name '{}', author '{}' and release date '{}' already exists", name, author, releaseDate);
            throw new RuntimeException("Book already exists");
        }

        Book book = new Book(UUID.randomUUID(), name, author, releaseDate, BookStatus.AVAILABLE);
        book = bookRepository.save(book);

        log.info("Successfully added a new book with name '{}', author '{}' and release date '{}'", name, author, releaseDate);
        return book;
    }
}
