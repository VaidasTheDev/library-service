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
import org.springframework.data.domain.Example;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.vaidas.library.model.messages.BookServiceErrorMessages.ERROR_DUPLICATE_BOOK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks BookService bookService;

    private final static String SAMPLE_BOOK_NAME = "Sample Book";
    private final static String SAMPLE_BOOK_NAME_2 = "Another Book";
    private final static String SAMPLE_AUTHOR = "Sample Author";
    private final static String SAMPLE_AUTHOR_2 = "Another Author";
    private final static LocalDate SAMPLE_RELEASE_DATE = LocalDate.now();
    private final static LocalDate SAMPLE_RELEASE_DATE_2 = LocalDate.now().minusDays(1);

    @Test
    public void shouldAddBook() {
        // GIVEN
        BookDetails bookDetails = new BookDetails(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR, SAMPLE_RELEASE_DATE);
        Book mockBook = buildMockBook(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR, SAMPLE_RELEASE_DATE);
        when(bookRepository.save(any())).thenReturn(mockBook);
        when(bookRepository.findBookByNameAndAuthorAndReleaseDate(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR, SAMPLE_RELEASE_DATE))
                .thenReturn(Optional.empty());

        // WHEN
        Book book = bookService.addBook(bookDetails);

        // THEN
        assertEquals(mockBook, book);
        verify(bookRepository, times(1)).findBookByNameAndAuthorAndReleaseDate(
                SAMPLE_BOOK_NAME,
                SAMPLE_AUTHOR,
                SAMPLE_RELEASE_DATE
        );
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    public void shouldNotAddDuplicateBooks() {
        // GIVEN
        BookDetails bookDetails = new BookDetails(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR, SAMPLE_RELEASE_DATE);
        Book mockBook = buildMockBook(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR, SAMPLE_RELEASE_DATE);
        when(bookRepository.findBookByNameAndAuthorAndReleaseDate(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR, SAMPLE_RELEASE_DATE))
                .thenReturn(Optional.of(mockBook));

        // WHEN
        try {
            bookService.addBook(bookDetails);
            fail("Should throw RuntimeException exception");
        } catch (RuntimeException e) {
            // THEN
            assertEquals(ERROR_DUPLICATE_BOOK, e.getMessage());
            verify(bookRepository, times(1)).findBookByNameAndAuthorAndReleaseDate(
                    SAMPLE_BOOK_NAME,
                    SAMPLE_AUTHOR,
                    SAMPLE_RELEASE_DATE
            );
            verify(bookRepository, times(0)).save(any());
        }
    }

    @Test
    public void shouldEditBook() {
        // GIVEN
        Book mockUpdatedBook = buildMockBook(
                SAMPLE_BOOK_NAME,
                SAMPLE_AUTHOR,
                SAMPLE_RELEASE_DATE
        );
        Book mockPersistedBook = buildMockBook(
                mockUpdatedBook.getId(),
                SAMPLE_BOOK_NAME_2,
                SAMPLE_AUTHOR_2,
                SAMPLE_RELEASE_DATE_2,
                BookStatus.UNAVAILABLE
        );
        when(bookRepository.findById(mockPersistedBook.getId()))
                .thenReturn(Optional.of(mockPersistedBook));
        when(bookRepository.save(eq(mockUpdatedBook))).thenReturn(mockUpdatedBook);

        // WHEN
        Book book = bookService.editBook(mockUpdatedBook);

        // THEN
        assertEquals(mockUpdatedBook, book);
        verify(bookRepository, times(1)).findById(mockUpdatedBook.getId());
        verify(bookRepository, times(1)).save(eq(mockUpdatedBook));
    }

    @Test
    public void shouldPartiallyEditBook() {
        // GIVEN
        Book mockUpdatedBook = buildMockBook(
                SAMPLE_BOOK_NAME,
                SAMPLE_AUTHOR_2,
                SAMPLE_RELEASE_DATE_2
        );
        Book mockPersistedBook = buildMockBook(
                mockUpdatedBook.getId(),
                SAMPLE_BOOK_NAME_2,
                SAMPLE_AUTHOR_2,
                SAMPLE_RELEASE_DATE_2,
                BookStatus.UNAVAILABLE
        );
        when(bookRepository.findById(mockPersistedBook.getId()))
                .thenReturn(Optional.of(mockPersistedBook));
        when(bookRepository.save(eq(mockUpdatedBook))).thenReturn(mockUpdatedBook);

        // WHEN
        Book book = bookService.editBook(mockUpdatedBook);

        // THEN
        assertEquals(mockUpdatedBook, book);
        verify(bookRepository, times(1)).findById(mockUpdatedBook.getId());
        verify(bookRepository, times(1)).save(eq(mockUpdatedBook));
    }

    @Test
    public void shouldGetBooks() {
        // GIVEN
        BookDetails bookDetails = new BookDetails(SAMPLE_BOOK_NAME, null, null);
        List<Book> bookList = List.of(
                buildMockBook(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR, SAMPLE_RELEASE_DATE),
                buildMockBook(SAMPLE_BOOK_NAME, SAMPLE_AUTHOR_2, SAMPLE_RELEASE_DATE_2)
        );

        when(bookRepository.findAll(any(Example.class)))
                .thenReturn(bookList);

        // WHEN
        List<Book> books = bookService.getBooks(bookDetails);

        // THEN
        assertEquals(bookList, books);
        verify(bookRepository, times(1)).findAll(any(Example.class));
    }

    private Book buildMockBook(String name, String author, LocalDate releaseDate) {
        return buildMockBook(
                UUID.randomUUID(),
                name,
                author,
                releaseDate,
                BookStatus.AVAILABLE
        );
    }

    private Book buildMockBook(UUID id, String name, String author, LocalDate releaseDate, BookStatus bookStatus) {
        return new Book(
                id,
                name,
                author,
                releaseDate,
                bookStatus
        );
    }
}
