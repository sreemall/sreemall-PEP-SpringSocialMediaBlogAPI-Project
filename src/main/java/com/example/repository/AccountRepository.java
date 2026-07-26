package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
     Account findByUsername(String username);
     boolean existByUsername (String username);
}
