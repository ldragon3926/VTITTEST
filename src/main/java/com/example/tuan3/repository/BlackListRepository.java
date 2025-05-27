package com.example.tuan3.repository;

import com.example.tuan3.entity.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepository extends JpaRepository<TokenBlackList, String> , JpaSpecificationExecutor<TokenBlackList> {



    
}
