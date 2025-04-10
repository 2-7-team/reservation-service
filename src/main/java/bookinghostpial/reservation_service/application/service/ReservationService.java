package bookinghostpial.reservation_service.application.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookinghostpial.reservation_service.application.client.ReservationClient;
import bookinghostpial.reservation_service.application.dto.request.CreateReservationDto;
import bookinghostpial.reservation_service.domain.model.Reservation;
import bookinghostpial.reservation_service.domain.model.ReservationSlot;
import bookinghostpial.reservation_service.domain.model.ReservationStatus;
import bookinghostpial.reservation_service.domain.repository.ReservationRepository;
import bookinghostpial.reservation_service.domain.repository.ReservationSlotRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final ReservationSlotRepository reservationSlotRepository;
	private final ReservationClient reservationClient;
	private static long USER_ID = 1L;
	//private static UUID uuid = UUID.randomUUID();

	@Transactional
	public void createReservation(CreateReservationDto hospitalId) {


		/*
		 * RESERVATION SLOT 존재하는지 확인 후 반환.
		 * */
		ReservationSlot reservationSlot = checkReservationSlot(hospitalId.getHospitalId(),    //추후 도메인 이벤트 방식으로 변경 고려
			hospitalId.getReservationDate(),
			hospitalId.getReservationTime());

		reservationSlot.decrease();                    //동시성 처리 필요

		Reservation reservation = Reservation.createReservationBuilder()
			.userId(USER_ID)
			.reservationSlotId(reservationSlot.getId())
			.status(ReservationStatus.SCHEDULED)
			.build();

		USER_ID++;
		reservationRepository.save(reservation);
	}

	private ReservationSlot checkReservationSlot(UUID hospitalId, LocalDate reservationDate, Integer reservationTime) {

		//HospitalInfoResponse hospital = reservationClient.getHospital(hospitalId.getHospitalId()); 나중에 병원으로부터 좌석정보 받아오기 (현재는 10으로 임시)
		//int leftSeat = hospital.좌석정보

		ReservationSlot reservationSlot = reservationSlotRepository.findByReservationInfo(hospitalId, reservationDate,
				reservationTime)
			.orElseGet(() -> reservationSlotRepository.save(            //mvp 개발 완료 후 동시성 문제 처리
				ReservationSlot.createReservationSlotBuilder()
					.hospitalId(hospitalId)
					.leftSeat(10) //임시(leftSeat)
					.reservationDate(reservationDate)
					.reservationTime(reservationTime)
					.build()
			));

		return reservationSlot;
	}

	public void getReservationList(
		//UserInfo userInfo
	) {
	}
}
