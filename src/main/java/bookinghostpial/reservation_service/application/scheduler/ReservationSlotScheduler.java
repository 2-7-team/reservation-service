package bookinghostpial.reservation_service.application.scheduler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import bookinghostpial.reservation_service.application.client.ReservationClient;
import bookinghostpial.reservation_service.application.dto.FindOneScheduleResponseDto;
import bookinghostpial.reservation_service.application.dto.HospitalWithSchedulesResponse;
import bookinghostpial.reservation_service.domain.model.ReservationSlot;
import bookinghostpial.reservation_service.domain.repository.ReservationSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationSlotScheduler {

	private final ReservationClient reservationClient;
	private final ReservationSlotRepository reservationSlotRepository;
	private List<List<ReservationSlot>> failedHospitalList = new ArrayList<>();

	@Scheduled(cron = "0 0 6 * * *")
	public void reservationSlotScheduler() {
		List<HospitalWithSchedulesResponse> hospitalList = reservationClient.getHospital();

		for (HospitalWithSchedulesResponse hospital : hospitalList) {
			List<FindOneScheduleResponseDto> schedules = hospital.getSchedules();
			List<ReservationSlot> reservationSlots = new ArrayList<>();
			for (FindOneScheduleResponseDto schedule : schedules) {
				reservationSlots.add(ReservationSlot.createReservationSlotBuilder()
					.hospitalId(hospital.getId())
					.reservationDate(LocalDate.now())
					.reservationTime(schedule.getTime().getHour())
					.leftSeat(schedule.getCapacity())
					.build());
			}
			try {
				reservationSlotRepository.saveAll(reservationSlots);
			} catch (Exception e) {
				failedHospitalList.add(reservationSlots);
				log.error("hospital id : {}  , 병원명 {} 등록 실패", hospital.getId(), hospital.getName());
			}
		}
	}

	//추후 멀티 서버 환경이면 락 걸어야함
	//실패 재시도
	@Scheduled(cron = "0 30 6 * * *")
	public void retrySlotScheduler() {
		for (List<ReservationSlot> reservationSlots : failedHospitalList) {
			try {
				reservationSlotRepository.saveAll(reservationSlots);
			} catch (Exception e) {
				log.error("hospital id: {}  재시도 실패", reservationSlots.get(0).getHospitalId());
			}
		}
	}
}
