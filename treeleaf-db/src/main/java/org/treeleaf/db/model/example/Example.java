package org.treeleaf.db.model.example;

import org.treeleaf.common.bean.Pageable;

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
     * 分页
     */
    private Pageable pageable;

    public Example() {
        oredCriteria = new ArrayList<T>();
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public Example setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
        return this;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public Example setDistinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public List<T> getOredCriteria() {
        return oredCriteria;
    }

    public int getLimit() {
        return this.pageable != null ? this.pageable.getPageSize() : 10;
    }

    public long getStart() {
        return this.pageable != null ? this.pageable.getOffset() : 0;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public Example setPageable(Pageable pageable) {
        this.pageable = pageable;
        return this;
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

    public Example clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        return this;
    }

    /**
     * 创建条件表达式对象
     *
     * @return
     */
    protected abstract T createCriteriaInternal();

}

