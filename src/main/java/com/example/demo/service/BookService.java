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
        if (bookRepository.findByName(bookRequest.name()).isPresent()) {
            throw new IllegalStateException("Книг уже существует");
        }

        Book book = Book.builder()
                .name(bookRequest.name())
                .year(bookRequest.year())
                .build();

        return bookRepository.save(book);
    }
    public void delete( Long id){
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Книга с ID " + id + " не найдена");
        }
        bookRepository.deleteById(id);
    }

    public Book update(Long id, BookRequest bookRequest) {
        Book upBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книга не найдена"));


        if (bookRepository.existsByNameAndIdNot(bookRequest.name(), id)) {
            throw new IllegalStateException("Книга с таким названием уже существует");
        }

        upBook.setName(bookRequest.name());
        upBook.setYear(bookRequest.year());

        return bookRepository.save(upBook);
    }
}