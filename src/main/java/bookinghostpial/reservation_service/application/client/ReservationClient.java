package bookinghostpial.reservation_service.application.client;

import java.util.List;

import bookinghostpial.reservation_service.application.dto.HospitalWithSchedulesResponse;

public interface ReservationClient {

	List<HospitalWithSchedulesResponse> getHospital();
}
