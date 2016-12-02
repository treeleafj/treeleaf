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

    private Class leftJoin;

    private String leftField;

    /**
     * 统计的某个字段
     */
    private String sumField;

    private String onWhere;

    public Example() {
        oredCriteria = new ArrayList<>();
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
        return this.pageable != null ? this.pageable.getPageSize() : -1;
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

    public Example leftJoin(Class leftJoin) {
        this.leftJoin = leftJoin;
        return this;
    }

    public Example on(String onWhere) {
        this.onWhere = onWhere;
        return this;
    }

    public Example leftField(String leftField) {
        this.leftField = leftField;
        return this;
    }

    public String getLeftField() {
        return leftField;
    }

    public Class getLeftJoin() {
        return leftJoin;
    }

    public String getOnWhere() {
        return onWhere;
    }

    public String getSumField() {
        return sumField;
    }

    public Example sumField(String sumField) {
        this.sumField = sumField;
        return this;
    }
}

