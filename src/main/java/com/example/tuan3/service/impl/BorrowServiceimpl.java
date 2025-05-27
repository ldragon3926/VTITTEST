package com.example.tuan3.service.impl;

import com.example.tuan3.dto.borrow.BorrowDTO;
import com.example.tuan3.entity.Borrows;
import com.example.tuan3.exception.NotFoundExpection;
import com.example.tuan3.repository.BorrowRepository;
import com.example.tuan3.service.BorrowService;
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
public class BorrowServiceimpl implements BorrowService {
    @Autowired
    BorrowRepository borrowRepository;


    @Override
    public List<Borrows> findAll() {
        return borrowRepository.findAll();
    }

    @Override
    public List<BorrowDTO> getAll() {
        return borrowRepository.getAll();
    }
    @Override
    public Page<BorrowDTO> phanTrang(Integer page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, 3, sort);
        return borrowRepository.getPhanTrang(pageable);
    }


    @Override
    public Optional<Borrows> findById(Long id) {
        return borrowRepository.findById(id);
    }

    @Override
    public Borrows getOne(Long id) {
        return borrowRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Borrows add(Borrows borrows) {
        return borrowRepository.save(borrows);
    }

    @Override
    @Transactional
    public Borrows update(Borrows borrows, Long id) {
        Borrows bFind = borrowRepository.findById(id).orElseThrow(() -> new NotFoundExpection("Không tìm thấy id ở vị trí id: "+id));
        borrows.setId(bFind.getId());
        return borrowRepository.save(borrows);
    }

    @Override
    @Transactional
    public void delete( Long id) {
        borrowRepository.deleteById(id);
    }
}
