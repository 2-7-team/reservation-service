package bookinghostpial.reservation_service.application.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookinghospital.common_module.userInfo.UserDetails;
import bookinghostpial.reservation_service.application.event.ReservationCreateAlertEvent;
import bookinghostpial.reservation_service.domain.exception.NotExistReservationException;
import bookinghostpial.reservation_service.domain.exception.ReservationAlreadyDeletedException;
import bookinghostpial.reservation_service.domain.exception.ReservationPermissionDenied;
import bookinghostpial.reservation_service.domain.model.Reservation;
import bookinghostpial.reservation_service.domain.model.ReservationStatus;
import bookinghostpial.reservation_service.domain.repository.ReservationRepository;
import bookinghostpial.reservation_service.presentation.dto.response.ReservationDetailsResponse;
import bookinghostpial.reservation_service.presentation.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Transactional
	public void createReservation(UUID slotId, LocalDate reservationDate, Integer reservationTime,
		UserDetails userInfo) {

		Reservation reservation = Reservation.createReservationBuilder()
			.userId(userInfo.getUserId())
			.reservationSlotId(slotId)
			.reservationDate(reservationDate)
			.reservationTime(reservationTime)
			.status(ReservationStatus.SCHEDULED)
			.build();

		reservationRepository.save(reservation);

		eventPublisher.publishEvent(
			new ReservationCreateAlertEvent(reservation.getId(), userInfo.getUserId(), reservationDate,
				//나중에 dto 전체 builder or 정적팩토리로 바꾸겠습니다...
				reservationTime));
	}

	public Page<ReservationResponse> getReservationList(
		Pageable pageable,
		UserDetails userInfo
	) {
		Long userId = null; //수정
		if (userInfo.getRole().equals("ROLE_USER")) {
			userId = userInfo.getUserId();
		}
		Page<Reservation> allByUserId = reservationRepository.findAllByUserId(pageable, userId);

		return allByUserId.map(
			reservation -> ReservationResponse.builder()
				.reservation(reservation).build());
	}

	//Response만 만들고 있는데 컨트롤러에 위임이 낫지 않나
	public ReservationDetailsResponse getReservationDetails(UUID reservationId) {

		Reservation reservation = findReservation(reservationId);
		isDeleted(reservation);
		return ReservationDetailsResponse
			.builder()
			.reservation(reservation)
			.build();
	}

	@Transactional
	public void updateReservation(UUID newSlotId, Reservation reservation, LocalDate reservationDate,
		Integer reservationTime,
		UserDetails userInfo) {

		checkAuthority(userInfo, reservation);

		reservation.update(reservationDate, reservationTime, newSlotId);
	}

	@Transactional
	public void deleteReservation(Reservation reservation, UserDetails userInfo) {

		checkAuthority(userInfo, reservation);

		reservation.delete(userInfo.getUserId());
	}

	private void checkAuthority(UserDetails userInfo, Reservation reservation) {
		if (!userInfo.getRole().equals("ROLE_ADMIN") && !reservation.getUserId().equals(userInfo.getUserId())) {
			throw new ReservationPermissionDenied("접근 권한이 없습니다");
		}
	}

	public Reservation findReservation(UUID reservationId) {
		return reservationRepository.findById(reservationId)
			.orElseThrow(() -> new NotExistReservationException("존재하지 않는 예약입니다"));
	}

	private void isDeleted(Reservation reservation) {
		if (reservation.isDeleted()) {
			throw new ReservationAlreadyDeletedException("이미 삭제된 예약입니다.");
		}
	}
}
