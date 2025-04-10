package bookinghostpial.reservation_service.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import bookinghostpial.reservation_service.domain.model.Reservation;
import bookinghostpial.reservation_service.domain.repository.ReservationRepository;
import bookinghostpial.reservation_service.infrastructure.repository.jpa.ReservationJpaRepository;
import bookinghostpial.reservation_service.infrastructure.repository.querydsl.ReservationQueryDslRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

	private final ReservationJpaRepository reservationJpaRepository;
	private final ReservationQueryDslRepository reservationQueryDslRepository;

	@Override
	public Reservation save(Reservation reservation) {
		return reservationJpaRepository.save(reservation);
	}

	@Override
	public Page<Reservation> findAllByUserId(Pageable pageable) {
		return reservationQueryDslRepository.findAllByUserId(1L, pageable); //임시
	}

	@Override
	public Optional<Reservation> findById(UUID reservationId) {
		return reservationJpaRepository.findById(reservationId);
	}

}
