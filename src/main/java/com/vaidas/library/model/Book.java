package com.vaidas.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

import static com.vaidas.library.model.GlobalConstants.DEFAULT_DATE_FORMAT;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "book", uniqueConstraints = { @UniqueConstraint(columnNames = {"name" , "author", "release_date"}) })
public class Book {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, name = "release_date")
    @JsonFormat(pattern = DEFAULT_DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDate releaseDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BookStatus status;

}
