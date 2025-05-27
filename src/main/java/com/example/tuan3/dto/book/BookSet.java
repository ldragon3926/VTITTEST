package com.example.tuan3.dto.book;

import com.example.tuan3.entity.Book;
import com.example.tuan3.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class BookSet {
    private Long id;
    @NotBlank(message = "Không được để mã trống")
    private String code;
    @NotBlank(message = "Không được để title trống")
    private String title;
    @NotBlank(message = "Không được để tác giả trống")
    private String authors;
    @NotBlank(message = "Không được để nhà xuất bản trống")
    private String publisher;
    @NotNull(message = "Không được để số trang trống")
    private Integer pageCount;
    @NotBlank(message = "Không được để kiểu in trống")
    private String printType;
    @NotBlank(message = "Không được để ngôn ngữ trống")
    private String language;
    @NotBlank(message = "Không được để mô tả trống")
    private String description;
    @NotNull(message = "Không được để số lượng trống")
    private Integer quantity;
    private boolean status;

    private Set<Long> idCategorys;

    public Book dto(Book book, List<Category> idCategory) {
        book.setId(this.getId());
        book.setCode(this.getCode());
        book.setTitle(this.getTitle());
        book.setAuthors(this.getAuthors());
        book.setPublisher(this.getPublisher());
        book.setPageCount(this.getPageCount());
        book.setPrintType(this.getPrintType());
        book.setLanguage(this.getLanguage());
        book.setDescription(this.getDescription());
        book.setQuantity(this.getQuantity());
        book.setStatus(this.isStatus());
        if (idCategory != null) {
            book.setCategory(new HashSet<>(idCategory));
        }
        return book;
    }


}
