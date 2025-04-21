package bookinghostpial.reservation_service.application.handler;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import bookinghostpial.reservation_service.application.event.ReservationCreateAlertEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReservationSlotEventHandler {

	private final KafkaTemplate<String, ReservationCreateAlertEvent> kafkaTemplate;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void sendMessage(ReservationCreateAlertEvent event) {

		// Kafka 메시지 보내기
		kafkaTemplate.send("reservation.alerts", "key", event);
	}

}
