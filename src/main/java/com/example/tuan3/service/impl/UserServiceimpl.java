package com.example.tuan3.service.impl;

import com.example.tuan3.entity.User;
import com.example.tuan3.exception.NotFoundExpection;
import com.example.tuan3.repository.UserRepository;
import com.example.tuan3.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> phanTrang(Integer page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return userRepository.findAll(pageable);
    }


    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getOne(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
@Transactional
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user, Long id) {
        User uFind = userRepository.findById(id).orElseThrow(() -> new NotFoundExpection("Không tìm thấy id ở vị trí id: "+id));
        user.setId(uFind.getId());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete( Long id) {
        userRepository.deleteById(id);
    }
}
