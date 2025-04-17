package bookinghostpial.reservation_service.domain.exception;

public class CannotUpdateSeatException extends RuntimeException {
	public CannotUpdateSeatException(String message) {
		super(message);
	}
}
