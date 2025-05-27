package com.example.tuan3.service;

import com.example.tuan3.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Page<User> phanTrang(Integer page);
    Optional<User> findById(Long id);
    User add (User user);
    User update (User user, Long id);
    void delete( Long id);
    User getOne(Long id);
}
