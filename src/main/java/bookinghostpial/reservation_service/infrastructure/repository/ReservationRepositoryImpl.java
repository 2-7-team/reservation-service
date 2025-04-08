package bookinghostpial.reservation_service.infrastructure.repository;

import org.springframework.stereotype.Repository;

import bookinghostpial.reservation_service.domain.model.Reservation;
import bookinghostpial.reservation_service.domain.repository.ReservationRepository;
import bookinghostpial.reservation_service.infrastructure.repository.jpa.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

	private final ReservationJpaRepository reservationJpaRepository;

	@Override
	public Reservation save(Reservation reservation) {
		reservationJpaRepository.save(reservation);
	}
}
