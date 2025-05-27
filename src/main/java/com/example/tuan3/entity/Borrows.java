package com.example.tuan3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "borrows")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Borrows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tương ứng SERIAL
    private Long id;
    @Column(name = "borrow_date")
    private LocalDate borrowDate;
    @Column(name = "return_date")
    private LocalDate returnDate;
    @Column(name = "status")
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;
}
