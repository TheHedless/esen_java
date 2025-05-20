package com.esen.Bookstore.repository;

import com.esen.Bookstore.model.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
}
