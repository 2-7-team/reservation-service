package bookinghostpial.reservation_service.infrastructure.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import bookinghostpial.reservation_service.application.lock.ReservationLockClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ReservationLockClientImpl implements ReservationLockClient {

	private final RedissonClient redissonClient;

	@Override
	public RLock getLock(String key) {
		return redissonClient.getLock(key);
	}

}
