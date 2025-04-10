package bookinghostpial.reservation_service.domain.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import bookinghospital.common_module.handler.GlobalExceptionHandler;

@Order(1)
@RestControllerAdvice
public class ReservationExceptionHandler extends GlobalExceptionHandler {

	@ExceptionHandler(NoLeftSeatException.class)
	protected ResponseEntity<String> handleException(NoLeftSeatException e) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}
