package com.oydipi.bookservice.controller;

import com.oydipi.bookservice.model.Book;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
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
        log.info("All books fetched");
        return books;
    }

    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable String bookId) {
        log.info("book id {} retrieved", bookId);
        return books.stream().filter(b -> b.getId().equals(bookId)).findFirst().orElse(null);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        log.info("book added {}", book);
        return books.add(book) ? book : null;
    }
}
