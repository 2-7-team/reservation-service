package bookinghostpial.reservation_service.infrastructure.client;

import java.util.List;

import org.springframework.stereotype.Component;

import bookinghostpial.reservation_service.application.client.ReservationClient;
import bookinghostpial.reservation_service.application.dto.HospitalWithSchedulesResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReservationClientImpl implements ReservationClient {

	private final HospitalFeignClient hospitalFeignClient;

	@Override
	public List<HospitalWithSchedulesResponse> getHospital() {
		return hospitalFeignClient.getAllHospital();
	}
}
