package com.example.alottoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.alottoi.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
  
}