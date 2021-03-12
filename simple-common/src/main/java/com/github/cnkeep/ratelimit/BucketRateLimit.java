package com.github.cnkeep.ratelimit;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: 漏桶算法
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/3/12
 * @version:
 **/
public class BucketRateLimit extends AbstractRateLimit {
    private long maxLimit;
    private volatile long lastTime = System.currentTimeMillis();
    private static final long SECOND_MILLS = 1000;
    private int qps;
    private AtomicLong bucket = new AtomicLong(0);
    /**
     * 每个token 漏出消耗的时间
     */
    private long onePermitUsingMicro;

    public BucketRateLimit(int qps) {
        this.qps = qps;
        onePermitUsingMicro = TimeUnit.SECONDS.toMicros(1) / qps;
        maxLimit = qps;
    }

    @Override
    public boolean acquire() {
        synchronized (mutex()) {
            long now = System.currentTimeMillis();
            long interval = now - lastTime;
            long reduce = TimeUnit.MILLISECONDS.toMicros(interval) / onePermitUsingMicro;
            if (reduce != 0) {
                lastTime = now;
            }

            System.out.println("cur:" + bucket.get() + ", reduce:" + reduce);
            if (bucket.addAndGet(-reduce) < 0) {
                bucket.set(0);
            }

            if (bucket.get() + 1 > maxLimit) {
                return false;
            }

            bucket.incrementAndGet();
            return true;
        }
    }
}
