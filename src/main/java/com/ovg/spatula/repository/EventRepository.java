package com.ovg.spatula.repository;

import com.ovg.spatula.entity.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

  @Query(value = "SELECT e.* FROM event e JOIN location l ON e.location_id = l.id " +
      "WHERE ST_DWithin(l.location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography, :distance)", nativeQuery = true)
  List<Event> findEventsWithinDistance(@Param("lng") double lng,
      @Param("lat") double lat,
      @Param("distance") double distance);
}