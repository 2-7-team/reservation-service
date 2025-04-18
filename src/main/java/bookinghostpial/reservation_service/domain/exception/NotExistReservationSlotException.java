package bookinghostpial.reservation_service.domain.exception;

public class NotExistReservationSlotException extends RuntimeException {
	public NotExistReservationSlotException(String message) {
		super(message);
	}
}
