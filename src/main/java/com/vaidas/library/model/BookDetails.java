package com.vaidas.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDetails implements Serializable {

    @NotEmpty(message = BookValidationMessages.NAME_EMPTY)
    @Min(value = 2, message = BookValidationMessages.NAME_TOO_SHORT)
    private String name;

    @NotEmpty(message = BookValidationMessages.AUTHOR_EMPTY)
    @Min(value = 1, message = BookValidationMessages.AUTHOR_TOO_SHORT)
    private String author;

    @NotNull(message = BookValidationMessages.RELEASE_DATE_NULL)
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date releaseDate;
}
