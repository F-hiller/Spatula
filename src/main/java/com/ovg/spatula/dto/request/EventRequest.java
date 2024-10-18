package com.ovg.spatula.dto.request;

import com.ovg.spatula.dto.BasicLocationDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventRequest {

  private String name;
  private LocalDateTime eventDateTime;
  private int totalSeats;
  private BasicLocationDto basicLocationDto;
}
