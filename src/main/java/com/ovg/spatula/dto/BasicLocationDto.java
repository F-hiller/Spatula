package com.ovg.spatula.dto;

import com.ovg.spatula.entity.Location;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BasicLocationDto {

  private String name;
  private double lng;
  private double lat;
  private String address;

  public BasicLocationDto(Location location) {
    this.name = location.getName();
    this.lng = location.getLocation().getX();
    this.lat = location.getLocation().getY();
    this.address = location.getAddress();
  }
}
