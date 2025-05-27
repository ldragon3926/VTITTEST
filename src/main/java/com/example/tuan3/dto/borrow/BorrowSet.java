package com.example.tuan3.dto.borrow;

import com.example.tuan3.entity.Book;
import com.example.tuan3.entity.Borrows;
import com.example.tuan3.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class BorrowSet {
    private Long id;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    @NotNull(message = "Không được idUser để trống")
    private Long idUser;
    @NotNull(message = "Không được idBook để trống")
    private Long idBook;
    private boolean status;


    public Borrows dto(Borrows borrows) {
        borrows.setId(this.getId());
        borrows.setBook(Book.builder().id(this.getIdBook()).build());
        borrows.setUser(User.builder().id(this.getIdUser()).build());
        borrows.setBorrowDate(this.getBorrowDate());
        borrows.setReturnDate(this.getReturnDate());
        borrows.setStatus(this.isStatus());
        return borrows;
    }
}
