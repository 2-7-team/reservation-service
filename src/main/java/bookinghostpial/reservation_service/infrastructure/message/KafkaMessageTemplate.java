package bookinghostpial.reservation_service.infrastructure.message;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import bookinghostpial.reservation_service.application.event.ReservationCreateAlertEvent;
import bookinghostpial.reservation_service.application.message.MessageTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageTemplate implements MessageTemplate {

	private final KafkaTemplate<String, ReservationCreateAlertEvent> kafkaTemplate;
	private static final int MAX_RETRY = 3;

	@Override
	public void send(String topic, String key, ReservationCreateAlertEvent message) {
		int tryCount = 0;
		boolean success = false;

		while (tryCount < MAX_RETRY && !success) {
			try {
				kafkaTemplate.send(topic, key, message).get();
				success = true;
			} catch (Exception e) {
				tryCount++;
				if (tryCount == MAX_RETRY) {
					log.error("Kafka 메시지 전송 최종 실패");
				}
			}
		}

	}
}
