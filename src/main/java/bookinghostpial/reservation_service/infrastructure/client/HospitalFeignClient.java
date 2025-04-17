package bookinghostpial.reservation_service.infrastructure.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import bookinghostpial.reservation_service.application.dto.HospitalWithSchedulesResponse;

@FeignClient(name = "hospital-service", url = "localhost:8080")
public interface HospitalFeignClient {

	@GetMapping("/api/hospitals/internal/all")
	List<HospitalWithSchedulesResponse> getAllHospital();
}
