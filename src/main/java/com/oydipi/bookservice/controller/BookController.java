package com.oydipi.bookservice.controller;

import com.oydipi.bookservice.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private static List<Book> books = new ArrayList<>();

    @GetMapping
    public List<Book> getBooks() {
        return books;
    }

    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable String bookId) {
        return books.stream().filter(b -> b.getId().equals(bookId)).findFirst().orElse(null);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return books.add(book) ? book : null;
    }
}
