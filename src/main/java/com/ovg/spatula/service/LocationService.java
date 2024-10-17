package com.ovg.spatula.service;

import com.ovg.spatula.dto.BasicLocationDto;
import com.ovg.spatula.entity.Location;
import com.ovg.spatula.repository.LocationRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

  private final LocationRepository locationRepository;

  public LocationService(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  public Location saveLocation(BasicLocationDto basicLocationDto) {
    String name = basicLocationDto.getName();
    String address = basicLocationDto.getAddress();
    double lng = basicLocationDto.getLng();
    double lat = basicLocationDto.getLat();
    Point locationPoint = new GeometryFactory().createPoint(new Coordinate(lng, lat));
    Location location = Location.builder()
        .name(name)
        .address(address)
        .location(locationPoint)
        .build();
    return locationRepository.save(location);
  }
}
