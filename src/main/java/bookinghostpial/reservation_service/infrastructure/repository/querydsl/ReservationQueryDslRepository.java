package bookinghostpial.reservation_service.infrastructure.repository.querydsl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import bookinghostpial.reservation_service.domain.model.QReservation;
import bookinghostpial.reservation_service.domain.model.Reservation;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReservationQueryDslRepository {

	private final JPAQueryFactory jpaQueryFactory;
	QReservation reservation = QReservation.reservation;

	public Page<Reservation> findAllByUserId(Long userId, Pageable pageable) {

		List<Reservation> fetch = jpaQueryFactory.select(reservation)
			.from(reservation)
			.where(isUser(userId))
			.where(reservation.isDeleted.eq(false))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return new PageImpl<Reservation>(fetch, pageable, getTotalCount(userId));
	}

	private Long getTotalCount(Long userId) {
		return jpaQueryFactory.select(reservation.count())
			.from(reservation)
			.where(isUser(userId))
			.where(reservation.isDeleted.eq(false))
			.fetchOne();
	}

	private BooleanExpression isUser(Long userId) {
		return userId != null ? reservation.userId.eq(userId) : null;
	}
}
