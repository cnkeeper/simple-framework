package com.github.cnkeep.arthas.event;


public interface EventListener {
    /**
     * 业务回调函数
     * @param event
     * @return 改返回值将覆盖作为原函数的返回值返回
     */
    Object onEvent(Event event);
}
