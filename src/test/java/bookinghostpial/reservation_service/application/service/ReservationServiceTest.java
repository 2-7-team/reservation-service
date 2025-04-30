package bookinghostpial.reservation_service.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import bookinghostpial.reservation_service.application.facade.ReservationFacade;
import bookinghostpial.reservation_service.domain.repository.ReservationRepository;
import bookinghostpial.reservation_service.domain.repository.ReservationSlotRepository;
import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(false)
class ReservationServiceTest {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ReservationFacade reservationFacade;

	@Autowired
	private ReservationSlotRepository reservationSlotRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private EntityManager entityManager;

	private UUID randomUUID = UUID.randomUUID();
/*

	@Test
	void createReservation() throws InterruptedException {

		//GIVEN
		final int count = 50;

		final ExecutorService executorService = Executors.newFixedThreadPool(32);
		final CountDownLatch latch = new CountDownLatch(count);
		UUID hospitalId = randomUUID;

		reservationSlotRepository.save(ReservationSlot.createReservationSlotBuilder()
			.reservationDate(LocalDate.now())
			.reservationTime(10)
			.leftSeat(10)
			.hospitalId(hospitalId)
			.build());

		entityManager.flush();
		for (int i = 0; i < count; i++) {
			executorService.execute(() -> {
				try {
					reservationFacade.reserve(hospitalId, LocalDate.now(), 10,
						new UserDetails(1L, "ROLE_USER"));
				} catch (NotExistReservationSlotException e) {

				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		ReservationSlot reservationSlot = reservationSlotRepository.findByReservationInfo(hospitalId, LocalDate.now(),
			10).get();

		//	Assertions.assertEquals(0, reservationSlot.getLeft_seat());


	}
*/
}