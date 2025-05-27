package com.example.tuan3.dto.user;

import com.example.tuan3.entity.Role;
import com.example.tuan3.entity.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserSet {
    private Long id;
    @NotBlank(message = "Không được để CCCD trống")
    private String identityNumber;
    @NotBlank(message = "Không được để username trống")
    private String username;
    @NotBlank(message = "Không được để password trống")
    private String password;
    @NotBlank(message = "Không được để họ và tên trống")
    private String fullname;

    @NotNull(message = "Không được để tuổi trống")
    @Max(150)
    @Min(1)
    private Integer age;

    @NotNull(message = "Không được để ngày sinh trống")
    private LocalDate birthday;
    @NotBlank(message = "Không được để địa trỉ trống")
    private String address;
    private boolean status;

    private Set<Long> idRoles;

    public User dto(User user, List<Role> idRolesfromDB) {
        user.setId(this.getId());
        user.setIdentityNumber(this.getIdentityNumber());
        user.setUsername(this.getUsername());
        user.setPassword(this.getPassword());
        user.setFullname(this.getFullname());
        user.setAge(this.getAge());
        user.setBirthday(this.getBirthday());
        user.setAddress(this.getAddress());
        user.setStatus(isStatus());
        if(idRolesfromDB != null ){
        user.setRoles(new HashSet<>(idRolesfromDB));
        }
        return user;
    }
}
