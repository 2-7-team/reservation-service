package bookinghostpial.reservation_service.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class HospitalInfoResponse {
	private UUID hospitalId;
	private String name;
	private String address;
	private String number;
	private LocalDateTime open_hour;
	private LocalDateTime close_hour;
	private String description;
}
