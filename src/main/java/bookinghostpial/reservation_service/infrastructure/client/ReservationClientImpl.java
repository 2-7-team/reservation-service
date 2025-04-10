package bookinghostpial.reservation_service.infrastructure.client;

import java.util.UUID;

import org.springframework.stereotype.Component;

import bookinghostpial.reservation_service.application.client.ReservationClient;
import bookinghostpial.reservation_service.application.dto.HospitalInfoResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReservationClientImpl implements ReservationClient {

	private final HospitalFeignClient hospitalFeignClient;

	@Override
	public HospitalInfoResponse getHospital(UUID hospitalId) {
		return hospitalFeignClient.getHospital(hospitalId);
	}
}
