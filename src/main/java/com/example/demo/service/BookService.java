package com.example.demo.service;

import com.example.demo.dto.BookRequest;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book create(BookRequest bookRequest) {
        if (bookRepository.findByName(bookRequest.getName()).isPresent()) {
            throw new IllegalStateException("Книг уже существует");
        }

        Book book = Book.builder()
                .name(bookRequest.getName())
                .year(bookRequest.getYear())
                .build();

        return bookRepository.save(book);
    }
}