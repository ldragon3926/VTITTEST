package com.example.tuan3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class TokenBlackList {
    @Id
    private String token;

    private Instant expiryDate;
}