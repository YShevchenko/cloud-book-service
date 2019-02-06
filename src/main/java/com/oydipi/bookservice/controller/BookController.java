package com.oydipi.bookservice.controller;

import com.oydipi.bookservice.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    //setup some data for test
    private static List<Book> books = new ArrayList<>(
            Arrays.asList(
                    new Book("b1", "John", "John's book"),
                    new Book("b2", "Jane", "Jane's book")
            ));

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
