package com.vaidas.library.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookValidationMessages {

    public static final String ID_NULL = "Book ID is required";

    public static final String NAME_EMPTY = "Name is required";
    public static final String NAME_TOO_SHORT = "Book name must have at least 1 character";

    public static final String AUTHOR_EMPTY = "Author is required";
    public static final String AUTHOR_TOO_SHORT = "Book name must have at least 1 character";

    public static final String RELEASE_DATE_NULL = "Release date is required";
}
