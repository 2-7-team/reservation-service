package bookinghostpial.reservation_service.domain.exception;

public class ReservationAlreadyDeletedException extends RuntimeException {
	public ReservationAlreadyDeletedException(String message) {
		super(message);
	}
}
