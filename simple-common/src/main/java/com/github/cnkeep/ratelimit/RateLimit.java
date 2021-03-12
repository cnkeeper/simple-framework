package com.github.cnkeep.ratelimit;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/3/12
 * @version:
 **/
public interface RateLimit {
    /**
     *
     * @return
     */
    boolean acquire();

}
