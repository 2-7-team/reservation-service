package bookinghostpial.reservation_service.domain.exception;

public class NoLeftSeatException extends RuntimeException {
	public NoLeftSeatException(String message) {
		super(message);
	}
}
