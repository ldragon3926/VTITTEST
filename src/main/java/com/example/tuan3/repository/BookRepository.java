package com.example.tuan3.repository;

import com.example.tuan3.dto.book.BookDTO;
import com.example.tuan3.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

 @Query(value = "select code, title, authors, publisher, pageCount, printType, language, description, quantity   from books  where status = 1", nativeQuery = true)
 List<BookDTO> getAll();
 @Query(value = "select code, title, authors, publisher, pageCount, printType, language, description, quantity   from books  where status = 1", nativeQuery = true)
 Page<BookDTO> getPhanTrang(Pageable pageable);

}
