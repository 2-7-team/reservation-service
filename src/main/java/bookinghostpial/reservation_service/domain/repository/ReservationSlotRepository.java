package bookinghostpial.reservation_service.domain.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import bookinghostpial.reservation_service.domain.model.ReservationSlot;

public interface ReservationSlotRepository {
	ReservationSlot findById(Long id);

	ReservationSlot save(ReservationSlot reservationSlot);

	Optional<ReservationSlot> findByReservationInfo(UUID hospitalId, LocalDate reservationDate,
		Integer reservationTime);
}
