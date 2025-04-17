package bookinghostpial.reservation_service.application.facade;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookinghospital.common_module.userInfo.UserDetails;
import bookinghostpial.reservation_service.application.service.ReservationService;
import bookinghostpial.reservation_service.application.service.ReservationSlotService;
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

		ReservationSlot reservationSlot = reservationSlotService.decreaseReservationSlot(hospitalId, reservationDate,
			reservationTime);

		reservationService.createReservation(reservationSlot.getId(), reservationDate, reservationTime,
			userInfo);

	}

	@Transactional
	public void updateReserve(UUID reservationId, LocalDate newReservationDate, Integer newReservationTime,
		UserDetails userInfo) {

		ReservationSlot slot = reservationSlotService.findSlotForUpdate(reservationId);

		reservationSlotService.increaseReservationSlot(slot.getHospitalId(), slot.getReservationDate(),
			slot.getReservationTime());

		ReservationSlot newSlot = reservationSlotService.decreaseReservationSlot(slot.getHospitalId(),
			newReservationDate,
			newReservationTime);

		reservationService.updateReservation(slot.getId(), newSlot.getId(), reservationId, newReservationDate,
			newReservationTime, userInfo);

	}

	@Transactional
	public void cancelReservation(UUID reservationId, UserDetails userInfo) {

		ReservationSlot slot = reservationSlotService.findSlotForUpdate(reservationId);

		reservationSlotService.increaseReservationSlot(slot.getHospitalId(), slot.getReservationDate(),
			slot.getReservationTime());

		reservationService.deleteReservation(slot.getId(), reservationId, userInfo);
	}

}
