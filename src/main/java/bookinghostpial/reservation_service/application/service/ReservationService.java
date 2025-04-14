package bookinghostpial.reservation_service.application.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookinghostpial.reservation_service.application.client.ReservationClient;
import bookinghostpial.reservation_service.domain.exception.NotExistReservationException;
import bookinghostpial.reservation_service.domain.exception.ReservationAlreadyDeletedException;
import bookinghostpial.reservation_service.domain.model.Reservation;
import bookinghostpial.reservation_service.domain.model.ReservationSlot;
import bookinghostpial.reservation_service.domain.model.ReservationStatus;
import bookinghostpial.reservation_service.domain.repository.ReservationRepository;
import bookinghostpial.reservation_service.domain.repository.ReservationSlotRepository;
import bookinghostpial.reservation_service.presentation.dto.response.ReservationDetailsResponse;
import bookinghostpial.reservation_service.presentation.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final ReservationSlotRepository reservationSlotRepository;
	private final ReservationClient reservationClient;
	private static long USER_ID = 1L;

	@Transactional
	public void createReservation(UUID hospitalId, LocalDate reservationDate, Integer reservationTime) {

		/*
		 * RESERVATION SLOT 존재하는지 확인 후 반환.
		 * */
		ReservationSlot reservationSlot = checkReservationSlot(hospitalId,    //추후 도메인 이벤트 방식으로 변경 고려
			reservationDate,
			reservationTime);

		reservationSlot.decrease();                    //동시성 처리 필요

		Reservation reservation = Reservation.createReservationBuilder()
			.userId(USER_ID)    //임시
			.reservationSlotId(reservationSlot.getId())
			.reservationDate(reservationDate)
			.reservationTime(reservationTime)
			.status(ReservationStatus.SCHEDULED)
			.build();

		reservationRepository.save(reservation);
	}

	public Page<ReservationResponse> getReservationList(
		//UserInfo userInfo
		@PageableDefault Pageable pageable
	) {
		Page<Reservation> allByUserId = reservationRepository.findAllByUserId(pageable);

		return allByUserId.map(
			reservation -> ReservationResponse.builder()
				.reservation(reservation).build());
	}

	public ReservationDetailsResponse getReservationDetails(UUID reservationId) {

		Reservation reservation = findReservation(reservationId);
		isDeleted(reservation);
		return ReservationDetailsResponse
			.builder()
			.reservation(reservation)
			.build();
	}

	@Transactional
	public void updateReservation(UUID reservationId, LocalDate reservationDate, Integer reservationTime) {
		Reservation reservation = findReservation(reservationId);

		ReservationSlot slot = findSlotForUpdate(reservation.getReservationSlotId());
/*		유저 정보 권한 검증
		if (!userInfo.getRole.equals("ADMIN") && reservation.getUserId() != userInfo) {
			throw new ReservationPermissionDenied("접근 권한이 없습니다");
		}*/

		slot.increase();

		ReservationSlot newReservationSlot = checkReservationSlot(slot.getHospitalId(),    //추후 도메인 이벤트 방식으로 변경 고려
			reservationDate,
			reservationTime);

		newReservationSlot.decrease();

		reservation.update(reservationDate, reservationTime, newReservationSlot.getId());
	}

	@Transactional
	public void deleteReservation(UUID reservationId) {
		Reservation reservation = findReservation(reservationId);
		ReservationSlot slot = findSlotForUpdate(reservation.getReservationSlotId());
		/*
			if (!userInfo.getRole.equals("ADMIN") && reservation.getUserId() != userInfo) {
			throw new ReservationPermissionDenied("접근 권한이 없습니다");
		 */

		slot.increase();

		reservation.delete(1L);    //임시
	}

	private ReservationSlot checkReservationSlot(UUID hospitalId, LocalDate reservationDate, Integer reservationTime) {

		//HospitalInfoResponse hospital = reservationClient.getHospital(hospitalId.getHospitalId()); 나중에 병원으로부터 좌석정보 받아오기 (현재는 10으로 임시)
		//int leftSeat = hospital.좌석정보

		Optional<ReservationSlot> reservationSlot = reservationSlotRepository.findByReservationInfo(hospitalId,
			reservationDate,
			reservationTime);

		if (reservationSlot.isPresent()) {
			return reservationSlot.get();
		}
		try {
			return reservationSlotRepository.save(            //mvp 개발 완료 후 동시성 문제 처리
				ReservationSlot.createReservationSlotBuilder()
					.hospitalId(hospitalId)
					.leftSeat(10) //임시(leftSeat)
					.reservationDate(reservationDate)
					.reservationTime(reservationTime)
					.build());
		} catch (Exception e) {
			return reservationSlotRepository.findByReservationInfo(hospitalId, reservationDate, reservationTime)
				.orElseThrow(() -> new IllegalArgumentException("오류"));
		}
	}

	private ReservationSlot findSlotForUpdate(UUID reservationSlotId) {
		return reservationSlotRepository.findByIdForUpdate(reservationSlotId)        //리팩토링 필수
			.orElseThrow(() -> new NotExistReservationException("예약 정보가 존재하지 않습니다"));
	}

	private Reservation findReservation(UUID reservationId) {
		return reservationRepository.findById(reservationId)
			.orElseThrow(() -> new NotExistReservationException("존재하지 않는 예약입니다"));
	}

	private void isDeleted(Reservation reservation) {
		if (reservation.isDeleted()) {
			throw new ReservationAlreadyDeletedException("이미 삭제된 예약입니다.");
		}
	}
}
