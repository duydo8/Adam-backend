package com.example.adambackend.repository;

import com.example.adambackend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query(value = "select DISTINCT c2.id,c2.category_name,c2.status,c2.create_date,c2.category_parent_id " +
			"from categories c1, categories c2 where c2.id = c1.category_parent_id or c2.category_parent_id is null or " +
			"c1.category_parent_id is null and c2.status = 1 and c1.status = 1", nativeQuery = true)
	List<Category> findAllCategoryParent();

	@Query(value = "select * from categories where category_parent_id = ?1 and status = 1", nativeQuery = true)
	List<Category> findByCategoryParentId(int parentId);

	@Modifying
	@Transactional
	@Query(value = "update categories set status = 0 where id = ?1", nativeQuery = true)
	void updateCategoriesDeleted(Integer id);

	@Query(value = "select * from categories where status = 1 order by create_date desc", nativeQuery = true)
	List<Category> findAll();

	@Query(value = "select c from Category c where c.status = 1 and c.categoryName like concat('%',:name,'%') order by c.createDate desc")
	List<Category> findAll(@Param("name") String name);
}
