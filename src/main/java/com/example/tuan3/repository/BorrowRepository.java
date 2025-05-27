package com.example.tuan3.repository;

import com.example.tuan3.dto.borrow.BorrowDTO;
import com.example.tuan3.entity.Borrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrows, Long> {
    @Query(value = "SELECT \n" +
            "    u.fullname, \n" +
            "    b2.code, \n" +
            "    b.borrow_date, \n" +
            "    b.return_date \n" +
            "FROM \n" +
            "    borrows b \n" +
            "INNER JOIN users u ON b.user_id = u.id \n" +
            "INNER JOIN books b2 ON b.book_id = b2.id \n" +
            "ORDER BY \n" +
            "    b.return_date IS NOT NULL, \n" +
            "    b.return_date   where status = 1  \n", nativeQuery = true)
    List<BorrowDTO> getAll();

    @Query(value = "SELECT \n" +
            "    u.fullname, \n" +
            "    b2.code, \n" +
            "    b.borrow_date, \n" +
            "    b.return_date \n" +
            "FROM \n" +
            "    borrows b \n" +
            "INNER JOIN users u ON b.user_id = u.id \n" +
            "INNER JOIN books b2 ON b.book_id = b2.id \n" +
            "ORDER BY \n" +
            "    b.return_date IS NOT NULL, \n" +
            "    b.return_date   where status = 1  \n", nativeQuery = true)
    Page<BorrowDTO> getPhanTrang(Pageable pageable);
}
