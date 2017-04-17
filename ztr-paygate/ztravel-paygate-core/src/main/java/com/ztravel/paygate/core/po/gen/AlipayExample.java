package com.ztravel.paygate.core.po.gen;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlipayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer pgOffset;

    private Integer pgLength;

    public AlipayExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected AlipayExample(AlipayExample example) {
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

        public Criteria andPayTypeIsNull() {
            addCriterion("pay_type is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("pay_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(String value) {
            addCriterion("pay_type =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(String value) {
            addCriterion("pay_type <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(String value) {
            addCriterion("pay_type >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("pay_type >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(String value) {
            addCriterion("pay_type <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(String value) {
            addCriterion("pay_type <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLike(String value) {
            addCriterion("pay_type like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotLike(String value) {
            addCriterion("pay_type not like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<String> values) {
            addCriterion("pay_type in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<String> values) {
            addCriterion("pay_type not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(String value1, String value2) {
            addCriterion("pay_type between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(String value1, String value2) {
            addCriterion("pay_type not between", value1, value2, "payType");
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

        public Criteria andTransCommentIsNull() {
            addCriterion("trans_comment is null");
            return (Criteria) this;
        }

        public Criteria andTransCommentIsNotNull() {
            addCriterion("trans_comment is not null");
            return (Criteria) this;
        }

        public Criteria andTransCommentEqualTo(String value) {
            addCriterion("trans_comment =", value, "transComment");
            return (Criteria) this;
        }

        public Criteria andTransCommentNotEqualTo(String value) {
            addCriterion("trans_comment <>", value, "transComment");
            return (Criteria) this;
        }

        public Criteria andTransCommentGreaterThan(String value) {
            addCriterion("trans_comment >", value, "transComment");
            return (Criteria) this;
        }

        public Criteria andTransCommentGreaterThanOrEqualTo(String value) {
            addCriterion("trans_comment >=", value, "transComment");
            return (Criteria) this;
        }

        public Criteria andTransCommentLessThan(String value) {
            addCriterion("trans_comment <", value, "transComment");
            return (Criteria) this;
        }

        public Criteria andTransCommentLessThanOrEqualTo(String value) {
            addCriterion("trans_comment <=", value, "transComment");
            return (Criteria) this;
        }

        public Criteria andTransCommentLike(String value) {
            addCriterion("trans_comment like", value, "transComment");
            return (Criteria) this;
        }

        public Criteria andTransCommentNotLike(String value) {
            addCriterion("trans_comment not like", value, "transComment");
            return (Criteria) this;
        }

        public Criteria andTransCommentIn(List<String> values) {
            addCriterion("trans_comment in", values, "transComment");
            return (Criteria) this;
        }

        public Criteria andTransCommentNotIn(List<String> values) {
            addCriterion("trans_comment not in", values, "transComment");
            return (Criteria) this;
        }

        public Criteria andTransCommentBetween(String value1, String value2) {
            addCriterion("trans_comment between", value1, value2, "transComment");
            return (Criteria) this;
        }

        public Criteria andTransCommentNotBetween(String value1, String value2) {
            addCriterion("trans_comment not between", value1, value2, "transComment");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlIsNull() {
            addCriterion("fg_notify_url is null");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlIsNotNull() {
            addCriterion("fg_notify_url is not null");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlEqualTo(String value) {
            addCriterion("fg_notify_url =", value, "fgNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlNotEqualTo(String value) {
            addCriterion("fg_notify_url <>", value, "fgNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlGreaterThan(String value) {
            addCriterion("fg_notify_url >", value, "fgNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlGreaterThanOrEqualTo(String value) {
            addCriterion("fg_notify_url >=", value, "fgNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlLessThan(String value) {
            addCriterion("fg_notify_url <", value, "fgNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlLessThanOrEqualTo(String value) {
            addCriterion("fg_notify_url <=", value, "fgNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlLike(String value) {
            addCriterion("fg_notify_url like", value, "fgNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlNotLike(String value) {
            addCriterion("fg_notify_url not like", value, "fgNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlIn(List<String> values) {
            addCriterion("fg_notify_url in", values, "fgNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlNotIn(List<String> values) {
            addCriterion("fg_notify_url not in", values, "fgNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlBetween(String value1, String value2) {
            addCriterion("fg_notify_url between", value1, value2, "fgNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andFgNotifyUrlNotBetween(String value1, String value2) {
            addCriterion("fg_notify_url not between", value1, value2, "fgNotifyUrl");
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

        public Criteria andTransDateIsNull() {
            addCriterion("trans_date is null");
            return (Criteria) this;
        }

        public Criteria andTransDateIsNotNull() {
            addCriterion("trans_date is not null");
            return (Criteria) this;
        }

        public Criteria andTransDateEqualTo(String value) {
            addCriterion("trans_date =", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotEqualTo(String value) {
            addCriterion("trans_date <>", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateGreaterThan(String value) {
            addCriterion("trans_date >", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateGreaterThanOrEqualTo(String value) {
            addCriterion("trans_date >=", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateLessThan(String value) {
            addCriterion("trans_date <", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateLessThanOrEqualTo(String value) {
            addCriterion("trans_date <=", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateLike(String value) {
            addCriterion("trans_date like", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotLike(String value) {
            addCriterion("trans_date not like", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateIn(List<String> values) {
            addCriterion("trans_date in", values, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotIn(List<String> values) {
            addCriterion("trans_date not in", values, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateBetween(String value1, String value2) {
            addCriterion("trans_date between", value1, value2, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotBetween(String value1, String value2) {
            addCriterion("trans_date not between", value1, value2, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransTimeIsNull() {
            addCriterion("trans_time is null");
            return (Criteria) this;
        }

        public Criteria andTransTimeIsNotNull() {
            addCriterion("trans_time is not null");
            return (Criteria) this;
        }

        public Criteria andTransTimeEqualTo(String value) {
            addCriterion("trans_time =", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeNotEqualTo(String value) {
            addCriterion("trans_time <>", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeGreaterThan(String value) {
            addCriterion("trans_time >", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_time >=", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeLessThan(String value) {
            addCriterion("trans_time <", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeLessThanOrEqualTo(String value) {
            addCriterion("trans_time <=", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeLike(String value) {
            addCriterion("trans_time like", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeNotLike(String value) {
            addCriterion("trans_time not like", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeIn(List<String> values) {
            addCriterion("trans_time in", values, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeNotIn(List<String> values) {
            addCriterion("trans_time not in", values, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeBetween(String value1, String value2) {
            addCriterion("trans_time between", value1, value2, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeNotBetween(String value1, String value2) {
            addCriterion("trans_time not between", value1, value2, "transTime");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeIsNull() {
            addCriterion("sign_ret_code is null");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeIsNotNull() {
            addCriterion("sign_ret_code is not null");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeEqualTo(String value) {
            addCriterion("sign_ret_code =", value, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeNotEqualTo(String value) {
            addCriterion("sign_ret_code <>", value, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeGreaterThan(String value) {
            addCriterion("sign_ret_code >", value, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sign_ret_code >=", value, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeLessThan(String value) {
            addCriterion("sign_ret_code <", value, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeLessThanOrEqualTo(String value) {
            addCriterion("sign_ret_code <=", value, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeLike(String value) {
            addCriterion("sign_ret_code like", value, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeNotLike(String value) {
            addCriterion("sign_ret_code not like", value, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeIn(List<String> values) {
            addCriterion("sign_ret_code in", values, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeNotIn(List<String> values) {
            addCriterion("sign_ret_code not in", values, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeBetween(String value1, String value2) {
            addCriterion("sign_ret_code between", value1, value2, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetCodeNotBetween(String value1, String value2) {
            addCriterion("sign_ret_code not between", value1, value2, "signRetCode");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgIsNull() {
            addCriterion("sign_ret_msg is null");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgIsNotNull() {
            addCriterion("sign_ret_msg is not null");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgEqualTo(String value) {
            addCriterion("sign_ret_msg =", value, "signRetMsg");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgNotEqualTo(String value) {
            addCriterion("sign_ret_msg <>", value, "signRetMsg");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgGreaterThan(String value) {
            addCriterion("sign_ret_msg >", value, "signRetMsg");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgGreaterThanOrEqualTo(String value) {
            addCriterion("sign_ret_msg >=", value, "signRetMsg");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgLessThan(String value) {
            addCriterion("sign_ret_msg <", value, "signRetMsg");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgLessThanOrEqualTo(String value) {
            addCriterion("sign_ret_msg <=", value, "signRetMsg");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgLike(String value) {
            addCriterion("sign_ret_msg like", value, "signRetMsg");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgNotLike(String value) {
            addCriterion("sign_ret_msg not like", value, "signRetMsg");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgIn(List<String> values) {
            addCriterion("sign_ret_msg in", values, "signRetMsg");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgNotIn(List<String> values) {
            addCriterion("sign_ret_msg not in", values, "signRetMsg");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgBetween(String value1, String value2) {
            addCriterion("sign_ret_msg between", value1, value2, "signRetMsg");
            return (Criteria) this;
        }

        public Criteria andSignRetMsgNotBetween(String value1, String value2) {
            addCriterion("sign_ret_msg not between", value1, value2, "signRetMsg");
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

        public Criteria andBuyerEmailIsNull() {
            addCriterion("buyer_email is null");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailIsNotNull() {
            addCriterion("buyer_email is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailEqualTo(String value) {
            addCriterion("buyer_email =", value, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailNotEqualTo(String value) {
            addCriterion("buyer_email <>", value, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailGreaterThan(String value) {
            addCriterion("buyer_email >", value, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailGreaterThanOrEqualTo(String value) {
            addCriterion("buyer_email >=", value, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailLessThan(String value) {
            addCriterion("buyer_email <", value, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailLessThanOrEqualTo(String value) {
            addCriterion("buyer_email <=", value, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailLike(String value) {
            addCriterion("buyer_email like", value, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailNotLike(String value) {
            addCriterion("buyer_email not like", value, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailIn(List<String> values) {
            addCriterion("buyer_email in", values, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailNotIn(List<String> values) {
            addCriterion("buyer_email not in", values, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailBetween(String value1, String value2) {
            addCriterion("buyer_email between", value1, value2, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerEmailNotBetween(String value1, String value2) {
            addCriterion("buyer_email not between", value1, value2, "buyerEmail");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIsNull() {
            addCriterion("buyer_id is null");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIsNotNull() {
            addCriterion("buyer_id is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerIdEqualTo(String value) {
            addCriterion("buyer_id =", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotEqualTo(String value) {
            addCriterion("buyer_id <>", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdGreaterThan(String value) {
            addCriterion("buyer_id >", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdGreaterThanOrEqualTo(String value) {
            addCriterion("buyer_id >=", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdLessThan(String value) {
            addCriterion("buyer_id <", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdLessThanOrEqualTo(String value) {
            addCriterion("buyer_id <=", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdLike(String value) {
            addCriterion("buyer_id like", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotLike(String value) {
            addCriterion("buyer_id not like", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIn(List<String> values) {
            addCriterion("buyer_id in", values, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotIn(List<String> values) {
            addCriterion("buyer_id not in", values, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdBetween(String value1, String value2) {
            addCriterion("buyer_id between", value1, value2, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotBetween(String value1, String value2) {
            addCriterion("buyer_id not between", value1, value2, "buyerId");
            return (Criteria) this;
        }

        public Criteria andTradeStatusIsNull() {
            addCriterion("trade_status is null");
            return (Criteria) this;
        }

        public Criteria andTradeStatusIsNotNull() {
            addCriterion("trade_status is not null");
            return (Criteria) this;
        }

        public Criteria andTradeStatusEqualTo(String value) {
            addCriterion("trade_status =", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusNotEqualTo(String value) {
            addCriterion("trade_status <>", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusGreaterThan(String value) {
            addCriterion("trade_status >", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusGreaterThanOrEqualTo(String value) {
            addCriterion("trade_status >=", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusLessThan(String value) {
            addCriterion("trade_status <", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusLessThanOrEqualTo(String value) {
            addCriterion("trade_status <=", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusLike(String value) {
            addCriterion("trade_status like", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusNotLike(String value) {
            addCriterion("trade_status not like", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusIn(List<String> values) {
            addCriterion("trade_status in", values, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusNotIn(List<String> values) {
            addCriterion("trade_status not in", values, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusBetween(String value1, String value2) {
            addCriterion("trade_status between", value1, value2, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusNotBetween(String value1, String value2) {
            addCriterion("trade_status not between", value1, value2, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(String value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(String value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(String value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(String value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(String value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(String value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLike(String value) {
            addCriterion("gmt_create like", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotLike(String value) {
            addCriterion("gmt_create not like", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<String> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<String> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(String value1, String value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(String value1, String value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentIsNull() {
            addCriterion("gmt_payment is null");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentIsNotNull() {
            addCriterion("gmt_payment is not null");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentEqualTo(String value) {
            addCriterion("gmt_payment =", value, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentNotEqualTo(String value) {
            addCriterion("gmt_payment <>", value, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentGreaterThan(String value) {
            addCriterion("gmt_payment >", value, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentGreaterThanOrEqualTo(String value) {
            addCriterion("gmt_payment >=", value, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentLessThan(String value) {
            addCriterion("gmt_payment <", value, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentLessThanOrEqualTo(String value) {
            addCriterion("gmt_payment <=", value, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentLike(String value) {
            addCriterion("gmt_payment like", value, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentNotLike(String value) {
            addCriterion("gmt_payment not like", value, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentIn(List<String> values) {
            addCriterion("gmt_payment in", values, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentNotIn(List<String> values) {
            addCriterion("gmt_payment not in", values, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentBetween(String value1, String value2) {
            addCriterion("gmt_payment between", value1, value2, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andGmtPaymentNotBetween(String value1, String value2) {
            addCriterion("gmt_payment not between", value1, value2, "gmtPayment");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeIsNull() {
            addCriterion("notify_time is null");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeIsNotNull() {
            addCriterion("notify_time is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeEqualTo(String value) {
            addCriterion("notify_time =", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeNotEqualTo(String value) {
            addCriterion("notify_time <>", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeGreaterThan(String value) {
            addCriterion("notify_time >", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeGreaterThanOrEqualTo(String value) {
            addCriterion("notify_time >=", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeLessThan(String value) {
            addCriterion("notify_time <", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeLessThanOrEqualTo(String value) {
            addCriterion("notify_time <=", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeLike(String value) {
            addCriterion("notify_time like", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeNotLike(String value) {
            addCriterion("notify_time not like", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeIn(List<String> values) {
            addCriterion("notify_time in", values, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeNotIn(List<String> values) {
            addCriterion("notify_time not in", values, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeBetween(String value1, String value2) {
            addCriterion("notify_time between", value1, value2, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeNotBetween(String value1, String value2) {
            addCriterion("notify_time not between", value1, value2, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andExtraInfosIsNull() {
            addCriterion("extra_infos is null");
            return (Criteria) this;
        }

        public Criteria andExtraInfosIsNotNull() {
            addCriterion("extra_infos is not null");
            return (Criteria) this;
        }

        public Criteria andExtraInfosEqualTo(String value) {
            addCriterion("extra_infos =", value, "extraInfos");
            return (Criteria) this;
        }

        public Criteria andExtraInfosNotEqualTo(String value) {
            addCriterion("extra_infos <>", value, "extraInfos");
            return (Criteria) this;
        }

        public Criteria andExtraInfosGreaterThan(String value) {
            addCriterion("extra_infos >", value, "extraInfos");
            return (Criteria) this;
        }

        public Criteria andExtraInfosGreaterThanOrEqualTo(String value) {
            addCriterion("extra_infos >=", value, "extraInfos");
            return (Criteria) this;
        }

        public Criteria andExtraInfosLessThan(String value) {
            addCriterion("extra_infos <", value, "extraInfos");
            return (Criteria) this;
        }

        public Criteria andExtraInfosLessThanOrEqualTo(String value) {
            addCriterion("extra_infos <=", value, "extraInfos");
            return (Criteria) this;
        }

        public Criteria andExtraInfosLike(String value) {
            addCriterion("extra_infos like", value, "extraInfos");
            return (Criteria) this;
        }

        public Criteria andExtraInfosNotLike(String value) {
            addCriterion("extra_infos not like", value, "extraInfos");
            return (Criteria) this;
        }

        public Criteria andExtraInfosIn(List<String> values) {
            addCriterion("extra_infos in", values, "extraInfos");
            return (Criteria) this;
        }

        public Criteria andExtraInfosNotIn(List<String> values) {
            addCriterion("extra_infos not in", values, "extraInfos");
            return (Criteria) this;
        }

        public Criteria andExtraInfosBetween(String value1, String value2) {
            addCriterion("extra_infos between", value1, value2, "extraInfos");
            return (Criteria) this;
        }

        public Criteria andExtraInfosNotBetween(String value1, String value2) {
            addCriterion("extra_infos not between", value1, value2, "extraInfos");
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

        public Criteria andPayStateIsNull() {
            addCriterion("pay_state is null");
            return (Criteria) this;
        }

        public Criteria andPayStateIsNotNull() {
            addCriterion("pay_state is not null");
            return (Criteria) this;
        }

        public Criteria andPayStateEqualTo(String value) {
            addCriterion("pay_state =", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotEqualTo(String value) {
            addCriterion("pay_state <>", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateGreaterThan(String value) {
            addCriterion("pay_state >", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateGreaterThanOrEqualTo(String value) {
            addCriterion("pay_state >=", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateLessThan(String value) {
            addCriterion("pay_state <", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateLessThanOrEqualTo(String value) {
            addCriterion("pay_state <=", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateLike(String value) {
            addCriterion("pay_state like", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotLike(String value) {
            addCriterion("pay_state not like", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateIn(List<String> values) {
            addCriterion("pay_state in", values, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotIn(List<String> values) {
            addCriterion("pay_state not in", values, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateBetween(String value1, String value2) {
            addCriterion("pay_state between", value1, value2, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotBetween(String value1, String value2) {
            addCriterion("pay_state not between", value1, value2, "payState");
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
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}