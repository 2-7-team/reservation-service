package bookinghostpial.reservation_service.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import bookinghostpial.reservation_service.domain.model.Reservation;

public interface ReservationRepository {
	Reservation save(Reservation reservation);

	Page<Reservation> findAllByUserId(Pageable pageable);

	Optional<Reservation> findById(UUID reservationId);
}
