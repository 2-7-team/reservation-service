package bookinghostpial.reservation_service.presentation.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateReservationRequest {
	private UUID hospitalId;
	private LocalDate reservationDate;
	private Integer reservationTime;
}
