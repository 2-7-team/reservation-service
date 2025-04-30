package bookinghostpial.reservation_service.application.lock;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DistributedLockAop {

	private static final String REDISSON_LOCK_PREFIX = "LOCK:";
	private final ReservationLockClient reservationLockClient;
	private final AopForTransaction aopForTransaction;

	@Around("@annotation(bookinghostpial.reservation_service.application.lock.DistributedLock)")
	public Object lock(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

		String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(),
			joinPoint.getArgs(), distributedLock.key());
		RLock rLock = reservationLockClient.getLock(key);
		//	log.info("key : {}", key);
		long start = System.currentTimeMillis(); // 시작 시간
		try {
			boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(),
				distributedLock.timeUnit());
			if (!available) {
				throw new InterruptedException();
			}

			//	log.info("락 획득 - key: {}, method: {}, 시간: {}ms",
			//			key, method.getName(), System.currentTimeMillis() - start);

			return aopForTransaction.proceed(joinPoint);
			//return joinPoint.proceed();
		} catch (InterruptedException e) {
			throw new InterruptedException();
		} finally {
			try {
				rLock.unlock();
				long end = System.currentTimeMillis();
				//		log.info("락 해제 - key: {}, method: {}, 점유 시간: {}ms",
				//		key, method.getName(), end - start);
			} catch (IllegalMonitorStateException e) {
				log.info("Redisson Lock Already UnLock serviceName : {}, key : {}", method.getName(), key);
			}
		}
	}
}
