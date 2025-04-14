package bookinghostpial.reservation_service.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import bookinghospital.common_module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_reservation")
public class Reservation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private UUID reservationSlotId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private LocalDate reservationDate;

	@Column(nullable = false)
	private Integer reservationTime;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

	@Builder(builderMethodName = "createReservationBuilder")
	private Reservation(UUID reservationSlotId, Long userId, ReservationStatus status, LocalDate reservationDate,
		Integer reservationTime) {
		this.reservationSlotId = reservationSlotId;
		this.userId = userId;
		this.status = status;
		this.reservationDate = reservationDate;
		this.reservationTime = reservationTime;
	}

	public void update(LocalDate reservationDate, Integer reservationTime, UUID reservationSlotId) {
		this.reservationSlotId = reservationSlotId;
		this.reservationDate = reservationDate;
		this.reservationTime = reservationTime;
	}
}
