package bookinghostpial.reservation_service.domain.exception;

public class ReservationPermissionDenied extends RuntimeException {
	public ReservationPermissionDenied(String message) {
		super(message);
	}
}
