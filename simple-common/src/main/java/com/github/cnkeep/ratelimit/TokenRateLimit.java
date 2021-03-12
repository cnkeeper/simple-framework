package com.github.cnkeep.ratelimit;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: 令牌桶算法
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/3/12
 * @version:
 **/
public class TokenRateLimit extends AbstractRateLimit {
    private long maxLimit;
    private volatile long lastTime = System.currentTimeMillis();
    private static final long SECOND_MILLS = 1000;
    private int qps;
    private AtomicLong current = new AtomicLong(0);
    /**
     * 每个token消耗的微秒数
     */
    private long onePermitUsingMicro;

    public TokenRateLimit(int qps) {
        this.qps = qps;
        maxLimit = qps;
        onePermitUsingMicro = TimeUnit.SECONDS.toMicros(1) / qps;
    }

    @Override
    public boolean acquire() {
        synchronized (mutex()) {
            long now = System.currentTimeMillis();
            long interval = now - lastTime;
            if (interval > SECOND_MILLS) {
                // 大于时间窗口，重置
                interval = SECOND_MILLS;
                lastTime = now;
            }

            long makePermits = TimeUnit.MILLISECONDS.toMicros(interval) / onePermitUsingMicro;
            System.out.println("cur:" + current.get() + ", make:" + makePermits);

            if (makePermits != 0) {
                // 上次放入令牌时间
                lastTime = System.currentTimeMillis();
            }

            if (current.addAndGet(makePermits) > maxLimit) {
                // 桶溢出，重置
                current.set(makePermits);
            }

            if (current.get() <= 0 || current.decrementAndGet() < 0) {
                return false;
            }

            return true;
        }
    }

}
