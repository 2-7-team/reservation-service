package bookinghostpial.reservation_service.application.client;

import java.util.UUID;

import bookinghostpial.reservation_service.application.dto.HospitalInfoResponse;

public interface ReservationClient {

	HospitalInfoResponse getHospital(UUID hospitalId);
}
