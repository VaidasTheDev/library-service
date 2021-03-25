package com.vaidas.library.service;

import com.vaidas.library.model.Book;
import com.vaidas.library.model.BookDetails;
import com.vaidas.library.model.BookStatus;
import com.vaidas.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.vaidas.library.model.messages.BookServiceErrorMessages.ERROR_DUPLICATE_BOOK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks BookService bookService;

    private final String SAMPLE_BOOK_NAME = "Sample Book";
    private final String SAMPLE_AUTHOR = "Sample Author";
    private final Date SAMPLE_RELEASE_DATE = new Date();

    @Test
    public void shouldAddBook() {
        // GIVEN
        BookDetails bookDetails = new BookDetails(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR, SAMPLE_RELEASE_DATE);
        Book mockBook = new Book(
                UUID.randomUUID(),
                SAMPLE_BOOK_NAME,
                SAMPLE_AUTHOR,
                SAMPLE_RELEASE_DATE,
                BookStatus.AVAILABLE
        );
        when(bookRepository.save(any())).thenReturn(mockBook);
        when(bookRepository.findBookByNameAndAuthorAndReleaseDate(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR, SAMPLE_RELEASE_DATE))
                .thenReturn(Optional.empty());

        // WHEN
        Book book = bookService.addBook(bookDetails);

        // THEN
        assertEquals(mockBook, book);
    }

    @Test
    public void shouldNotAddDuplicateBooks() {
        // GIVEN
        BookDetails bookDetails = new BookDetails(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR, SAMPLE_RELEASE_DATE);
        Book mockBook = new Book(
                UUID.randomUUID(),
                SAMPLE_BOOK_NAME,
                SAMPLE_AUTHOR,
                SAMPLE_RELEASE_DATE,
                BookStatus.AVAILABLE
        );
        when(bookRepository.findBookByNameAndAuthorAndReleaseDate(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR, SAMPLE_RELEASE_DATE))
                .thenReturn(Optional.of(mockBook));

        // WHEN
        try {
            bookService.addBook(bookDetails);
            fail("Should throw RuntimeException exception");
        } catch (RuntimeException e) {
            // THEN
            assertEquals(ERROR_DUPLICATE_BOOK, e.getMessage());
        }
    }
}
