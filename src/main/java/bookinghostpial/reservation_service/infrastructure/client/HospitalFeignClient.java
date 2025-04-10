package bookinghostpial.reservation_service.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import bookinghostpial.reservation_service.application.dto.HospitalInfoResponse;

@FeignClient(name = "hospital-service", url = "localhost:8080")
public interface HospitalFeignClient {

	@GetMapping("/api/hospitals/{hospital_id}")
	HospitalInfoResponse getHospital(@PathVariable("hospital_id") UUID hospitalId);
}
