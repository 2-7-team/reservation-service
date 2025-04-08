package bookinghostpial.reservation_service.application.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponse {
	private UUID id;
	private UUID hospitalId;
	private Long userId;
}
