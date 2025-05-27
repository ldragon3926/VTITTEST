package com.example.tuan3.dto.permission;

import com.example.tuan3.entity.Permission;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionSet {
    private Long id;
    @NotBlank(message = "Không được để mã trống")
    private String code;
    @NotBlank(message = "Không được để tên trống")
    private String name;
    @NotBlank(message = "Không được để mô tả trống")
    private String description;
    private boolean status;

    public Permission dto(Permission permission) {
        permission.setId(this.getId());
        permission.setCode(this.getCode());
        permission.setName(this.getName());
        permission.setDescription(this.getDescription());
        permission.setStatus(isStatus());
        return permission;
    }
}
