package bookinghostpial.reservation_service.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import bookinghostpial.reservation_service.domain.model.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationDetailsResponse {
	private UUID id;
	private UUID hospitalId;
	private Long userId;
	private LocalDateTime reservationTime;
	private ReservationStatus status;
}
