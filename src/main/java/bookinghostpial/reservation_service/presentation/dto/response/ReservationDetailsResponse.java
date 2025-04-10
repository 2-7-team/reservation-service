package bookinghostpial.reservation_service.presentation.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import bookinghostpial.reservation_service.domain.model.Reservation;
import bookinghostpial.reservation_service.domain.model.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationDetailsResponse {
	private UUID id;
	private Long userId;
	private LocalDate reservationDate;
	private Integer reservationTime;
	private ReservationStatus status;

	@Builder
	public ReservationDetailsResponse(Reservation reservation) {
		this.id = reservation.getId();
		this.userId = reservation.getUserId();
		this.reservationDate = reservation.getReservationDate();
		this.reservationTime = reservation.getReservationTime();
		this.status = reservation.getStatus();
	}
}
