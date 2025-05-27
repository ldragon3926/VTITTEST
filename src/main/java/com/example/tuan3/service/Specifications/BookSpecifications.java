package com.example.tuan3.service.Specifications;

import com.example.tuan3.entity.Book;
import com.example.tuan3.entity.Permission;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {
    public static Specification<Book> hasCode(String code) {
        return  ((root, query, cb) ->
                code  == null ? null : cb.like(cb.lower(root.get("code")),"%" + code.toLowerCase() + "%"));
    }
    public static Specification<Book> hasTitle(String title) {
        return  ((root, query, cb) ->
                title  == null ? null : cb.like(cb.lower(root.get("title")),"%" + title.toLowerCase() + "%"));
    }
    public static Specification<Book> hasAuthors(String authors) {
        return  ((root, query, cb) ->
                authors  == null ? null : cb.like(cb.lower(root.get("authors")),"%" + authors.toLowerCase() + "%"));
    }
    public static Specification<Book> hasPublisher(String publisher) {
        return  ((root, query, cb) ->
                publisher  == null ? null : cb.like(cb.lower(root.get("publisher")),"%" + publisher.toLowerCase() + "%"));

    }
    public static Specification<Book> hasPageCount(Integer pageCount) {
        return  ((root, query, cb) ->
                pageCount  == null ? null : cb.equal(root.get("pageCount"), pageCount));
    }
    public static Specification<Book> hasPrintType(String printType) {
        return  ((root, query, cb) ->
                printType  == null ? null : cb.like(cb.lower(root.get("printType")),"%" + printType.toLowerCase() + "%"));

    }
    public static Specification<Book> hasLanguage(String language) {
        return  ((root, query, cb) ->
                language  == null ? null : cb.like(cb.lower(root.get("language")),"%" + language.toLowerCase() + "%"));
    }
    public static Specification<Book> hasDescription(String description) {
        return  ((root, query, cb) ->
                description  == null ? null : cb.like(cb.lower(root.get("description")),"%" + description.toLowerCase() + "%"));
    }
    public static Specification<Book> hasQuantity(Integer quantity) {
        return  ((root, query, cb) ->
                quantity  == null ? null : cb.equal(root.get("quantity"), quantity));
    }
}


