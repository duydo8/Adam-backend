package com.example.adambackend.repository;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
