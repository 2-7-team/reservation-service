package bookinghostpial.reservation_service.presentation;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bookinghostpial.reservation_service.application.service.ReservationSlotService;
import bookinghostpial.reservation_service.presentation.dto.request.UpdateLeftSeatRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations/internal")
public class ReservationInternalController {

	private final ReservationSlotService reservationSlotService;

	@PatchMapping("/{hospital_id}")
	public ResponseEntity<String> updateReservationSeat(@PathVariable("hospital_id") UUID hospitalId,
		@RequestBody UpdateLeftSeatRequest leftSeatRequest) {

		reservationSlotService.updateLeftSeat(hospitalId, leftSeatRequest.getReservationTime(),
			leftSeatRequest.getUpdateLeftSeat());
		return ResponseEntity.ok().body("성공적으로 수정되었습니다");
	}
}
