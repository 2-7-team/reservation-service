package bookinghostpial.reservation_service.domain.exception;

public class NotExistReservationException extends RuntimeException {
	public NotExistReservationException(String message) {
		super(message);
	}
}
