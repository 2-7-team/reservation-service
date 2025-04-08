package bookinghostpial.reservation_service.application.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateReservationDto {
	private LocalDateTime reservationTime;
}
