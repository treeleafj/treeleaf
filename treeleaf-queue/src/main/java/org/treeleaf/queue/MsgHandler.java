package org.treeleaf.queue;

/**
 * Created by yaoshuhong on 2015/12/24.
 */
public interface MsgHandler<T> {

    void handler(T o);

}
