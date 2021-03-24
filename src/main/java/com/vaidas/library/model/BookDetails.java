package com.vaidas.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Getter
public class BookDetails implements Serializable {

    @NotEmpty(message = BookValidationMessages.NAME_EMPTY)
    private String name;

    @NotEmpty(message = BookValidationMessages.AUTHOR_EMPTY)
    private String author;

    @NotNull(message = BookValidationMessages.RELEASE_DATE_NULL)
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date releaseDate;
}
