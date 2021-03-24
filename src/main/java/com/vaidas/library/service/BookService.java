package com.vaidas.library.service;

import com.vaidas.library.model.Book;
import com.vaidas.library.model.BookDetails;
import com.vaidas.library.model.BookStatus;
import com.vaidas.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

    public List<Book> getBooks(BookDetails bookDetails) {
        log.info("Attempt to retrieve books OPTIONALLY filtered by name, author and release date");

        Book book = Book.builder()
                .name(bookDetails.getName())
                .author(bookDetails.getAuthor())
                .releaseDate(bookDetails.getReleaseDate())
                .build();

        List<Book> books = bookRepository.findAll(Example.of(book));
        log.info("Successfully retrieved {} books", books.size());

        return books;
    }
}
