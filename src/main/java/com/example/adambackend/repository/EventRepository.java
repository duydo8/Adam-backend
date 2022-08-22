package com.example.adambackend.repository;

import com.example.adambackend.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "select * from events where event_name like '%?1%'", nativeQuery = true)
    List<Event> findByName(String name);

    @Query(value = "select c from Event c where c.isActive=true and c.isDelete=false and  c.eventName like concat('%',:name,'%') ")
    List<Event> findAll(@Param("name") String name);

    @Query(value = "select c from Event c where c.isActive=true and c.isDelete=false order by c.createDate")
    List<Event> findAll();

    @Query(value = "select * from events  where  " +
            "DATEDIFF(CURRENT_DATE(),start_time)>0 " +
            "and DATEDIFF(end_time,CURRENT_DATE())>0 and is_active=1 and is_deleted=0 ", nativeQuery = true)
    List<Event> findAllByTime();

    @Modifying
    @Transactional
    @Query(value = "update events set is_deleted=1 , is_active=0 where id=?1", nativeQuery = true)
    void updateEventDeleted(Integer id);

}
