package com.example.tuan3.controller;

import com.example.tuan3.dto.book.BookSet;
import com.example.tuan3.dto.user.UserSet;
import com.example.tuan3.entity.*;
import com.example.tuan3.repository.BookRepository;
import com.example.tuan3.repository.CategoryRepository;
import com.example.tuan3.repository.UserRepository;
import com.example.tuan3.response.ResponseUltils;
import com.example.tuan3.service.BookService;
import com.example.tuan3.service.Specifications.BookSpecifications;
import com.example.tuan3.service.Specifications.UserSpecifications;
import com.example.tuan3.service.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/library/book")
public class BookController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookService bookService;

    @GetMapping("/export-excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=book.xls";
        response.setHeader(headerKey, headerValue);

        List<Book> listBooks = bookRepository.findAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Books");
        Row headerRow = sheet.createRow(0);
        String[] header = {"STT", "ID", "Code", "Title", "Authors", "Publisher", "Page Count", "Print Type", "Language", "Description", "Quantity"};
        for (int i = 0; i < header.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header[i]);
        }
        int STT = 1;
        int rowCount = 1;
        for (Book book : listBooks) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(STT++);
            row.createCell(1).setCellValue(book.getId());
            row.createCell(2).setCellValue(book.getCode());
            row.createCell(3).setCellValue(book.getTitle());
            row.createCell(4).setCellValue(book.getAuthors());
            row.createCell(5).setCellValue(book.getPublisher());
            row.createCell(6).setCellValue(book.getPageCount());
            row.createCell(7).setCellValue(book.getPrintType());
            row.createCell(8).setCellValue(book.getLanguage());
            row.createCell(9).setCellValue(book.getDescription());
            row.createCell(10).setCellValue(book.getQuantity());
        }


        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

    @GetMapping
    public ResponseEntity<?> searchUser(@RequestParam(required = false) String code,
                                        @RequestParam(required = false) String description,
                                        @RequestParam(required = false) String author,
                                        @RequestParam(required = false) String title,
                                        @RequestParam(required = false) String language,
                                        @RequestParam(required = false) Integer pageCount,
                                        @RequestParam(required = false) String publisher,
                                        @RequestParam(required = false) String printType,
                                        @RequestParam(required = false) Integer quantity,
                                        @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                        @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);


        Specification<Book> bookSpecification = Specification.where(BookSpecifications.hasCode(code))
                .and(BookSpecifications.hasDescription(description))
                .and(BookSpecifications.hasTitle(title))
                .and(BookSpecifications.hasAuthors(author))
                .and(BookSpecifications.hasPageCount(pageCount))
                .and(BookSpecifications.hasPublisher(publisher))
                .and(BookSpecifications.hasPrintType(printType))
                .and(BookSpecifications.hasQuantity(quantity))
                .and(BookSpecifications.hasLanguage(language));
        return ResponseUltils.success(bookRepository.findAll(bookSpecification, pageable), "ROLE_VIEW_BOOK", "Tìm danh sách sách thành công");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseUltils.success(bookService.findAll(), "ROLE_VIEW_BOOK", "Lấy tất cả danh sách sách thành công");
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) {
        Optional<Book> book = bookService.findById(id);
        if (book.isEmpty()) {
            return ResponseUltils.error("error.book.not_found", "Sách không tồn tại");
        }
        return ResponseUltils.success(bookService.getOne(id), "ROLE_VIEW_BOOK", "Lấy sách thành công");
    }

    @PostMapping("/create")
    public ResponseEntity<?> add(@RequestBody @Valid BookSet bookSet, BindingResult bindingResult) {
        Book book = new Book();

        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        List<Category> categories = categoryRepository.findAllById(bookSet.getIdCategorys());
        Book book1 = bookSet.dto(book, categories);
        return ResponseUltils.success(bookService.add(book1), "ROLE_CREATE_BOOK", "Thêm sách thành công");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid BookSet bookSet, BindingResult bindingResult, @PathVariable(name = "id") Long id) {
        Book book = new Book();
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        List<Category> categories = categoryRepository.findAllById(bookSet.getIdCategorys());
        Book book1 = bookSet.dto(book, categories);
        return ResponseUltils.success(bookService.update(book1, id), "ROLE_UPDATE_BOOK", "Update sách thành công");
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        Optional<Book> book = bookService.findById(id);
        if (book.isEmpty()) {
            return ResponseUltils.error("error.book.not_found", "Sách không tồn tại");
        }
        Book bookD = bookService.findById(id).get();
        bookD.setStatus(false);
        bookService.update(bookD, id);
        return ResponseUltils.success(null, "ROLE_DELETE_BOOK", "Xóa sách thành công");
    }
}
