package bookinghostpial.reservation_service.application.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public class HospitalWithSchedulesResponse {
	private UUID id; // 병원 식별 id
	private String name;
	private String address;
	private String phone;
	private String description;
	private LocalTime openHour;
	private LocalTime closeHour;
	private List<FindOneScheduleResponseDto> schedules = new ArrayList<>();
	protected LocalDateTime createdAt;
	protected Long createdBy;
}
