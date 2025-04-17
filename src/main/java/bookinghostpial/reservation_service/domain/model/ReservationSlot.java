package bookinghostpial.reservation_service.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import bookinghostpial.reservation_service.domain.exception.NoLeftSeatException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reservation_Slot"
	, uniqueConstraints = {
	@UniqueConstraint(
		name = "constraintsName",
		columnNames = {"hospitalId", "reservationDate", "reservationTime"}
	)
}

)
public class ReservationSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private UUID hospitalId;

	@Column(nullable = false)
	private Integer leftSeat;

	@Column(nullable = false)
	private LocalDate reservationDate;

	@Column(nullable = false)
	private Integer reservationTime;

	@Builder(builderMethodName = "createReservationSlotBuilder")
	public ReservationSlot(UUID hospitalId, Integer leftSeat, LocalDate reservationDate, Integer reservationTime) {
		this.hospitalId = hospitalId;
		this.leftSeat = leftSeat;
		this.reservationDate = reservationDate;
		this.reservationTime = reservationTime;
	}

	public void decrease() {
		if (leftSeat > 0) {
			leftSeat--;
		} else {
			throw new NoLeftSeatException("예약 가능한 좌석이 없습니다.");
		}
	}

	public void increase() {
		leftSeat++;
	}

	public void changeSeat(Integer updateLeftSeat) {
		this.leftSeat = updateLeftSeat;
	}
}
