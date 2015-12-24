package org.treeleaf.queue;

/**
 * Created by yaoshuhong on 2015/12/24.
 */
public interface Queuer {

    void add(Object msg);

    void setHandler(MsgHandler handler);
}
