package bookinghostpial.reservation_service.application.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookinghostpial.reservation_service.domain.exception.CannotUpdateSeatException;
import bookinghostpial.reservation_service.domain.exception.NotExistReservationException;
import bookinghostpial.reservation_service.domain.exception.NotExistReservationSlotException;
import bookinghostpial.reservation_service.domain.model.ReservationSlot;
import bookinghostpial.reservation_service.domain.repository.ReservationSlotRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationSlotService {

	private final ReservationSlotRepository reservationSlotRepository;

	public ReservationSlot findSlotForUpdate(UUID reservationSlotId) {
		return reservationSlotRepository.findByIdForUpdate(reservationSlotId)        //리팩토링 필수
			.orElseThrow(() -> new NotExistReservationException("예약 정보가 존재하지 않습니다"));   //에러코드로 관리
	}

	@Transactional
	public ReservationSlot decreaseReservationSlot(UUID hospitalId, LocalDate reservationDate,
		Integer reservationTime) {

		ReservationSlot reservationSlot = findReservationSlot(hospitalId, reservationDate, reservationTime);
		reservationSlot.decrease();

		return reservationSlot;
	}

	@Transactional
	public ReservationSlot increaseReservationSlot(UUID hospitalId, LocalDate reservationDate,
		Integer reservationTime) {

		ReservationSlot reservationSlot = findReservationSlot(hospitalId, reservationDate, reservationTime);
		reservationSlot.increase();

		return reservationSlot;
	}

	private ReservationSlot findReservationSlot(UUID hospitalId, LocalDate reservationDate, Integer reservationTime) {
		ReservationSlot reservationSlot = reservationSlotRepository.findByReservationInfo(hospitalId, reservationDate,
			reservationTime).orElseThrow(() -> new NotExistReservationSlotException("해당 시간대 예약이 현재 가능하지 않습니다."));

		return reservationSlot;

	}

	@Transactional
	public void updateLeftSeat(UUID hospitalId, Integer reservationTime, LocalDate reservationDate,
		Integer updateLeftSeat) {
		ReservationSlot reservationSlot = findReservationSlot(hospitalId, reservationDate, reservationTime);
		if (reservationSlot.getLeftSeat() < updateLeftSeat) {
			throw new CannotUpdateSeatException("현재 예약된 수보다 적게는 수정할 수 없습니다.");
		}
		reservationSlot.changeSeat(updateLeftSeat);
	}
}
