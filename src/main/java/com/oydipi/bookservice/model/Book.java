package com.oydipi.bookservice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Book {
    String id;
    String author;
    String title;
}
