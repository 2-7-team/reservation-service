package bookinghostpial.reservation_service.application.lock;

import org.redisson.api.RLock;

public interface ReservationLockClient {

	RLock getLock(String key);

}
