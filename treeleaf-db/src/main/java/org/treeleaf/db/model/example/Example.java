package org.treeleaf.db.model.example;

import java.util.ArrayList;
import java.util.List;

/**
 * 表达式
 *
 * @Author leaf
 * 2015/1/6 0006 22:53.
 */
public abstract class Example<T extends Criteria> {

    protected String orderByClause;
    protected boolean distinct;
    protected List<T> oredCriteria;
    /**
     * 默认查询10条
     */
    private int limit = 10;
    /**
     * 起始行号
     */
    private long start = 0;

    public Example() {
        oredCriteria = new ArrayList<T>();
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public List<T> getOredCriteria() {
        return oredCriteria;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public void or(T criteria) {
        oredCriteria.add(criteria);
    }

    public T or() {
        T criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public T createCriteria() {
        T criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * 创建条件表达式对象
     *
     * @return
     */
    protected abstract T createCriteriaInternal();

}

