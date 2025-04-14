package bookinghostpial.reservation_service.presentation;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bookinghospital.common_module.userInfo.UserDetails;
import bookinghospital.common_module.userInfo.UserInfo;
import bookinghostpial.reservation_service.application.service.ReservationService;
import bookinghostpial.reservation_service.presentation.dto.request.CreateReservationRequest;
import bookinghostpial.reservation_service.presentation.dto.request.UpdateReservationRequest;
import bookinghostpial.reservation_service.presentation.dto.response.ReservationDetailsResponse;
import bookinghostpial.reservation_service.presentation.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping()
	public ResponseEntity<Void> createReservation(@RequestBody CreateReservationRequest request,
		@RequestHeader("X-User-Name") String userName,
		@UserInfo UserDetails user) {

		//service로 request를 넘길것인가? or dto를 만들어서 보낼것인가?

		log.info("username  {} ", userName);
		log.info("userId  {} ", user.getUserId());
		log.info("userRole {} ", user.getRole());

		reservationService.createReservation(request.getHospitalId(), request.getReservationDate(),
			request.getReservationTime());

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping()
	public ResponseEntity<Page<ReservationResponse>> getAllReservations(
		//@UserInfo userInfo
		@PageableDefault Pageable pageable) {
		Page<ReservationResponse> reservationList = reservationService.getReservationList(pageable);
		return ResponseEntity.ok(reservationList);
	}

	@GetMapping("/{reservation_id}")
	public ResponseEntity<ReservationDetailsResponse> getReservationDetails(
		@PathVariable("reservation_id") UUID reservationId) {

		ReservationDetailsResponse reservationDetails = reservationService.getReservationDetails(reservationId);
		return ResponseEntity.status(HttpStatus.OK).body(reservationDetails);
	}

	@PatchMapping("/{reservation_id}")
	public ResponseEntity<Void> updateReservation(@PathVariable("reservation_id") UUID reservationId,
		@RequestBody UpdateReservationRequest request) {

		reservationService.updateReservation(reservationId, request.getReservationDate(), request.getReservationTime());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/{reservation_id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable("reservation_id") UUID reservationId) {

		reservationService.deleteReservation(reservationId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
