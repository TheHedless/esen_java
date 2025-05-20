package com.esen.Bookstore.service;

import com.esen.Bookstore.model.Book;
import com.esen.Bookstore.model.Bookstore;
import com.esen.Bookstore.repository.BookRepository;
import com.esen.Bookstore.repository.BookstoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookstoreService bookstoreService;

    public void save(Book book){
        bookRepository.save(book);
    }
    public List<Book> findAll(){
        return bookRepository.findAll();
    }
    public void delete(Long id){
        if (!bookRepository.existsById(id)){
            throw new IllegalArgumentException("No book by this id "+id);
        }
        var book=bookRepository.findById(id).get();
        bookstoreService.removeBookFromInventories(book);
        bookRepository.delete(book);
    }
    public Book update(Long id, String title, String author, String publisher, Double price){
        if(Stream.of(title,author,publisher,price).allMatch(Objects::isNull)){
            throw new IllegalArgumentException("At least one field is to be not null");
        }
        if (!bookRepository.existsById(id)){
            throw new IllegalArgumentException("No book by this id "+id);
        }
        var book = bookRepository.findById(id).get();
        if (title!=null){
            book.setTitle(title);
        }if (author!=null){
            book.setAuthor(author);
        }if (publisher!=null){
            book.setPublisher(publisher);
        }if (price!=null){
            book.setPrice(price);
        }
        return bookRepository.save(book);
    }
    public Book listBookId(Long id){
        if (!bookRepository.existsById(id)){
            throw new IllegalArgumentException("No book by this id "+id);
        }
        return bookRepository.findById(id).get();
    }
    public Map<String, Double> findPrices(Long id){
        if (!bookRepository.existsById(id)){
            throw new IllegalArgumentException("No book by this id "+id);
        }
        List<Bookstore> bookstores = bookstoreService.findAll();
        Map<String, Double> prices = new HashMap<>();
        for (Bookstore bookstore:bookstores){
            prices.put(
                    bookstore.getLocation(),
                    bookstore.getPriceModifier()*bookRepository.findById(id).get().getPrice());
        }
        return prices;
    }
    public List<Book> findByPublisherOrTitleOrAuthor(String publisher, String title, String author) {
        return bookRepository.findAll().stream().filter(book -> {
            if (title != null) {
                return book.getTitle().equals(title);
            }
            if (publisher != null) {
                return book.getPublisher().equals(publisher);
            }
            if (author != null) {
                return book.getAuthor().equals(author);
            }
            return true;
        }).toList();
    }
}
