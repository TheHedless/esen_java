package com.esen.Bookstore.service;

import com.esen.Bookstore.model.Book;
import com.esen.Bookstore.model.Bookstore;
import com.esen.Bookstore.repository.BookRepository;
import com.esen.Bookstore.repository.BookstoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookstoreService {
    private final BookstoreRepository bookstoreRepository;

    public void removeBookFromInventories(Book book){
        bookstoreRepository.findAll()
                .forEach(bookstore->{
                    bookstore.getInventory().remove(book);
                    bookstoreRepository.save(bookstore);
                });
    }
    public List<Bookstore> findAll(){
        return bookstoreRepository.findAll();
    }
    public void deletebookstore(Long id){
        if (!bookstoreRepository.existsById(id)){
            throw new IllegalArgumentException("No book by this id "+id);
        }
        var bookstore=bookstoreRepository.findById(id).get();
        bookstoreRepository.delete(bookstore);
    }
    public void save(Bookstore bookstore){
        bookstoreRepository.save(bookstore);
    }
    public Bookstore update(Long id, String location, Double priceModifier, Double moneyInCashRegister ){
        if(Stream.of(location,priceModifier,moneyInCashRegister).allMatch(Objects::isNull)){
            throw new IllegalArgumentException("At least one field is to be not null");
        }
        if (!bookstoreRepository.existsById(id)){
            throw new IllegalArgumentException("No book by this id "+id);
        }
        var bookstore = bookstoreRepository.findById(id).get();
        if (location!=null){
            bookstore.setLocation(location);
        }if (priceModifier!=null){
            bookstore.setPriceModifier(priceModifier);
        }if (moneyInCashRegister!=null){
            bookstore.setMoneyInCashRegister(moneyInCashRegister);
        }
        return bookstoreRepository.save(bookstore);
    }
}
