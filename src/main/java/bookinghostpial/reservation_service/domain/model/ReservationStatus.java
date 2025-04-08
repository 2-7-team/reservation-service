package bookinghostpial.reservation_service.domain.model;

public enum ReservationStatus {
	SCHEDULED,	//예약 성공(진료 전)
	CANCELLED,  //예약 취소
	COMPLETED	//진료 완료
}
