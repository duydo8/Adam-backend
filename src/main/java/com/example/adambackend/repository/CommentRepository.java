package com.example.adambackend.repository;

import com.example.adambackend.entities.Comment;
import com.example.adambackend.enums.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select count(c) from Comment c join Account a on c.account.id= a.id join Product  p on c.product.id= p.id where a.id=?1 and p.id=?2")
    Integer countCommentByAccountIdAndProductId(Long idAccount,Long idProduct);
    @Query("select c from Comment  c join Account a on c.account.id= a.id join Product  p on c.product.id= p.id  where a.id=?1 and p.id=?2")
    List<Comment> findCommentByIdAccountAndIdProduct(Long idAccount, Long idProduct);


    @Transactional
    @Modifying
    @Query(value = "insert into Comment(content,time_create,product_id,account_id,status) values (?1,?2,?3,?4,?5)",nativeQuery = true)
    Comment createAccountwithAccountIdAndProductId(String content, LocalDateTime localDateTime, Long productId, Long accountId, CommentStatus commentStatus);

    @Query(value = "select c from comments c join products p on c.product_id=p.id where p.id=?1 and c.status='active'",nativeQuery = true)
    List<Comment>findAllCommentByProductIdAndStatusIsActive(Long productId);
}
