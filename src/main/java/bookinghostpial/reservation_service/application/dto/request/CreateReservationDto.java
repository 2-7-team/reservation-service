package bookinghostpial.reservation_service.application.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateReservationDto {
	private UUID hospitalId;
	private LocalDate reservationDate;
	private Integer reservationTime;
}
