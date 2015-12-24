package com.anna.repository;

import com.anna.data.Hobby;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, String>  {
    List<Hobby> findHobbyByIdLike(String id);
}
