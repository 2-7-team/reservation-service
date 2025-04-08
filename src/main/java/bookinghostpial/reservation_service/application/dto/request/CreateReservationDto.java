package bookinghostpial.reservation_service.application.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateReservationDto {
	private UUID hospitalId;
	private LocalDateTime reservationTime;
}
