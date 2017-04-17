package com.ztravel.paygate.core.po.gen;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlipayRefundExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer pgOffset;

    private Integer pgLength;

    public AlipayRefundExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected AlipayRefundExample(AlipayRefundExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.distinct = example.distinct;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * @param pgOffset 指定返回记录行的偏移量<br> pgOffset= 5,pgLength=10;  // 检索记录行 6-15
     */
    public void setPgOffset(Integer pgOffset) {
        this.pgOffset = pgOffset;
    }

    public Integer getPgOffset() {
        return pgOffset;
    }

    /**
     * @param pgLength 指定返回记录行的最大数目<br> pgOffset= 5,pgLength=10;  // 检索记录行 6-15
     */
    public void setPgLength(Integer pgLength) {
        this.pgLength = pgLength;
    }

    public Integer getPgLength() {
        return pgLength;
    }

    protected abstract static class GeneratedCriteria {
        protected List<String> criteriaWithoutValue;

        protected List<Map<String, Object>> criteriaWithSingleValue;

        protected List<Map<String, Object>> criteriaWithListValue;

        protected List<Map<String, Object>> criteriaWithBetweenValue;

        protected GeneratedCriteria() {
            super();
            criteriaWithoutValue = new ArrayList<String>();
            criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
            criteriaWithListValue = new ArrayList<Map<String, Object>>();
            criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List<String> getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public void setCriteriaWithoutValue(List<String> criteriaWithoutValue) {
            this.criteriaWithoutValue = criteriaWithoutValue;
        }

        public List<Map<String, Object>> getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public void setCriteriaWithSingleValue(List<Map<String, Object>> criteriaWithSingleValue) {
            this.criteriaWithSingleValue = criteriaWithSingleValue;
        }

        public List<Map<String, Object>> getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public void setCriteriaWithListValue(List<Map<String, Object>> criteriaWithListValue) {
            this.criteriaWithListValue = criteriaWithListValue;
        }

        public List<Map<String, Object>> getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        public void setCriteriaWithBetweenValue(List<Map<String, Object>> criteriaWithBetweenValue) {
            this.criteriaWithBetweenValue = criteriaWithBetweenValue;
        }

        public void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List<? extends Object> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List<Object> list = new ArrayList<Object>();
            list.add(value1);
            list.add(value2);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andAlipayRefundIdIsNull() {
            addCriterion("alipay_refund_id is null");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdIsNotNull() {
            addCriterion("alipay_refund_id is not null");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdEqualTo(String value) {
            addCriterion("alipay_refund_id =", value, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdNotEqualTo(String value) {
            addCriterion("alipay_refund_id <>", value, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdGreaterThan(String value) {
            addCriterion("alipay_refund_id >", value, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdGreaterThanOrEqualTo(String value) {
            addCriterion("alipay_refund_id >=", value, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdLessThan(String value) {
            addCriterion("alipay_refund_id <", value, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdLessThanOrEqualTo(String value) {
            addCriterion("alipay_refund_id <=", value, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdLike(String value) {
            addCriterion("alipay_refund_id like", value, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdNotLike(String value) {
            addCriterion("alipay_refund_id not like", value, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdIn(List<String> values) {
            addCriterion("alipay_refund_id in", values, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdNotIn(List<String> values) {
            addCriterion("alipay_refund_id not in", values, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdBetween(String value1, String value2) {
            addCriterion("alipay_refund_id between", value1, value2, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayRefundIdNotBetween(String value1, String value2) {
            addCriterion("alipay_refund_id not between", value1, value2, "alipayRefundId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdIsNull() {
            addCriterion("alipay_id is null");
            return (Criteria) this;
        }

        public Criteria andAlipayIdIsNotNull() {
            addCriterion("alipay_id is not null");
            return (Criteria) this;
        }

        public Criteria andAlipayIdEqualTo(String value) {
            addCriterion("alipay_id =", value, "alipayId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdNotEqualTo(String value) {
            addCriterion("alipay_id <>", value, "alipayId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdGreaterThan(String value) {
            addCriterion("alipay_id >", value, "alipayId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdGreaterThanOrEqualTo(String value) {
            addCriterion("alipay_id >=", value, "alipayId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdLessThan(String value) {
            addCriterion("alipay_id <", value, "alipayId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdLessThanOrEqualTo(String value) {
            addCriterion("alipay_id <=", value, "alipayId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdLike(String value) {
            addCriterion("alipay_id like", value, "alipayId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdNotLike(String value) {
            addCriterion("alipay_id not like", value, "alipayId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdIn(List<String> values) {
            addCriterion("alipay_id in", values, "alipayId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdNotIn(List<String> values) {
            addCriterion("alipay_id not in", values, "alipayId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdBetween(String value1, String value2) {
            addCriterion("alipay_id between", value1, value2, "alipayId");
            return (Criteria) this;
        }

        public Criteria andAlipayIdNotBetween(String value1, String value2) {
            addCriterion("alipay_id not between", value1, value2, "alipayId");
            return (Criteria) this;
        }

        public Criteria andClientIdIsNull() {
            addCriterion("client_id is null");
            return (Criteria) this;
        }

        public Criteria andClientIdIsNotNull() {
            addCriterion("client_id is not null");
            return (Criteria) this;
        }

        public Criteria andClientIdEqualTo(String value) {
            addCriterion("client_id =", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotEqualTo(String value) {
            addCriterion("client_id <>", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdGreaterThan(String value) {
            addCriterion("client_id >", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdGreaterThanOrEqualTo(String value) {
            addCriterion("client_id >=", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdLessThan(String value) {
            addCriterion("client_id <", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdLessThanOrEqualTo(String value) {
            addCriterion("client_id <=", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdLike(String value) {
            addCriterion("client_id like", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotLike(String value) {
            addCriterion("client_id not like", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdIn(List<String> values) {
            addCriterion("client_id in", values, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotIn(List<String> values) {
            addCriterion("client_id not in", values, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdBetween(String value1, String value2) {
            addCriterion("client_id between", value1, value2, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotBetween(String value1, String value2) {
            addCriterion("client_id not between", value1, value2, "clientId");
            return (Criteria) this;
        }

        public Criteria andRefundNumIsNull() {
            addCriterion("refund_num is null");
            return (Criteria) this;
        }

        public Criteria andRefundNumIsNotNull() {
            addCriterion("refund_num is not null");
            return (Criteria) this;
        }

        public Criteria andRefundNumEqualTo(String value) {
            addCriterion("refund_num =", value, "refundNum");
            return (Criteria) this;
        }

        public Criteria andRefundNumNotEqualTo(String value) {
            addCriterion("refund_num <>", value, "refundNum");
            return (Criteria) this;
        }

        public Criteria andRefundNumGreaterThan(String value) {
            addCriterion("refund_num >", value, "refundNum");
            return (Criteria) this;
        }

        public Criteria andRefundNumGreaterThanOrEqualTo(String value) {
            addCriterion("refund_num >=", value, "refundNum");
            return (Criteria) this;
        }

        public Criteria andRefundNumLessThan(String value) {
            addCriterion("refund_num <", value, "refundNum");
            return (Criteria) this;
        }

        public Criteria andRefundNumLessThanOrEqualTo(String value) {
            addCriterion("refund_num <=", value, "refundNum");
            return (Criteria) this;
        }

        public Criteria andRefundNumLike(String value) {
            addCriterion("refund_num like", value, "refundNum");
            return (Criteria) this;
        }

        public Criteria andRefundNumNotLike(String value) {
            addCriterion("refund_num not like", value, "refundNum");
            return (Criteria) this;
        }

        public Criteria andRefundNumIn(List<String> values) {
            addCriterion("refund_num in", values, "refundNum");
            return (Criteria) this;
        }

        public Criteria andRefundNumNotIn(List<String> values) {
            addCriterion("refund_num not in", values, "refundNum");
            return (Criteria) this;
        }

        public Criteria andRefundNumBetween(String value1, String value2) {
            addCriterion("refund_num between", value1, value2, "refundNum");
            return (Criteria) this;
        }

        public Criteria andRefundNumNotBetween(String value1, String value2) {
            addCriterion("refund_num not between", value1, value2, "refundNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNull() {
            addCriterion("order_num is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNotNull() {
            addCriterion("order_num is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumEqualTo(String value) {
            addCriterion("order_num =", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotEqualTo(String value) {
            addCriterion("order_num <>", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThan(String value) {
            addCriterion("order_num >", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThanOrEqualTo(String value) {
            addCriterion("order_num >=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThan(String value) {
            addCriterion("order_num <", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThanOrEqualTo(String value) {
            addCriterion("order_num <=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLike(String value) {
            addCriterion("order_num like", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotLike(String value) {
            addCriterion("order_num not like", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumIn(List<String> values) {
            addCriterion("order_num in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotIn(List<String> values) {
            addCriterion("order_num not in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumBetween(String value1, String value2) {
            addCriterion("order_num between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotBetween(String value1, String value2) {
            addCriterion("order_num not between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumIsNull() {
            addCriterion("trace_num is null");
            return (Criteria) this;
        }

        public Criteria andTraceNumIsNotNull() {
            addCriterion("trace_num is not null");
            return (Criteria) this;
        }

        public Criteria andTraceNumEqualTo(String value) {
            addCriterion("trace_num =", value, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumNotEqualTo(String value) {
            addCriterion("trace_num <>", value, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumGreaterThan(String value) {
            addCriterion("trace_num >", value, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumGreaterThanOrEqualTo(String value) {
            addCriterion("trace_num >=", value, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumLessThan(String value) {
            addCriterion("trace_num <", value, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumLessThanOrEqualTo(String value) {
            addCriterion("trace_num <=", value, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumLike(String value) {
            addCriterion("trace_num like", value, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumNotLike(String value) {
            addCriterion("trace_num not like", value, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumIn(List<String> values) {
            addCriterion("trace_num in", values, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumNotIn(List<String> values) {
            addCriterion("trace_num not in", values, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumBetween(String value1, String value2) {
            addCriterion("trace_num between", value1, value2, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTraceNumNotBetween(String value1, String value2) {
            addCriterion("trace_num not between", value1, value2, "traceNum");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNull() {
            addCriterion("trans_amount is null");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNotNull() {
            addCriterion("trans_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTransAmountEqualTo(Long value) {
            addCriterion("trans_amount =", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotEqualTo(Long value) {
            addCriterion("trans_amount <>", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThan(Long value) {
            addCriterion("trans_amount >", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("trans_amount >=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThan(Long value) {
            addCriterion("trans_amount <", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThanOrEqualTo(Long value) {
            addCriterion("trans_amount <=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountIn(List<Long> values) {
            addCriterion("trans_amount in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotIn(List<Long> values) {
            addCriterion("trans_amount not in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountBetween(Long value1, Long value2) {
            addCriterion("trans_amount between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotBetween(Long value1, Long value2) {
            addCriterion("trans_amount not between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIsNull() {
            addCriterion("refund_amount is null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIsNotNull() {
            addCriterion("refund_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountEqualTo(Long value) {
            addCriterion("refund_amount =", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotEqualTo(Long value) {
            addCriterion("refund_amount <>", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThan(Long value) {
            addCriterion("refund_amount >", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("refund_amount >=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThan(Long value) {
            addCriterion("refund_amount <", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThanOrEqualTo(Long value) {
            addCriterion("refund_amount <=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIn(List<Long> values) {
            addCriterion("refund_amount in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotIn(List<Long> values) {
            addCriterion("refund_amount not in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountBetween(Long value1, Long value2) {
            addCriterion("refund_amount between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotBetween(Long value1, Long value2) {
            addCriterion("refund_amount not between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundCommentIsNull() {
            addCriterion("refund_comment is null");
            return (Criteria) this;
        }

        public Criteria andRefundCommentIsNotNull() {
            addCriterion("refund_comment is not null");
            return (Criteria) this;
        }

        public Criteria andRefundCommentEqualTo(String value) {
            addCriterion("refund_comment =", value, "refundComment");
            return (Criteria) this;
        }

        public Criteria andRefundCommentNotEqualTo(String value) {
            addCriterion("refund_comment <>", value, "refundComment");
            return (Criteria) this;
        }

        public Criteria andRefundCommentGreaterThan(String value) {
            addCriterion("refund_comment >", value, "refundComment");
            return (Criteria) this;
        }

        public Criteria andRefundCommentGreaterThanOrEqualTo(String value) {
            addCriterion("refund_comment >=", value, "refundComment");
            return (Criteria) this;
        }

        public Criteria andRefundCommentLessThan(String value) {
            addCriterion("refund_comment <", value, "refundComment");
            return (Criteria) this;
        }

        public Criteria andRefundCommentLessThanOrEqualTo(String value) {
            addCriterion("refund_comment <=", value, "refundComment");
            return (Criteria) this;
        }

        public Criteria andRefundCommentLike(String value) {
            addCriterion("refund_comment like", value, "refundComment");
            return (Criteria) this;
        }

        public Criteria andRefundCommentNotLike(String value) {
            addCriterion("refund_comment not like", value, "refundComment");
            return (Criteria) this;
        }

        public Criteria andRefundCommentIn(List<String> values) {
            addCriterion("refund_comment in", values, "refundComment");
            return (Criteria) this;
        }

        public Criteria andRefundCommentNotIn(List<String> values) {
            addCriterion("refund_comment not in", values, "refundComment");
            return (Criteria) this;
        }

        public Criteria andRefundCommentBetween(String value1, String value2) {
            addCriterion("refund_comment between", value1, value2, "refundComment");
            return (Criteria) this;
        }

        public Criteria andRefundCommentNotBetween(String value1, String value2) {
            addCriterion("refund_comment not between", value1, value2, "refundComment");
            return (Criteria) this;
        }

        public Criteria andPartnerIsNull() {
            addCriterion("partner is null");
            return (Criteria) this;
        }

        public Criteria andPartnerIsNotNull() {
            addCriterion("partner is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerEqualTo(String value) {
            addCriterion("partner =", value, "partner");
            return (Criteria) this;
        }

        public Criteria andPartnerNotEqualTo(String value) {
            addCriterion("partner <>", value, "partner");
            return (Criteria) this;
        }

        public Criteria andPartnerGreaterThan(String value) {
            addCriterion("partner >", value, "partner");
            return (Criteria) this;
        }

        public Criteria andPartnerGreaterThanOrEqualTo(String value) {
            addCriterion("partner >=", value, "partner");
            return (Criteria) this;
        }

        public Criteria andPartnerLessThan(String value) {
            addCriterion("partner <", value, "partner");
            return (Criteria) this;
        }

        public Criteria andPartnerLessThanOrEqualTo(String value) {
            addCriterion("partner <=", value, "partner");
            return (Criteria) this;
        }

        public Criteria andPartnerLike(String value) {
            addCriterion("partner like", value, "partner");
            return (Criteria) this;
        }

        public Criteria andPartnerNotLike(String value) {
            addCriterion("partner not like", value, "partner");
            return (Criteria) this;
        }

        public Criteria andPartnerIn(List<String> values) {
            addCriterion("partner in", values, "partner");
            return (Criteria) this;
        }

        public Criteria andPartnerNotIn(List<String> values) {
            addCriterion("partner not in", values, "partner");
            return (Criteria) this;
        }

        public Criteria andPartnerBetween(String value1, String value2) {
            addCriterion("partner between", value1, value2, "partner");
            return (Criteria) this;
        }

        public Criteria andPartnerNotBetween(String value1, String value2) {
            addCriterion("partner not between", value1, value2, "partner");
            return (Criteria) this;
        }

        public Criteria andSellerEmailIsNull() {
            addCriterion("seller_email is null");
            return (Criteria) this;
        }

        public Criteria andSellerEmailIsNotNull() {
            addCriterion("seller_email is not null");
            return (Criteria) this;
        }

        public Criteria andSellerEmailEqualTo(String value) {
            addCriterion("seller_email =", value, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerEmailNotEqualTo(String value) {
            addCriterion("seller_email <>", value, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerEmailGreaterThan(String value) {
            addCriterion("seller_email >", value, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerEmailGreaterThanOrEqualTo(String value) {
            addCriterion("seller_email >=", value, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerEmailLessThan(String value) {
            addCriterion("seller_email <", value, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerEmailLessThanOrEqualTo(String value) {
            addCriterion("seller_email <=", value, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerEmailLike(String value) {
            addCriterion("seller_email like", value, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerEmailNotLike(String value) {
            addCriterion("seller_email not like", value, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerEmailIn(List<String> values) {
            addCriterion("seller_email in", values, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerEmailNotIn(List<String> values) {
            addCriterion("seller_email not in", values, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerEmailBetween(String value1, String value2) {
            addCriterion("seller_email between", value1, value2, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerEmailNotBetween(String value1, String value2) {
            addCriterion("seller_email not between", value1, value2, "sellerEmail");
            return (Criteria) this;
        }

        public Criteria andSellerIdIsNull() {
            addCriterion("seller_id is null");
            return (Criteria) this;
        }

        public Criteria andSellerIdIsNotNull() {
            addCriterion("seller_id is not null");
            return (Criteria) this;
        }

        public Criteria andSellerIdEqualTo(String value) {
            addCriterion("seller_id =", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotEqualTo(String value) {
            addCriterion("seller_id <>", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdGreaterThan(String value) {
            addCriterion("seller_id >", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdGreaterThanOrEqualTo(String value) {
            addCriterion("seller_id >=", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLessThan(String value) {
            addCriterion("seller_id <", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLessThanOrEqualTo(String value) {
            addCriterion("seller_id <=", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLike(String value) {
            addCriterion("seller_id like", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotLike(String value) {
            addCriterion("seller_id not like", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdIn(List<String> values) {
            addCriterion("seller_id in", values, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotIn(List<String> values) {
            addCriterion("seller_id not in", values, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdBetween(String value1, String value2) {
            addCriterion("seller_id between", value1, value2, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotBetween(String value1, String value2) {
            addCriterion("seller_id not between", value1, value2, "sellerId");
            return (Criteria) this;
        }

        public Criteria andRefundDateIsNull() {
            addCriterion("refund_date is null");
            return (Criteria) this;
        }

        public Criteria andRefundDateIsNotNull() {
            addCriterion("refund_date is not null");
            return (Criteria) this;
        }

        public Criteria andRefundDateEqualTo(String value) {
            addCriterion("refund_date =", value, "refundDate");
            return (Criteria) this;
        }

        public Criteria andRefundDateNotEqualTo(String value) {
            addCriterion("refund_date <>", value, "refundDate");
            return (Criteria) this;
        }

        public Criteria andRefundDateGreaterThan(String value) {
            addCriterion("refund_date >", value, "refundDate");
            return (Criteria) this;
        }

        public Criteria andRefundDateGreaterThanOrEqualTo(String value) {
            addCriterion("refund_date >=", value, "refundDate");
            return (Criteria) this;
        }

        public Criteria andRefundDateLessThan(String value) {
            addCriterion("refund_date <", value, "refundDate");
            return (Criteria) this;
        }

        public Criteria andRefundDateLessThanOrEqualTo(String value) {
            addCriterion("refund_date <=", value, "refundDate");
            return (Criteria) this;
        }

        public Criteria andRefundDateLike(String value) {
            addCriterion("refund_date like", value, "refundDate");
            return (Criteria) this;
        }

        public Criteria andRefundDateNotLike(String value) {
            addCriterion("refund_date not like", value, "refundDate");
            return (Criteria) this;
        }

        public Criteria andRefundDateIn(List<String> values) {
            addCriterion("refund_date in", values, "refundDate");
            return (Criteria) this;
        }

        public Criteria andRefundDateNotIn(List<String> values) {
            addCriterion("refund_date not in", values, "refundDate");
            return (Criteria) this;
        }

        public Criteria andRefundDateBetween(String value1, String value2) {
            addCriterion("refund_date between", value1, value2, "refundDate");
            return (Criteria) this;
        }

        public Criteria andRefundDateNotBetween(String value1, String value2) {
            addCriterion("refund_date not between", value1, value2, "refundDate");
            return (Criteria) this;
        }

        public Criteria andDetailDataIsNull() {
            addCriterion("detail_data is null");
            return (Criteria) this;
        }

        public Criteria andDetailDataIsNotNull() {
            addCriterion("detail_data is not null");
            return (Criteria) this;
        }

        public Criteria andDetailDataEqualTo(String value) {
            addCriterion("detail_data =", value, "detailData");
            return (Criteria) this;
        }

        public Criteria andDetailDataNotEqualTo(String value) {
            addCriterion("detail_data <>", value, "detailData");
            return (Criteria) this;
        }

        public Criteria andDetailDataGreaterThan(String value) {
            addCriterion("detail_data >", value, "detailData");
            return (Criteria) this;
        }

        public Criteria andDetailDataGreaterThanOrEqualTo(String value) {
            addCriterion("detail_data >=", value, "detailData");
            return (Criteria) this;
        }

        public Criteria andDetailDataLessThan(String value) {
            addCriterion("detail_data <", value, "detailData");
            return (Criteria) this;
        }

        public Criteria andDetailDataLessThanOrEqualTo(String value) {
            addCriterion("detail_data <=", value, "detailData");
            return (Criteria) this;
        }

        public Criteria andDetailDataLike(String value) {
            addCriterion("detail_data like", value, "detailData");
            return (Criteria) this;
        }

        public Criteria andDetailDataNotLike(String value) {
            addCriterion("detail_data not like", value, "detailData");
            return (Criteria) this;
        }

        public Criteria andDetailDataIn(List<String> values) {
            addCriterion("detail_data in", values, "detailData");
            return (Criteria) this;
        }

        public Criteria andDetailDataNotIn(List<String> values) {
            addCriterion("detail_data not in", values, "detailData");
            return (Criteria) this;
        }

        public Criteria andDetailDataBetween(String value1, String value2) {
            addCriterion("detail_data between", value1, value2, "detailData");
            return (Criteria) this;
        }

        public Criteria andDetailDataNotBetween(String value1, String value2) {
            addCriterion("detail_data not between", value1, value2, "detailData");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeIsNull() {
            addCriterion("req_ret_code is null");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeIsNotNull() {
            addCriterion("req_ret_code is not null");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeEqualTo(String value) {
            addCriterion("req_ret_code =", value, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeNotEqualTo(String value) {
            addCriterion("req_ret_code <>", value, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeGreaterThan(String value) {
            addCriterion("req_ret_code >", value, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeGreaterThanOrEqualTo(String value) {
            addCriterion("req_ret_code >=", value, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeLessThan(String value) {
            addCriterion("req_ret_code <", value, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeLessThanOrEqualTo(String value) {
            addCriterion("req_ret_code <=", value, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeLike(String value) {
            addCriterion("req_ret_code like", value, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeNotLike(String value) {
            addCriterion("req_ret_code not like", value, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeIn(List<String> values) {
            addCriterion("req_ret_code in", values, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeNotIn(List<String> values) {
            addCriterion("req_ret_code not in", values, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeBetween(String value1, String value2) {
            addCriterion("req_ret_code between", value1, value2, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetCodeNotBetween(String value1, String value2) {
            addCriterion("req_ret_code not between", value1, value2, "reqRetCode");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgIsNull() {
            addCriterion("req_ret_msg is null");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgIsNotNull() {
            addCriterion("req_ret_msg is not null");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgEqualTo(String value) {
            addCriterion("req_ret_msg =", value, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgNotEqualTo(String value) {
            addCriterion("req_ret_msg <>", value, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgGreaterThan(String value) {
            addCriterion("req_ret_msg >", value, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgGreaterThanOrEqualTo(String value) {
            addCriterion("req_ret_msg >=", value, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgLessThan(String value) {
            addCriterion("req_ret_msg <", value, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgLessThanOrEqualTo(String value) {
            addCriterion("req_ret_msg <=", value, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgLike(String value) {
            addCriterion("req_ret_msg like", value, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgNotLike(String value) {
            addCriterion("req_ret_msg not like", value, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgIn(List<String> values) {
            addCriterion("req_ret_msg in", values, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgNotIn(List<String> values) {
            addCriterion("req_ret_msg not in", values, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgBetween(String value1, String value2) {
            addCriterion("req_ret_msg between", value1, value2, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andReqRetMsgNotBetween(String value1, String value2) {
            addCriterion("req_ret_msg not between", value1, value2, "reqRetMsg");
            return (Criteria) this;
        }

        public Criteria andSuccessNumIsNull() {
            addCriterion("success_num is null");
            return (Criteria) this;
        }

        public Criteria andSuccessNumIsNotNull() {
            addCriterion("success_num is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessNumEqualTo(Short value) {
            addCriterion("success_num =", value, "successNum");
            return (Criteria) this;
        }

        public Criteria andSuccessNumNotEqualTo(Short value) {
            addCriterion("success_num <>", value, "successNum");
            return (Criteria) this;
        }

        public Criteria andSuccessNumGreaterThan(Short value) {
            addCriterion("success_num >", value, "successNum");
            return (Criteria) this;
        }

        public Criteria andSuccessNumGreaterThanOrEqualTo(Short value) {
            addCriterion("success_num >=", value, "successNum");
            return (Criteria) this;
        }

        public Criteria andSuccessNumLessThan(Short value) {
            addCriterion("success_num <", value, "successNum");
            return (Criteria) this;
        }

        public Criteria andSuccessNumLessThanOrEqualTo(Short value) {
            addCriterion("success_num <=", value, "successNum");
            return (Criteria) this;
        }

        public Criteria andSuccessNumIn(List<Short> values) {
            addCriterion("success_num in", values, "successNum");
            return (Criteria) this;
        }

        public Criteria andSuccessNumNotIn(List<Short> values) {
            addCriterion("success_num not in", values, "successNum");
            return (Criteria) this;
        }

        public Criteria andSuccessNumBetween(Short value1, Short value2) {
            addCriterion("success_num between", value1, value2, "successNum");
            return (Criteria) this;
        }

        public Criteria andSuccessNumNotBetween(Short value1, Short value2) {
            addCriterion("success_num not between", value1, value2, "successNum");
            return (Criteria) this;
        }

        public Criteria andResultDetailsIsNull() {
            addCriterion("result_details is null");
            return (Criteria) this;
        }

        public Criteria andResultDetailsIsNotNull() {
            addCriterion("result_details is not null");
            return (Criteria) this;
        }

        public Criteria andResultDetailsEqualTo(String value) {
            addCriterion("result_details =", value, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andResultDetailsNotEqualTo(String value) {
            addCriterion("result_details <>", value, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andResultDetailsGreaterThan(String value) {
            addCriterion("result_details >", value, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andResultDetailsGreaterThanOrEqualTo(String value) {
            addCriterion("result_details >=", value, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andResultDetailsLessThan(String value) {
            addCriterion("result_details <", value, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andResultDetailsLessThanOrEqualTo(String value) {
            addCriterion("result_details <=", value, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andResultDetailsLike(String value) {
            addCriterion("result_details like", value, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andResultDetailsNotLike(String value) {
            addCriterion("result_details not like", value, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andResultDetailsIn(List<String> values) {
            addCriterion("result_details in", values, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andResultDetailsNotIn(List<String> values) {
            addCriterion("result_details not in", values, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andResultDetailsBetween(String value1, String value2) {
            addCriterion("result_details between", value1, value2, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andResultDetailsNotBetween(String value1, String value2) {
            addCriterion("result_details not between", value1, value2, "resultDetails");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgIsNull() {
            addCriterion("trans_ret_msg is null");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgIsNotNull() {
            addCriterion("trans_ret_msg is not null");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgEqualTo(String value) {
            addCriterion("trans_ret_msg =", value, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgNotEqualTo(String value) {
            addCriterion("trans_ret_msg <>", value, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgGreaterThan(String value) {
            addCriterion("trans_ret_msg >", value, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgGreaterThanOrEqualTo(String value) {
            addCriterion("trans_ret_msg >=", value, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgLessThan(String value) {
            addCriterion("trans_ret_msg <", value, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgLessThanOrEqualTo(String value) {
            addCriterion("trans_ret_msg <=", value, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgLike(String value) {
            addCriterion("trans_ret_msg like", value, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgNotLike(String value) {
            addCriterion("trans_ret_msg not like", value, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgIn(List<String> values) {
            addCriterion("trans_ret_msg in", values, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgNotIn(List<String> values) {
            addCriterion("trans_ret_msg not in", values, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgBetween(String value1, String value2) {
            addCriterion("trans_ret_msg between", value1, value2, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andTransRetMsgNotBetween(String value1, String value2) {
            addCriterion("trans_ret_msg not between", value1, value2, "transRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgIsNull() {
            addCriterion("refund_ret_msg is null");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgIsNotNull() {
            addCriterion("refund_ret_msg is not null");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgEqualTo(String value) {
            addCriterion("refund_ret_msg =", value, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgNotEqualTo(String value) {
            addCriterion("refund_ret_msg <>", value, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgGreaterThan(String value) {
            addCriterion("refund_ret_msg >", value, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgGreaterThanOrEqualTo(String value) {
            addCriterion("refund_ret_msg >=", value, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgLessThan(String value) {
            addCriterion("refund_ret_msg <", value, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgLessThanOrEqualTo(String value) {
            addCriterion("refund_ret_msg <=", value, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgLike(String value) {
            addCriterion("refund_ret_msg like", value, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgNotLike(String value) {
            addCriterion("refund_ret_msg not like", value, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgIn(List<String> values) {
            addCriterion("refund_ret_msg in", values, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgNotIn(List<String> values) {
            addCriterion("refund_ret_msg not in", values, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgBetween(String value1, String value2) {
            addCriterion("refund_ret_msg between", value1, value2, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundRetMsgNotBetween(String value1, String value2) {
            addCriterion("refund_ret_msg not between", value1, value2, "refundRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeIsNull() {
            addCriterion("valsign_ret_code is null");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeIsNotNull() {
            addCriterion("valsign_ret_code is not null");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeEqualTo(String value) {
            addCriterion("valsign_ret_code =", value, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeNotEqualTo(String value) {
            addCriterion("valsign_ret_code <>", value, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeGreaterThan(String value) {
            addCriterion("valsign_ret_code >", value, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeGreaterThanOrEqualTo(String value) {
            addCriterion("valsign_ret_code >=", value, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeLessThan(String value) {
            addCriterion("valsign_ret_code <", value, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeLessThanOrEqualTo(String value) {
            addCriterion("valsign_ret_code <=", value, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeLike(String value) {
            addCriterion("valsign_ret_code like", value, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeNotLike(String value) {
            addCriterion("valsign_ret_code not like", value, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeIn(List<String> values) {
            addCriterion("valsign_ret_code in", values, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeNotIn(List<String> values) {
            addCriterion("valsign_ret_code not in", values, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeBetween(String value1, String value2) {
            addCriterion("valsign_ret_code between", value1, value2, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetCodeNotBetween(String value1, String value2) {
            addCriterion("valsign_ret_code not between", value1, value2, "valsignRetCode");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgIsNull() {
            addCriterion("valsign_ret_msg is null");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgIsNotNull() {
            addCriterion("valsign_ret_msg is not null");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgEqualTo(String value) {
            addCriterion("valsign_ret_msg =", value, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgNotEqualTo(String value) {
            addCriterion("valsign_ret_msg <>", value, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgGreaterThan(String value) {
            addCriterion("valsign_ret_msg >", value, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgGreaterThanOrEqualTo(String value) {
            addCriterion("valsign_ret_msg >=", value, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgLessThan(String value) {
            addCriterion("valsign_ret_msg <", value, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgLessThanOrEqualTo(String value) {
            addCriterion("valsign_ret_msg <=", value, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgLike(String value) {
            addCriterion("valsign_ret_msg like", value, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgNotLike(String value) {
            addCriterion("valsign_ret_msg not like", value, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgIn(List<String> values) {
            addCriterion("valsign_ret_msg in", values, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgNotIn(List<String> values) {
            addCriterion("valsign_ret_msg not in", values, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgBetween(String value1, String value2) {
            addCriterion("valsign_ret_msg between", value1, value2, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andValsignRetMsgNotBetween(String value1, String value2) {
            addCriterion("valsign_ret_msg not between", value1, value2, "valsignRetMsg");
            return (Criteria) this;
        }

        public Criteria andRefundStateIsNull() {
            addCriterion("refund_state is null");
            return (Criteria) this;
        }

        public Criteria andRefundStateIsNotNull() {
            addCriterion("refund_state is not null");
            return (Criteria) this;
        }

        public Criteria andRefundStateEqualTo(String value) {
            addCriterion("refund_state =", value, "refundState");
            return (Criteria) this;
        }

        public Criteria andRefundStateNotEqualTo(String value) {
            addCriterion("refund_state <>", value, "refundState");
            return (Criteria) this;
        }

        public Criteria andRefundStateGreaterThan(String value) {
            addCriterion("refund_state >", value, "refundState");
            return (Criteria) this;
        }

        public Criteria andRefundStateGreaterThanOrEqualTo(String value) {
            addCriterion("refund_state >=", value, "refundState");
            return (Criteria) this;
        }

        public Criteria andRefundStateLessThan(String value) {
            addCriterion("refund_state <", value, "refundState");
            return (Criteria) this;
        }

        public Criteria andRefundStateLessThanOrEqualTo(String value) {
            addCriterion("refund_state <=", value, "refundState");
            return (Criteria) this;
        }

        public Criteria andRefundStateLike(String value) {
            addCriterion("refund_state like", value, "refundState");
            return (Criteria) this;
        }

        public Criteria andRefundStateNotLike(String value) {
            addCriterion("refund_state not like", value, "refundState");
            return (Criteria) this;
        }

        public Criteria andRefundStateIn(List<String> values) {
            addCriterion("refund_state in", values, "refundState");
            return (Criteria) this;
        }

        public Criteria andRefundStateNotIn(List<String> values) {
            addCriterion("refund_state not in", values, "refundState");
            return (Criteria) this;
        }

        public Criteria andRefundStateBetween(String value1, String value2) {
            addCriterion("refund_state between", value1, value2, "refundState");
            return (Criteria) this;
        }

        public Criteria andRefundStateNotBetween(String value1, String value2) {
            addCriterion("refund_state not between", value1, value2, "refundState");
            return (Criteria) this;
        }

        public Criteria andAckContentIsNull() {
            addCriterion("ack_content is null");
            return (Criteria) this;
        }

        public Criteria andAckContentIsNotNull() {
            addCriterion("ack_content is not null");
            return (Criteria) this;
        }

        public Criteria andAckContentEqualTo(String value) {
            addCriterion("ack_content =", value, "ackContent");
            return (Criteria) this;
        }

        public Criteria andAckContentNotEqualTo(String value) {
            addCriterion("ack_content <>", value, "ackContent");
            return (Criteria) this;
        }

        public Criteria andAckContentGreaterThan(String value) {
            addCriterion("ack_content >", value, "ackContent");
            return (Criteria) this;
        }

        public Criteria andAckContentGreaterThanOrEqualTo(String value) {
            addCriterion("ack_content >=", value, "ackContent");
            return (Criteria) this;
        }

        public Criteria andAckContentLessThan(String value) {
            addCriterion("ack_content <", value, "ackContent");
            return (Criteria) this;
        }

        public Criteria andAckContentLessThanOrEqualTo(String value) {
            addCriterion("ack_content <=", value, "ackContent");
            return (Criteria) this;
        }

        public Criteria andAckContentLike(String value) {
            addCriterion("ack_content like", value, "ackContent");
            return (Criteria) this;
        }

        public Criteria andAckContentNotLike(String value) {
            addCriterion("ack_content not like", value, "ackContent");
            return (Criteria) this;
        }

        public Criteria andAckContentIn(List<String> values) {
            addCriterion("ack_content in", values, "ackContent");
            return (Criteria) this;
        }

        public Criteria andAckContentNotIn(List<String> values) {
            addCriterion("ack_content not in", values, "ackContent");
            return (Criteria) this;
        }

        public Criteria andAckContentBetween(String value1, String value2) {
            addCriterion("ack_content between", value1, value2, "ackContent");
            return (Criteria) this;
        }

        public Criteria andAckContentNotBetween(String value1, String value2) {
            addCriterion("ack_content not between", value1, value2, "ackContent");
            return (Criteria) this;
        }

        public Criteria andConfirmResultIsNull() {
            addCriterion("confirm_result is null");
            return (Criteria) this;
        }

        public Criteria andConfirmResultIsNotNull() {
            addCriterion("confirm_result is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmResultEqualTo(String value) {
            addCriterion("confirm_result =", value, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andConfirmResultNotEqualTo(String value) {
            addCriterion("confirm_result <>", value, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andConfirmResultGreaterThan(String value) {
            addCriterion("confirm_result >", value, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andConfirmResultGreaterThanOrEqualTo(String value) {
            addCriterion("confirm_result >=", value, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andConfirmResultLessThan(String value) {
            addCriterion("confirm_result <", value, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andConfirmResultLessThanOrEqualTo(String value) {
            addCriterion("confirm_result <=", value, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andConfirmResultLike(String value) {
            addCriterion("confirm_result like", value, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andConfirmResultNotLike(String value) {
            addCriterion("confirm_result not like", value, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andConfirmResultIn(List<String> values) {
            addCriterion("confirm_result in", values, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andConfirmResultNotIn(List<String> values) {
            addCriterion("confirm_result not in", values, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andConfirmResultBetween(String value1, String value2) {
            addCriterion("confirm_result between", value1, value2, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andConfirmResultNotBetween(String value1, String value2) {
            addCriterion("confirm_result not between", value1, value2, "confirmResult");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andRespTimeIsNull() {
            addCriterion("resp_time is null");
            return (Criteria) this;
        }

        public Criteria andRespTimeIsNotNull() {
            addCriterion("resp_time is not null");
            return (Criteria) this;
        }

        public Criteria andRespTimeEqualTo(Date value) {
            addCriterion("resp_time =", value, "respTime");
            return (Criteria) this;
        }

        public Criteria andRespTimeNotEqualTo(Date value) {
            addCriterion("resp_time <>", value, "respTime");
            return (Criteria) this;
        }

        public Criteria andRespTimeGreaterThan(Date value) {
            addCriterion("resp_time >", value, "respTime");
            return (Criteria) this;
        }

        public Criteria andRespTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("resp_time >=", value, "respTime");
            return (Criteria) this;
        }

        public Criteria andRespTimeLessThan(Date value) {
            addCriterion("resp_time <", value, "respTime");
            return (Criteria) this;
        }

        public Criteria andRespTimeLessThanOrEqualTo(Date value) {
            addCriterion("resp_time <=", value, "respTime");
            return (Criteria) this;
        }

        public Criteria andRespTimeIn(List<Date> values) {
            addCriterion("resp_time in", values, "respTime");
            return (Criteria) this;
        }

        public Criteria andRespTimeNotIn(List<Date> values) {
            addCriterion("resp_time not in", values, "respTime");
            return (Criteria) this;
        }

        public Criteria andRespTimeBetween(Date value1, Date value2) {
            addCriterion("resp_time between", value1, value2, "respTime");
            return (Criteria) this;
        }

        public Criteria andRespTimeNotBetween(Date value1, Date value2) {
            addCriterion("resp_time not between", value1, value2, "respTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeIsNull() {
            addCriterion("complete_time is null");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeIsNotNull() {
            addCriterion("complete_time is not null");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeEqualTo(Date value) {
            addCriterion("complete_time =", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotEqualTo(Date value) {
            addCriterion("complete_time <>", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeGreaterThan(Date value) {
            addCriterion("complete_time >", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("complete_time >=", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeLessThan(Date value) {
            addCriterion("complete_time <", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeLessThanOrEqualTo(Date value) {
            addCriterion("complete_time <=", value, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeIn(List<Date> values) {
            addCriterion("complete_time in", values, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotIn(List<Date> values) {
            addCriterion("complete_time not in", values, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeBetween(Date value1, Date value2) {
            addCriterion("complete_time between", value1, value2, "completeTime");
            return (Criteria) this;
        }

        public Criteria andCompleteTimeNotBetween(Date value1, Date value2) {
            addCriterion("complete_time not between", value1, value2, "completeTime");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsIsNull() {
            addCriterion("refund_profit_details is null");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsIsNotNull() {
            addCriterion("refund_profit_details is not null");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsEqualTo(String value) {
            addCriterion("refund_profit_details =", value, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsNotEqualTo(String value) {
            addCriterion("refund_profit_details <>", value, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsGreaterThan(String value) {
            addCriterion("refund_profit_details >", value, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsGreaterThanOrEqualTo(String value) {
            addCriterion("refund_profit_details >=", value, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsLessThan(String value) {
            addCriterion("refund_profit_details <", value, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsLessThanOrEqualTo(String value) {
            addCriterion("refund_profit_details <=", value, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsLike(String value) {
            addCriterion("refund_profit_details like", value, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsNotLike(String value) {
            addCriterion("refund_profit_details not like", value, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsIn(List<String> values) {
            addCriterion("refund_profit_details in", values, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsNotIn(List<String> values) {
            addCriterion("refund_profit_details not in", values, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsBetween(String value1, String value2) {
            addCriterion("refund_profit_details between", value1, value2, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andRefundProfitDetailsNotBetween(String value1, String value2) {
            addCriterion("refund_profit_details not between", value1, value2, "refundProfitDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsIsNull() {
            addCriterion("unfreeze_details is null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsIsNotNull() {
            addCriterion("unfreeze_details is not null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsEqualTo(String value) {
            addCriterion("unfreeze_details =", value, "unfreezeDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsNotEqualTo(String value) {
            addCriterion("unfreeze_details <>", value, "unfreezeDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsGreaterThan(String value) {
            addCriterion("unfreeze_details >", value, "unfreezeDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsGreaterThanOrEqualTo(String value) {
            addCriterion("unfreeze_details >=", value, "unfreezeDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsLessThan(String value) {
            addCriterion("unfreeze_details <", value, "unfreezeDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsLessThanOrEqualTo(String value) {
            addCriterion("unfreeze_details <=", value, "unfreezeDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsLike(String value) {
            addCriterion("unfreeze_details like", value, "unfreezeDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsNotLike(String value) {
            addCriterion("unfreeze_details not like", value, "unfreezeDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsIn(List<String> values) {
            addCriterion("unfreeze_details in", values, "unfreezeDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsNotIn(List<String> values) {
            addCriterion("unfreeze_details not in", values, "unfreezeDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsBetween(String value1, String value2) {
            addCriterion("unfreeze_details between", value1, value2, "unfreezeDetails");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDetailsNotBetween(String value1, String value2) {
            addCriterion("unfreeze_details not between", value1, value2, "unfreezeDetails");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}