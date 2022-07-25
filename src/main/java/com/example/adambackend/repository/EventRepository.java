package com.example.adambackend.repository;

import com.example.adambackend.entities.Event;
import com.example.adambackend.entities.Material;
import com.example.adambackend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "select * from events where event_name like '%?1%'",nativeQuery = true)
    List<Event> findByName(String name);
    @Query(value = "select c from Event c where c.isActive=true and c.isDelete=false and  c.eventName like concat('%',:name,'%') ")
    List<Event> findAll(@Param("name")  String name);

}
