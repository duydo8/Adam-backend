package com.example.adambackend.repository;

import com.example.adambackend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

	Optional<Tag> findByTagName(String tagName);

	@Transactional
	@Modifying
	@Query(value = "update tags set status  = 0 where id = ?1", nativeQuery = true)
	void updateDeletedByTagId(Integer productId);

	@Query(value = "select * from tags where status = 1 order by create_date desc", nativeQuery = true)
	List<Tag> findAll();

	@Query(value = "select t from Tag t where t.status = 1 and (:name is null or t.tagName like concat('%',:name,'%')) order by t.createDate desc")
	List<Tag> findAll(@Param("name") String name);

	@Query("select t from Tag t where t.id = ?1 and t.status = 1")
	Optional<Tag> findById(Integer id);
}
