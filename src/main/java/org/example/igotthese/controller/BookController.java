package org.example.igotthese.controller;

import java.util.List;
import org.example.igotthese.data.dto.BookDto;
import org.example.igotthese.data.dto.BookDto.MyBook;
import org.example.igotthese.data.dto.BookDto.Ranker;
import org.example.igotthese.data.dto.BookDto.Ranking;
import org.example.igotthese.data.dto.BookDto.Response;
import org.example.igotthese.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/mybook")
    public ResponseEntity<BookDto.Response> getBookByUserId(@RequestParam Long userId) {
        Response book = bookService.getBookByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/ranker")
    public ResponseEntity<MyBook> getRankerBook(@RequestParam Long userId) {
        MyBook rankerBook = bookService.getRankerBook(userId);
        return ResponseEntity.status(HttpStatus.OK).body(rankerBook);
    }

}
