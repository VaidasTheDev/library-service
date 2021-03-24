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

    public Book addBook(BookDetails bookDetails) {
        log.info("Attempt to add a new book '{}'", bookDetails);

        String name = bookDetails.getName();
        String author = bookDetails.getAuthor();
        Date releaseDate = bookDetails.getReleaseDate();

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

    public Book editBook(Book updatedBook) {
        log.info("Attempt to edit book with id '{}'", updatedBook.getId());

        Optional<Book> optionalBook = bookRepository.findById(updatedBook.getId());
        if (optionalBook.isEmpty()) {
            throw new RuntimeException("Book with id '" + updatedBook.getId() + "' does not exist");
        }

        Book persistedBook = optionalBook.get();

        // Only update fields with values
        if (updatedBook.getName() != null) {
            persistedBook.setName(updatedBook.getName());
        }
        if (updatedBook.getAuthor() != null) {
            persistedBook.setAuthor(updatedBook.getAuthor());
        }
        if (updatedBook.getReleaseDate() != null) {
            persistedBook.setReleaseDate(updatedBook.getReleaseDate());
        }
        if (updatedBook.getStatus() != null) {
            persistedBook.setStatus(updatedBook.getStatus());
        }

        persistedBook = bookRepository.save(persistedBook);
        log.info("Successfully edited book with id '{}'", persistedBook.getId());
        return persistedBook;
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
