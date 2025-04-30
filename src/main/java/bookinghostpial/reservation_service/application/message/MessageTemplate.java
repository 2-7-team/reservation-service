package bookinghostpial.reservation_service.application.message;

import bookinghostpial.reservation_service.application.event.ReservationCreateAlertEvent;

public interface MessageTemplate {
	void send(String topic, String key, ReservationCreateAlertEvent message);
}
