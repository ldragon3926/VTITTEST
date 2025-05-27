package com.example.tuan3.dto.category;

import com.example.tuan3.entity.Category;
import com.example.tuan3.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySet {
    private Long id;
    @NotBlank(message = "Không được để tên trống")
    private String name;
    @NotBlank(message = "Không được để mô tả trống")
    private String description;
    private boolean status;

    public Category dto(Category category) {
        category.setId(this.getId());
        category.setName(this.getName());
        category.setDescription(this.getDescription());
        category.setStatus(this.isStatus());

        return category;
    }
}
