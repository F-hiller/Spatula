package com.ovg.spatula.repository;

import com.ovg.spatula.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}