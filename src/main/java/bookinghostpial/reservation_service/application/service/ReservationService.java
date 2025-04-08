package bookinghostpial.reservation_service.application.service;

import org.springframework.stereotype.Service;

import bookinghostpial.reservation_service.application.dto.request.CreateReservationDto;
import bookinghostpial.reservation_service.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;
	// private final ReservationClient reservationClient;

	public void createReservation(CreateReservationDto hospitalId) {

		// reservationClient.findById(hospitalId);
	}
}
