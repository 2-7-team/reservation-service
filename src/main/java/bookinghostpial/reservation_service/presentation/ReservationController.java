package bookinghostpial.reservation_service.presentation;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bookinghostpial.reservation_service.application.dto.request.CreateReservationDto;
import bookinghostpial.reservation_service.application.dto.request.UpdateReservationDto;
import bookinghostpial.reservation_service.application.dto.response.ReservationDetailsResponse;
import bookinghostpial.reservation_service.application.dto.response.ReservationListResponse;
import bookinghostpial.reservation_service.application.service.ReservationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping()
	public ResponseEntity<Void> createReservation(@RequestBody CreateReservationDto request) {
		reservationService.createReservation(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping()
	public ResponseEntity<ReservationListResponse> getAllReservations(
		//@UserInfo userInfo
	) {
		reservationService.getReservationList();
		return null;
	}

	@GetMapping("/{reservation_id}")
	public ResponseEntity<ReservationDetailsResponse> getReservationDetails(
		@PathVariable("reservation_id") UUID reservationId) {

		return null;
	}

	@PatchMapping("/{reservation_id}")
	public ResponseEntity<Void> updateReservation(@PathVariable("reservation_id") UUID reservationId,
		@RequestBody UpdateReservationDto request) {

		return null;
	}

	@DeleteMapping("/{reservation_id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable("reservation_id") UUID reservationId) {

		return null;
	}

}
