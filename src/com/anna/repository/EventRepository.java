/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.repository;

import com.anna.data.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author igor
 */
public interface EventRepository extends JpaRepository<Event, Long>
{
    List<Event> findByDate(String date);
    List<Event> findByNameLike(String startSymbols);
    List<Event> findByDateLike(String startDateString);
    List<Event> findByDateLikeAndNameLike(String startDateString, String startSymbols);
}
