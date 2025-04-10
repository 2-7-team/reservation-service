package bookinghostpial.reservation_service.infrastructure.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import bookinghostpial.reservation_service.domain.model.ReservationSlot;
import bookinghostpial.reservation_service.domain.repository.ReservationSlotRepository;
import bookinghostpial.reservation_service.infrastructure.repository.jpa.ReservationSlotJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationSlotRepositoryImpl implements ReservationSlotRepository {

	private final ReservationSlotJpaRepository reservationSlotJpaRepository;

	@Override
	public ReservationSlot findById(Long id) {
		return reservationSlotJpaRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("reservation slot not found"));
	}

	@Override
	public ReservationSlot save(ReservationSlot reservationSlot) {
		return reservationSlotJpaRepository.save(reservationSlot);
	}

	@Override
	public Optional<ReservationSlot> findByReservationInfo(UUID hospitalId, LocalDate reservationDate,
		Integer reservationTime) {
		return reservationSlotJpaRepository.findByHospitalIdAndReservationDateAndReservationTime(hospitalId,
			reservationDate, reservationTime);
	}

}
