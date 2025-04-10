package bookinghostpial.reservation_service.presentation.dto.request;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class UpdateReservationRequest {
	private LocalDate reservationDate;
	private Integer reservationTime;
}
