package com.ztravel.paygate.core.po.gen;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaygateClientExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer pgOffset;

    private Integer pgLength;

    public PaygateClientExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected PaygateClientExample(PaygateClientExample example) {
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

        public Criteria andPaygateClientIdIsNull() {
            addCriterion("paygate_client_id is null");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdIsNotNull() {
            addCriterion("paygate_client_id is not null");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdEqualTo(String value) {
            addCriterion("paygate_client_id =", value, "paygateClientId");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdNotEqualTo(String value) {
            addCriterion("paygate_client_id <>", value, "paygateClientId");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdGreaterThan(String value) {
            addCriterion("paygate_client_id >", value, "paygateClientId");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdGreaterThanOrEqualTo(String value) {
            addCriterion("paygate_client_id >=", value, "paygateClientId");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdLessThan(String value) {
            addCriterion("paygate_client_id <", value, "paygateClientId");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdLessThanOrEqualTo(String value) {
            addCriterion("paygate_client_id <=", value, "paygateClientId");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdLike(String value) {
            addCriterion("paygate_client_id like", value, "paygateClientId");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdNotLike(String value) {
            addCriterion("paygate_client_id not like", value, "paygateClientId");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdIn(List<String> values) {
            addCriterion("paygate_client_id in", values, "paygateClientId");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdNotIn(List<String> values) {
            addCriterion("paygate_client_id not in", values, "paygateClientId");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdBetween(String value1, String value2) {
            addCriterion("paygate_client_id between", value1, value2, "paygateClientId");
            return (Criteria) this;
        }

        public Criteria andPaygateClientIdNotBetween(String value1, String value2) {
            addCriterion("paygate_client_id not between", value1, value2, "paygateClientId");
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

        public Criteria andSignKeyIsNull() {
            addCriterion("sign_key is null");
            return (Criteria) this;
        }

        public Criteria andSignKeyIsNotNull() {
            addCriterion("sign_key is not null");
            return (Criteria) this;
        }

        public Criteria andSignKeyEqualTo(String value) {
            addCriterion("sign_key =", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyNotEqualTo(String value) {
            addCriterion("sign_key <>", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyGreaterThan(String value) {
            addCriterion("sign_key >", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyGreaterThanOrEqualTo(String value) {
            addCriterion("sign_key >=", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyLessThan(String value) {
            addCriterion("sign_key <", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyLessThanOrEqualTo(String value) {
            addCriterion("sign_key <=", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyLike(String value) {
            addCriterion("sign_key like", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyNotLike(String value) {
            addCriterion("sign_key not like", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyIn(List<String> values) {
            addCriterion("sign_key in", values, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyNotIn(List<String> values) {
            addCriterion("sign_key not in", values, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyBetween(String value1, String value2) {
            addCriterion("sign_key between", value1, value2, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyNotBetween(String value1, String value2) {
            addCriterion("sign_key not between", value1, value2, "signKey");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptIsNull() {
            addCriterion("supports_encrypt is null");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptIsNotNull() {
            addCriterion("supports_encrypt is not null");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptEqualTo(Boolean value) {
            addCriterion("supports_encrypt =", value, "supportsEncrypt");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptNotEqualTo(Boolean value) {
            addCriterion("supports_encrypt <>", value, "supportsEncrypt");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptGreaterThan(Boolean value) {
            addCriterion("supports_encrypt >", value, "supportsEncrypt");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptGreaterThanOrEqualTo(Boolean value) {
            addCriterion("supports_encrypt >=", value, "supportsEncrypt");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptLessThan(Boolean value) {
            addCriterion("supports_encrypt <", value, "supportsEncrypt");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptLessThanOrEqualTo(Boolean value) {
            addCriterion("supports_encrypt <=", value, "supportsEncrypt");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptIn(List<Boolean> values) {
            addCriterion("supports_encrypt in", values, "supportsEncrypt");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptNotIn(List<Boolean> values) {
            addCriterion("supports_encrypt not in", values, "supportsEncrypt");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptBetween(Boolean value1, Boolean value2) {
            addCriterion("supports_encrypt between", value1, value2, "supportsEncrypt");
            return (Criteria) this;
        }

        public Criteria andSupportsEncryptNotBetween(Boolean value1, Boolean value2) {
            addCriterion("supports_encrypt not between", value1, value2, "supportsEncrypt");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNull() {
            addCriterion("describe is null");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNotNull() {
            addCriterion("describe is not null");
            return (Criteria) this;
        }

        public Criteria andDescribeEqualTo(String value) {
            addCriterion("describe =", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotEqualTo(String value) {
            addCriterion("describe <>", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThan(String value) {
            addCriterion("describe >", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("describe >=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThan(String value) {
            addCriterion("describe <", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThanOrEqualTo(String value) {
            addCriterion("describe <=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLike(String value) {
            addCriterion("describe like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotLike(String value) {
            addCriterion("describe not like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeIn(List<String> values) {
            addCriterion("describe in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotIn(List<String> values) {
            addCriterion("describe not in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeBetween(String value1, String value2) {
            addCriterion("describe between", value1, value2, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotBetween(String value1, String value2) {
            addCriterion("describe not between", value1, value2, "describe");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentIsNull() {
            addCriterion("supports_payment is null");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentIsNotNull() {
            addCriterion("supports_payment is not null");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentEqualTo(Boolean value) {
            addCriterion("supports_payment =", value, "supportsPayment");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentNotEqualTo(Boolean value) {
            addCriterion("supports_payment <>", value, "supportsPayment");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentGreaterThan(Boolean value) {
            addCriterion("supports_payment >", value, "supportsPayment");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentGreaterThanOrEqualTo(Boolean value) {
            addCriterion("supports_payment >=", value, "supportsPayment");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentLessThan(Boolean value) {
            addCriterion("supports_payment <", value, "supportsPayment");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentLessThanOrEqualTo(Boolean value) {
            addCriterion("supports_payment <=", value, "supportsPayment");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentIn(List<Boolean> values) {
            addCriterion("supports_payment in", values, "supportsPayment");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentNotIn(List<Boolean> values) {
            addCriterion("supports_payment not in", values, "supportsPayment");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentBetween(Boolean value1, Boolean value2) {
            addCriterion("supports_payment between", value1, value2, "supportsPayment");
            return (Criteria) this;
        }

        public Criteria andSupportsPaymentNotBetween(Boolean value1, Boolean value2) {
            addCriterion("supports_payment not between", value1, value2, "supportsPayment");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundIsNull() {
            addCriterion("supports_refund is null");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundIsNotNull() {
            addCriterion("supports_refund is not null");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundEqualTo(Boolean value) {
            addCriterion("supports_refund =", value, "supportsRefund");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundNotEqualTo(Boolean value) {
            addCriterion("supports_refund <>", value, "supportsRefund");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundGreaterThan(Boolean value) {
            addCriterion("supports_refund >", value, "supportsRefund");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundGreaterThanOrEqualTo(Boolean value) {
            addCriterion("supports_refund >=", value, "supportsRefund");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundLessThan(Boolean value) {
            addCriterion("supports_refund <", value, "supportsRefund");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundLessThanOrEqualTo(Boolean value) {
            addCriterion("supports_refund <=", value, "supportsRefund");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundIn(List<Boolean> values) {
            addCriterion("supports_refund in", values, "supportsRefund");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundNotIn(List<Boolean> values) {
            addCriterion("supports_refund not in", values, "supportsRefund");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundBetween(Boolean value1, Boolean value2) {
            addCriterion("supports_refund between", value1, value2, "supportsRefund");
            return (Criteria) this;
        }

        public Criteria andSupportsRefundNotBetween(Boolean value1, Boolean value2) {
            addCriterion("supports_refund not between", value1, value2, "supportsRefund");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryIsNull() {
            addCriterion("supports_query is null");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryIsNotNull() {
            addCriterion("supports_query is not null");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryEqualTo(Boolean value) {
            addCriterion("supports_query =", value, "supportsQuery");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryNotEqualTo(Boolean value) {
            addCriterion("supports_query <>", value, "supportsQuery");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryGreaterThan(Boolean value) {
            addCriterion("supports_query >", value, "supportsQuery");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("supports_query >=", value, "supportsQuery");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryLessThan(Boolean value) {
            addCriterion("supports_query <", value, "supportsQuery");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryLessThanOrEqualTo(Boolean value) {
            addCriterion("supports_query <=", value, "supportsQuery");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryIn(List<Boolean> values) {
            addCriterion("supports_query in", values, "supportsQuery");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryNotIn(List<Boolean> values) {
            addCriterion("supports_query not in", values, "supportsQuery");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryBetween(Boolean value1, Boolean value2) {
            addCriterion("supports_query between", value1, value2, "supportsQuery");
            return (Criteria) this;
        }

        public Criteria andSupportsQueryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("supports_query not between", value1, value2, "supportsQuery");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeIsNull() {
            addCriterion("supports_gatetype is null");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeIsNotNull() {
            addCriterion("supports_gatetype is not null");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeEqualTo(String value) {
            addCriterion("supports_gatetype =", value, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeNotEqualTo(String value) {
            addCriterion("supports_gatetype <>", value, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeGreaterThan(String value) {
            addCriterion("supports_gatetype >", value, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeGreaterThanOrEqualTo(String value) {
            addCriterion("supports_gatetype >=", value, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeLessThan(String value) {
            addCriterion("supports_gatetype <", value, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeLessThanOrEqualTo(String value) {
            addCriterion("supports_gatetype <=", value, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeLike(String value) {
            addCriterion("supports_gatetype like", value, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeNotLike(String value) {
            addCriterion("supports_gatetype not like", value, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeIn(List<String> values) {
            addCriterion("supports_gatetype in", values, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeNotIn(List<String> values) {
            addCriterion("supports_gatetype not in", values, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeBetween(String value1, String value2) {
            addCriterion("supports_gatetype between", value1, value2, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andSupportsGatetypeNotBetween(String value1, String value2) {
            addCriterion("supports_gatetype not between", value1, value2, "supportsGatetype");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlIsNull() {
            addCriterion("payment_notify_url is null");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlIsNotNull() {
            addCriterion("payment_notify_url is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlEqualTo(String value) {
            addCriterion("payment_notify_url =", value, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlNotEqualTo(String value) {
            addCriterion("payment_notify_url <>", value, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlGreaterThan(String value) {
            addCriterion("payment_notify_url >", value, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlGreaterThanOrEqualTo(String value) {
            addCriterion("payment_notify_url >=", value, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlLessThan(String value) {
            addCriterion("payment_notify_url <", value, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlLessThanOrEqualTo(String value) {
            addCriterion("payment_notify_url <=", value, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlLike(String value) {
            addCriterion("payment_notify_url like", value, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlNotLike(String value) {
            addCriterion("payment_notify_url not like", value, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlIn(List<String> values) {
            addCriterion("payment_notify_url in", values, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlNotIn(List<String> values) {
            addCriterion("payment_notify_url not in", values, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlBetween(String value1, String value2) {
            addCriterion("payment_notify_url between", value1, value2, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andPaymentNotifyUrlNotBetween(String value1, String value2) {
            addCriterion("payment_notify_url not between", value1, value2, "paymentNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlIsNull() {
            addCriterion("refund_notify_url is null");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlIsNotNull() {
            addCriterion("refund_notify_url is not null");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlEqualTo(String value) {
            addCriterion("refund_notify_url =", value, "refundNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlNotEqualTo(String value) {
            addCriterion("refund_notify_url <>", value, "refundNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlGreaterThan(String value) {
            addCriterion("refund_notify_url >", value, "refundNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlGreaterThanOrEqualTo(String value) {
            addCriterion("refund_notify_url >=", value, "refundNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlLessThan(String value) {
            addCriterion("refund_notify_url <", value, "refundNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlLessThanOrEqualTo(String value) {
            addCriterion("refund_notify_url <=", value, "refundNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlLike(String value) {
            addCriterion("refund_notify_url like", value, "refundNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlNotLike(String value) {
            addCriterion("refund_notify_url not like", value, "refundNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlIn(List<String> values) {
            addCriterion("refund_notify_url in", values, "refundNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlNotIn(List<String> values) {
            addCriterion("refund_notify_url not in", values, "refundNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlBetween(String value1, String value2) {
            addCriterion("refund_notify_url between", value1, value2, "refundNotifyUrl");
            return (Criteria) this;
        }

        public Criteria andRefundNotifyUrlNotBetween(String value1, String value2) {
            addCriterion("refund_notify_url not between", value1, value2, "refundNotifyUrl");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}