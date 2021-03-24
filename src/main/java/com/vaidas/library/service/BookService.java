package com.vaidas.library.service;

import com.vaidas.library.model.Book;
import com.vaidas.library.model.BookStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookService {

    public Book addBook(String name) {
        return new Book(UUID.randomUUID(), name, BookStatus.AVAILABLE);
    }
}
