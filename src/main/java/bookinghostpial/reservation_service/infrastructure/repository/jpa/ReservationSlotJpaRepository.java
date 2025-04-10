package bookinghostpial.reservation_service.infrastructure.repository.jpa;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import bookinghostpial.reservation_service.domain.model.ReservationSlot;

public interface ReservationSlotJpaRepository extends JpaRepository<ReservationSlot, Long> {

	Optional<ReservationSlot> findByHospitalIdAndReservationDateAndReservationTime(UUID hospitalId,
		LocalDate reservationDate, Integer reservationTime);
}
