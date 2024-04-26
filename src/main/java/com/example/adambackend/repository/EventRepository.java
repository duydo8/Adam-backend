package com.example.adambackend.repository;

import com.example.adambackend.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
	@Query(value = "select e from Event e where e.status = 1 and (:name is null or e.eventName like concat('%', :name ,'%'))")
	List<Event> findAll(@Param("name") String name);

	@Query(value = "select * from events  where  " +
			"DATEDIFF(CURRENT_DATE(),start_time)>0 " +
			"and DATEDIFF(end_time,CURRENT_DATE())>0 and status = 1 ", nativeQuery = true)
	List<Event> findAllByTime();

	@Modifying
	@Transactional
	@Query(value = "update events set status = 0 where id = ?1", nativeQuery = true)
	void updateEventDeleted(Integer id);

	@Query("select e from Event e where e.status = 1 and e.id = ?1")
	Optional<Event> findById(Integer id);

	@Query("select e from Event e where e.status = 1 and e.id = ?1")
	Event findExistById(Integer id);
}
