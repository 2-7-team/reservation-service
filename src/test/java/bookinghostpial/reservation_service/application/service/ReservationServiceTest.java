package bookinghostpial.reservation_service.application.service;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import bookinghospital.common_module.userInfo.UserDetails;
import bookinghostpial.reservation_service.domain.model.ReservationSlot;
import bookinghostpial.reservation_service.domain.repository.ReservationRepository;
import bookinghostpial.reservation_service.domain.repository.ReservationSlotRepository;

@SpringBootTest
@Transactional
@Rollback(false)
class ReservationServiceTest {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ReservationSlotRepository reservationSlotRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Test
	void createReservation() throws InterruptedException {
		//GIVEN
		final int count = 50;
		final ExecutorService executorService = Executors.newFixedThreadPool(32);
		final CountDownLatch latch = new CountDownLatch(count);
		UUID hospitalId = UUID.randomUUID();
		for (int i = 0; i < count; i++) {
			executorService.execute(() -> {
				try {
					reservationService.createReservation(hospitalId, LocalDate.now(), 10,
						new UserDetails("1L", "ROLE_USER"));
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		ReservationSlot reservationSlot = reservationSlotRepository.findByReservationInfo(hospitalId, LocalDate.now(),
			10).get();

		Assertions.assertEquals(0, reservationSlot.getLeft_seat());

	}

}