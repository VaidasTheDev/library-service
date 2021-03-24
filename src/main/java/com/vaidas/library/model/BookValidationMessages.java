package com.vaidas.library.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookValidationMessages {

    public static final String NAME_EMPTY = "Please provide the name of the book";
    public static final String NAME_TOO_SHORT = "Book name must have at least 1 character";

    public static final String AUTHOR_EMPTY = "Please provide the name of author";
    public static final String AUTHOR_TOO_SHORT = "Book name must have at least 1 character";

    public static final String RELEASE_DATE_NULL = "Please provide the release date";
}
