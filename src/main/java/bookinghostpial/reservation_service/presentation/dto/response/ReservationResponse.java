package bookinghostpial.reservation_service.presentation.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import bookinghostpial.reservation_service.domain.model.Reservation;
import bookinghostpial.reservation_service.domain.model.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationResponse {
	private UUID id;
	private ReservationStatus status;
	private LocalDate reservationDate;
	private Integer reservationTime;
	private Long userId;

	@Builder
	public ReservationResponse(Reservation reservation) {
		this.id = reservation.getId();
		this.reservationDate = reservation.getReservationDate();
		this.reservationTime = reservation.getReservationTime();
		this.status = reservation.getStatus();
		this.userId = reservation.getUserId();
	}
}
