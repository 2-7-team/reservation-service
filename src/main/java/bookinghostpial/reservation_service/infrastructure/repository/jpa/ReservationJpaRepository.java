package bookinghostpial.reservation_service.infrastructure.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import bookinghostpial.reservation_service.domain.model.Reservation;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
}
