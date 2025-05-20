package com.esen.Bookstore.controller;


import com.esen.Bookstore.model.Book;
import com.esen.Bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("books") // localhost:3221/books alatt lesz elérhető az itteni method
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> findAll(){
        return ResponseEntity.ok(bookService.findAll());
    }
}
