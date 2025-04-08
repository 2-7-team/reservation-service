package bookinghostpial.reservation_service.application.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationListResponse {
	List<ReservationResponse> content;
}
