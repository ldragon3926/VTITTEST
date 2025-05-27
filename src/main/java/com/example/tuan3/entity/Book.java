package com.example.tuan3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tương ứng SERIAL
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "authors", nullable = false)
    private String authors;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "pageCount", nullable = false)
    private Integer pageCount;

    @Column(name = "printType", nullable = false)
    private String printType;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "status")
    private boolean status;
@ManyToMany
    @JoinTable(name = "book_category",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> category = new HashSet<>();


}
