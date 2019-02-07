package com.oydipi.bookservice.controller;

import com.oydipi.bookservice.model.Book;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Book> getBooks(@RequestParam(required = false, defaultValue = "") String author,
                               @RequestParam(required = false, defaultValue = "") String title) {
        log.info("Books fetched by author {} and title {}", author, title);
        return books.stream()
                .filter("" .equals(author) ? b -> true : b -> b.getAuthor().equals(author))
                .filter("" .equals(title) ? b -> true : b -> b.getTitle().equals(title))
                .collect(Collectors.toList());
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
