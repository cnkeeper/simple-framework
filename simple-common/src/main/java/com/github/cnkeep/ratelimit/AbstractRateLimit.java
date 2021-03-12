package com.github.cnkeep.ratelimit;


public abstract class AbstractRateLimit implements RateLimit {
    private volatile Object mutex;

    protected Object mutex() {
        if (mutex == null) {
            synchronized (this) {
                if (mutex == null) {
                    mutex = new Object();
                }
            }
        }
        return mutex;
    }
}
