package org.treeleaf.db.model.example;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准
 *
 * @Author leaf
 * 2015/1/6 0006 22:55.
 */
public class Criteria {

    private List<Criterion> criteria;

    public Criteria() {
        super();
        criteria = new ArrayList<>();
    }

    public boolean isValid() {
        return criteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
        return criteria;
    }

    public List<Criterion> getCriteria() {
        return criteria;
    }

    protected <T extends Criteria> T addCriterion(String condition, Object... values) {
        if (values == null) {
            throw new RuntimeException("Value for " + condition + " cannot be null");
        }
        criteria.add(new Criterion(condition, values));
        return (T) this;
    }

    //子类继承的时候写的代码格式(严格遵守以下格式写方法名):
    /*public Criteria andIdIsNull() {
        addCriterion("id is null");
        return (Criteria) this;
    }

    public Criteria andIdIsNotNull() {
        addCriterion("id is not null");
        return (Criteria) this;
    }

    public Criteria andIdEqualTo(String value) {
        addCriterion("id =", value, "id");
        return (Criteria) this;
    }

    public Criteria andIdNotEqualTo(String value) {
        addCriterion("id <>", value, "id");
        return (Criteria) this;
    }

    public Criteria andIdGreaterThan(String value) {
        addCriterion("id >", value, "id");
        return (Criteria) this;
    }

    public Criteria andIdGreaterThanOrEqualTo(String value) {
        addCriterion("id >=", value, "id");
        return (Criteria) this;
    }

    public Criteria andIdLessThan(String value) {
        addCriterion("id <", value, "id");
        return (Criteria) this;
    }

    public Criteria andIdLessThanOrEqualTo(String value) {
        addCriterion("id <=", value, "id");
        return (Criteria) this;
    }

    public Criteria andIdLike(String value) {
        addCriterion("id like", value, "id");
        return (Criteria) this;
    }

    public Criteria andIdNotLike(String value) {
        addCriterion("id not like", value, "id");
        return (Criteria) this;
    }

    public Criteria andIdIn(List<String> values) {
        addCriterion("id in", values, "id");
        return (Criteria) this;
    }

    public Criteria andIdNotIn(List<String> values) {
        addCriterion("id not in", values, "id");
        return (Criteria) this;
    }

    public Criteria andIdBetween(String value1, String value2) {
        addCriterion("id between", value1, value2, "id");
        return (Criteria) this;
    }

    public Criteria andIdNotBetween(String value1, String value2) {
        addCriterion("id not between", value1, value2, "id");
        return (Criteria) this;
    }*/
}
