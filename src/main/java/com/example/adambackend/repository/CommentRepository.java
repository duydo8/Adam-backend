package com.example.adambackend.repository;

import com.example.adambackend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select count(c.id) from Comment c join Account a on c.account.id = a.id join Product p on c.product.id = p.id " +
            "where a.id = ?1 and p.id = ?2")
    Integer countCommentByAccountIdAndProductId(Integer idAccount, Integer idProduct);

    @Query("select c from Comment  c join Account a on c.account.id = a.id join Product  p on c.product.id = p.id " +
            " where a.id = ?1 and p.id = ?2")
    List<Comment> findCommentByIdAccountAndIdProduct(Integer idAccount, Integer idProduct);

    @Transactional
    @Modifying
    @Query(value = "insert into Comment(content, create_date, product_id, account_id, status, vote) " +
            "values (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    Comment createCommentwithAccountIdAndProductId(String content, LocalDateTime localDateTime, Integer productId,
                                                   Integer accountId, Integer commentStatus, int vote);

    @Query(value = "select c from comments c join products p on c.product_id = p.id where p.id = ?1 and p.status = 1 " +
            "and c.commentStatus= 1 ", nativeQuery = true)
    List<Comment> findAllCommentByProductIdAndStatusIsActive(Integer productId);

    @Query(value = "select top(10) c from comments c join products p on c.product_id = p.id where p.id = ?1 and c.comment_status == 1 order by c.create_date", nativeQuery = true)
    List<Comment> findTop10CommentByProductId(Integer productId);

    @Query(value = "select count(*) from comments c join product p on c.product_id = p.id where p.id = ?1", nativeQuery = true)
    Integer countCommentByProduct(Integer productId);

    @Transactional
    @Modifying
    @Query(value = "delete from comments where product_id = ?1", nativeQuery = true)
    void deleteByProductId(Integer productId);

    @Transactional
    @Modifying
    @Query(value = "update Comment c set c.commentStatus = 0 where c.id = ?1")
    void updateDeletedById(Integer id);

    @Transactional
    @Modifying
    @Query(value = "update Comment c set c.commentStatus = 0 where c.commentParentId = ?1")
    void updateDeletedByParenId(Integer parentId);
}
