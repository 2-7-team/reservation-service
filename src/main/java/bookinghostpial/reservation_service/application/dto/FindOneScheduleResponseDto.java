package bookinghostpial.reservation_service.application.dto;

import java.time.LocalTime;

import lombok.Getter;

@Getter
public class FindOneScheduleResponseDto {
	private String name;
	private LocalTime time;
	private Integer capacity;
}
