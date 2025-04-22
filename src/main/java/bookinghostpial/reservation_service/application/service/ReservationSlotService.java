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
	public ReservationSlot increaseReservationSlot(UUID reservationSlotId) {

		ReservationSlot reservationSlot = findSlotForUpdate(reservationSlotId);
		reservationSlot.increase();

		return reservationSlot;
	}

	private ReservationSlot findReservationSlot(UUID hospitalId, LocalDate reservationDate, Integer reservationTime) {
		ReservationSlot reservationSlot = reservationSlotRepository.findByReservationInfo(hospitalId, reservationDate,
			reservationTime).orElseThrow(() -> new NotExistReservationSlotException("해당 시간대 예약이 현재 가능하지 않습니다."));

		return reservationSlot;

	}

	@Transactional
	public void updateLeftSeat(UUID hospitalId, Integer reservationTime,
		Integer updateLeftSeat) {

		LocalDate reservationDate = LocalDate.now();    // 변경 시점의 날짜 ( 오늘 ) ex) 오늘 오후 3시면은 이미 생성된 예약테이블 좌석 변경 시도. 오전 12시 이후 오전 6시 이전이면 테이블 존재 안하므로 error

		ReservationSlot reservationSlot = findReservationSlot(hospitalId, reservationDate,
			reservationTime);    //다른 메소드로 변경할 수 있을 듯
		if (reservationSlot.getLeftSeat() < updateLeftSeat) {
			throw new CannotUpdateSeatException("현재 예약된 수보다 적게는 수정할 수 없습니다.");
		}
		reservationSlot.changeSeat(updateLeftSeat);
	}
}
