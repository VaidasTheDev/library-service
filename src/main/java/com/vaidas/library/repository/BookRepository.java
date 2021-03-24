package com.vaidas.library.repository;

import com.vaidas.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    Optional<Book> findBookByNameAndAuthorAndReleaseDate(String name, String author, Date releaseDate);
}
