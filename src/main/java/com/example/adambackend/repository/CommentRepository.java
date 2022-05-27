package com.example.adambackend.repository;

import com.example.adambackend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select count(c) from Comment c join Account a on c.account.id= a.id join Product  p on c.product.id= p.id where a.id=?1 and p.id=?2")
    Integer countCommentByAccountIdAndProductId(Long idAccount,Long idProduct);
    @Query("select c from Comment  c join Account a on c.account.id= a.id join Product  p on c.product.id= p.id  where a.id=?1 and p.id=?2")
    List<Comment> findCommentByIdAccountAndIdProduct(Long idAccount, Long idProduct);

}
