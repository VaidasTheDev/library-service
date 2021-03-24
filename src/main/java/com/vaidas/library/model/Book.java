package com.vaidas.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book", uniqueConstraints = { @UniqueConstraint(columnNames = {"name" , "author", "release_date"}) })
public class Book {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, name = "release_date")
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date releaseDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BookStatus status;

}
