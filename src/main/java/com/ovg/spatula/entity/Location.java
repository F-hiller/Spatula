package com.ovg.spatula.entity;

import com.ovg.spatula.dto.BasicLocationDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String address;

  @Column(columnDefinition = "Geometry(Point, 4326)")
  private Point location;

  public Location(BasicLocationDto basicLocationDto, Point point) {
    this.name = (basicLocationDto.getName());
    this.address = (basicLocationDto.getAddress());
    this.location = (point);
  }
}
