package bookinghostpial.reservation_service.domain.repository;

import bookinghostpial.reservation_service.domain.model.Reservation;

public interface ReservationRepository {
	Reservation save(Reservation reservation);
}
