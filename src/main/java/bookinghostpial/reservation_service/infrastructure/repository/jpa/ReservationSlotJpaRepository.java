package bookinghostpial.reservation_service.infrastructure.repository.jpa;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import bookinghostpial.reservation_service.domain.model.ReservationSlot;
import jakarta.persistence.LockModeType;

public interface ReservationSlotJpaRepository extends JpaRepository<ReservationSlot, UUID> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<ReservationSlot> findByHospitalIdAndReservationDateAndReservationTime(UUID hospitalId,
		LocalDate reservationDate, Integer reservationTime);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<ReservationSlot> findById(UUID reservationSlotId);
}
