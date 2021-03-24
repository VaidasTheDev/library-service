package com.vaidas.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookData implements Serializable {

    @NotNull(message = BookValidationMessages.ID_NULL)
    private UUID id;

    @Null
    @NotEmpty(message = BookValidationMessages.NAME_EMPTY)
    @Size(min = 2, message = BookValidationMessages.NAME_TOO_SHORT)
    private String name;

    @Null
    @NotEmpty(message = BookValidationMessages.AUTHOR_EMPTY)
    @Size(min = 1, message = BookValidationMessages.AUTHOR_TOO_SHORT)
    private String author;

    @Null
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date releaseDate;

    @Null
    @Enumerated(value = EnumType.STRING)
    private BookStatus status;
}
