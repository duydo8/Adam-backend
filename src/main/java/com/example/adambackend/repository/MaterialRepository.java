package com.example.adambackend.repository;

import com.example.adambackend.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
	@Transactional
	@Modifying
	@Query(value = "update materials set status = 0 where id = ?1", nativeQuery = true)
	void updateDeleteById(Integer id);

	@Query(value = "select * from materials where status = 1", nativeQuery = true)
	List<Material> findAll();

	@Query(value = "select c from Material c where c.status = 1 and (:name is null or c.materialName like concat('%',:name,'%')) ")
	List<Material> findAll(@Param("name") String name);

	@Query("select m from Material m where m.status = 1 and m.materialName = ?1")
	Material findByName(String name);
}
