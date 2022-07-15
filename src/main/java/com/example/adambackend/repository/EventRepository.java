package com.example.adambackend.repository;

import com.example.adambackend.entities.Event;
import com.example.adambackend.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "select * from events where event_name like '%?1%'",nativeQuery = true)
    List<Event> findByName(String name);

}
