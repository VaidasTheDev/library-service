package com.vaidas.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
public class BookDetails {

    @NotEmpty(message = BookValidationMessages.NAME_EMPTY)
    private final String name;
}
