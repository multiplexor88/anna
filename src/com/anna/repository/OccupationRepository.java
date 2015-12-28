package com.anna.repository;

import com.anna.data.Occupation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupationRepository extends JpaRepository<Occupation,String>  {
    public List<Occupation> findOccupationByTypeLike(String id);
}
