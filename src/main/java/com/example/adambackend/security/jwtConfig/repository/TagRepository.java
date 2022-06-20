package com.example.adambackend.security.jwtConfig.repository;

import com.example.adambackend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByTagName(String tagName);
}
