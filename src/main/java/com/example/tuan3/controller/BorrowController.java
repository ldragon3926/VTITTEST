package com.example.tuan3.controller;

import com.example.tuan3.dto.book.BookSet;
import com.example.tuan3.dto.borrow.BorrowSet;
import com.example.tuan3.dto.borrow.BorrowSetUpdate;
import com.example.tuan3.entity.Book;
import com.example.tuan3.entity.Borrows;
import com.example.tuan3.entity.Category;
import com.example.tuan3.repository.BookRepository;
import com.example.tuan3.response.ResponseUltils;
import com.example.tuan3.service.BookService;
import com.example.tuan3.service.BorrowService;
import com.example.tuan3.service.Specifications.BookSpecifications;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/library/borrow")
public class BorrowController {
    @Autowired
    BorrowService borrowService;

    @GetMapping
    public ResponseEntity<?> view(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page) {
        return ResponseUltils.success(borrowService.phanTrang(page), "Lấy danh sách phân trang thành công");
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseUltils.success(borrowService.findAll(), "Lấy tất cả danh sách phiếu mượn thành công");
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) {
        Optional<Borrows> borrows = borrowService.findById(id);
        if (borrows.isEmpty()) {
            return ResponseUltils.error("error.borrow.not_found", "Phiếu mượn không tồn tại");
        }
        return ResponseUltils.success(borrowService.getOne(id), "Lấy phiếu mượn thành công");
    }

    @PostMapping("/create")
    public ResponseEntity<?> add(@RequestBody @Valid BorrowSet borrowSet, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        Borrows borrow = borrowSet.dto(new Borrows());
        LocalDate now = LocalDate.now();
        borrow.setBorrowDate(now);
        return ResponseUltils.success(borrowService.add(borrow), "Thêm phiếu mượn thành công");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid BorrowSetUpdate borrowSet, BindingResult bindingResult, @PathVariable(name = "id") Long id) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        Borrows borrow = borrowSet.dto(new Borrows());
        return ResponseUltils.success(borrowService.update(borrow, id), "Update phiếu mượn thành công");
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        Optional<Borrows> borrows = borrowService.findById(id);
        if (borrows.isEmpty()) {
            return ResponseUltils.error("error.borrow.not_found", "Phiếu mượn không tồn tại");
        }
        Borrows borrowsD = borrowService.findById(id).get();
        borrowsD.setStatus(false);
        borrowService.update(borrowsD, id);
        return ResponseUltils.success(null, "Xóa phiếu mượn thành công");
    }
}
