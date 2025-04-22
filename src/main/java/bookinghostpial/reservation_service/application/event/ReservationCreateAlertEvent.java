package bookinghostpial.reservation_service.application.event;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateAlertEvent {
	private UUID reservationId;
	private Long userId;
	private LocalDate reservationDate;
	private Integer reservationTime;

}
