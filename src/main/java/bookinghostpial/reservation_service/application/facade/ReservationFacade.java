package bookinghostpial.reservation_service.application.facade;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookinghospital.common_module.userInfo.UserDetails;
import bookinghostpial.reservation_service.application.service.ReservationService;
import bookinghostpial.reservation_service.application.service.ReservationSlotService;
import bookinghostpial.reservation_service.domain.model.Reservation;
import bookinghostpial.reservation_service.domain.model.ReservationSlot;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationFacade {
	private final ReservationSlotService reservationSlotService;
	private final ReservationService reservationService;

	@Transactional
	public void reserve(UUID hospitalId, LocalDate reservationDate, Integer reservationTime,
		UserDetails userInfo) {

		//예약 좌석 감소
		ReservationSlot reservationSlot = reservationSlotService.decreaseReservationSlot(hospitalId, reservationDate,
			reservationTime);

		//예약 생성
		reservationService.createReservation(reservationSlot.getId(), reservationDate, reservationTime,
			userInfo);

	}

	@Transactional
	public void updateReserve(UUID reservationId, LocalDate newReservationDate, Integer newReservationTime,
		UserDetails userInfo) {

		/*
		 *
		 * 그냥 reservation 찾은거로 slot 줄이고 새로 찾아서 업데이트 하면 되지 않나..... 근데 생성시에 그게 안됨...
		 * */

		//기존 예약 찾기
		Reservation reservation = reservationService.findReservation(reservationId);

		//기존 예약좌석 증가
		ReservationSlot slot = reservationSlotService.increaseReservationSlot(
			reservation.getReservationSlotId());

		//새 예약좌석 가능여부 확인 및 감소
		ReservationSlot newSlot = reservationSlotService.decreaseReservationSlot(slot.getHospitalId(),
			newReservationDate,
			newReservationTime);

		//기존 예약 업데이트								//도메인을 넘긴다? reservationService에 reservation은 괜찮지 않을까
		reservationService.updateReservation(newSlot.getId(), reservation, newReservationDate,
			newReservationTime, userInfo);

	}

	@Transactional
	public void cancelReservation(UUID reservationId, UserDetails userInfo) {

		//기존 예약 찾기
		Reservation reservation = reservationService.findReservation(reservationId);

		//기존 예약좌석 증가
		reservationSlotService.increaseReservationSlot(reservation.getReservationSlotId());

		//예약 취소 ( 고도화 시 예약 시간 전에만 취소 가능 )
		reservationService.deleteReservation(reservation, userInfo);
	}

}
