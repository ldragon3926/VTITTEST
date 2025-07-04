package com.example.tuan3.controller;

import com.example.tuan3.dto.user.UserSet;
import com.example.tuan3.entity.Role;
import com.example.tuan3.entity.User;
import com.example.tuan3.repository.RoleRepository;
import com.example.tuan3.repository.UserRepository;
import com.example.tuan3.response.ResponseUltils;
import com.example.tuan3.service.UserService;
import com.example.tuan3.service.Specifications.UserSpecifications;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/library/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/export-excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=user.xls";
        response.setHeader(headerKey, headerValue);
        List<User> users = userRepository.findAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Users");
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "CCCD", "Username", "Password", "Họ và tên", "Tuổi", "Ngày sinh", "Địa chỉ"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowCount = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getIdentityNumber());
            row.createCell(2).setCellValue(user.getUsername());
            row.createCell(3).setCellValue(user.getPassword());
            row.createCell(4).setCellValue(user.getFullname());
            row.createCell(5).setCellValue(user.getAge());
            row.createCell(6).setCellValue(user.getBirthday());
            row.createCell(7).setCellValue(user.getAddress());
        }


        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @GetMapping
    public ResponseEntity<?> searchUser(
            @RequestBody(required = false) User user,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<User> userSpecification = Specification.where(UserSpecifications.hasUsername(user.getUsername()))
                .and(UserSpecifications.hasFullName(user.getFullname()))
                .and(UserSpecifications.hasAge(user.getAge()))
                .and(UserSpecifications.hasAddress(user.getAddress()));
        return ResponseUltils.success(userRepository.findAll(userSpecification, pageable), "ROLE_VIEW_USER", "Tìm danh sách user thành công");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseUltils.success(userService.findAll(), "ROLE_VIEW_USER", "Lấy tất cả danh sách user thành công");
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            return ResponseUltils.error("error.user.not_found", "Người dùng không tồn tại");
        }
        return ResponseUltils.success(userService.getOne(id), "ROLE_VIEW_USER", "Lấy user thành công");
    }

    @PostMapping("/create")
    public ResponseEntity<?> add(@RequestBody @Valid UserSet userSet, BindingResult bindingResult) {
        User newUser = new User();
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        List<Role> roles = roleRepository.findAllById(userSet.getIdRoles());
        User user = userSet.dto(newUser, roles);
        return ResponseUltils.success(userService.add(user), "ROLE_CREATE_USER", "Thêm user thành công");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid UserSet userSet, BindingResult bindingResult, @PathVariable(name = "id") Long id) {
        User newUser = new User();
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
            return ResponseUltils.error("error.validation", errors);
        }
        List<Role> roles = roleRepository.findAllById(userSet.getIdRoles());

        User user = userSet.dto(newUser, roles);
        return ResponseUltils.success(userService.update(user, id), "ROLE_UPDATE_USER", "Update user thành công");
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            return ResponseUltils.error("error.user.not_found", "Người dùng không tồn tại");
        }
        User userD = userRepository.findById(id).get();
        userD.setStatus(false);
        userService.update(userD, id);
        return ResponseUltils.success(null, "ROLE_DELETE_USER", "Xóa user thành công");
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<?> delete(@RequestParam(name = "id") Long id){
//      Optional<User> user = userService.findById(id);
//        if (user.isEmpty()) {
//            return ResponseUltils.error("error.user.not_found", "Người dùng không tồn tại");
//        }
//        userService.delete(id);
//        return ResponseUltils.success(null,"ROLE_DELETE_USER","Xóa user thành công");
//    }
}
