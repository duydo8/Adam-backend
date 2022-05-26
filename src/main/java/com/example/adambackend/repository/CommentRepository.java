package com.example.adambackend.repository;

import com.example.adambackend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select count(c) from Comment c join Account a on c.account.id= a.id join Product  p on c.product.id= p.id")
    Integer countCommentByAccountIdAndProductId();
}
