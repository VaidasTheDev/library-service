package com.vaidas.library.model.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookServiceErrorMessages {

    public final static String ERROR_DUPLICATE_BOOK = "Book already exists";
    public final static String ERROR_NOT_FOUND = "Book does not exist";
}
