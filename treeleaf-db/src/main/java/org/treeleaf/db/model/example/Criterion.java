package org.treeleaf.db.model.example;

import java.util.List;

/**
 * 准则
 *
 * @Author leaf
 * 2015/1/6 0006 22:54.
 */
public class Criterion {

    private String condition;

    private Object[] value;

    public Criterion(String condition, Object[] values) {
        this.condition = condition;
        this.value = values;
    }
    public String getCondition() {
        return condition;
    }

    public Object [] getValue() {
        return value;
    }

}
