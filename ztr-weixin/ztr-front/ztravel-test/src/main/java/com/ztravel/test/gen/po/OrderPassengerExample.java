package com.ztravel.test.gen.po;

import java.util.ArrayList;
import java.util.List;

public class OrderPassengerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderPassengerExample() {
        oredCriteria = new ArrayList<Criteria>();
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

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
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

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
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
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andFirstNameIsNull() {
            addCriterion("first_name is null");
            return (Criteria) this;
        }

        public Criteria andFirstNameIsNotNull() {
            addCriterion("first_name is not null");
            return (Criteria) this;
        }

        public Criteria andFirstNameEqualTo(String value) {
            addCriterion("first_name =", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotEqualTo(String value) {
            addCriterion("first_name <>", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameGreaterThan(String value) {
            addCriterion("first_name >", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameGreaterThanOrEqualTo(String value) {
            addCriterion("first_name >=", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameLessThan(String value) {
            addCriterion("first_name <", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameLessThanOrEqualTo(String value) {
            addCriterion("first_name <=", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameLike(String value) {
            addCriterion("first_name like", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotLike(String value) {
            addCriterion("first_name not like", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameIn(List<String> values) {
            addCriterion("first_name in", values, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotIn(List<String> values) {
            addCriterion("first_name not in", values, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameBetween(String value1, String value2) {
            addCriterion("first_name between", value1, value2, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotBetween(String value1, String value2) {
            addCriterion("first_name not between", value1, value2, "firstName");
            return (Criteria) this;
        }

        public Criteria andLastNameIsNull() {
            addCriterion("last_name is null");
            return (Criteria) this;
        }

        public Criteria andLastNameIsNotNull() {
            addCriterion("last_name is not null");
            return (Criteria) this;
        }

        public Criteria andLastNameEqualTo(String value) {
            addCriterion("last_name =", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameNotEqualTo(String value) {
            addCriterion("last_name <>", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameGreaterThan(String value) {
            addCriterion("last_name >", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameGreaterThanOrEqualTo(String value) {
            addCriterion("last_name >=", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameLessThan(String value) {
            addCriterion("last_name <", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameLessThanOrEqualTo(String value) {
            addCriterion("last_name <=", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameLike(String value) {
            addCriterion("last_name like", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameNotLike(String value) {
            addCriterion("last_name not like", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameIn(List<String> values) {
            addCriterion("last_name in", values, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameNotIn(List<String> values) {
            addCriterion("last_name not in", values, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameBetween(String value1, String value2) {
            addCriterion("last_name between", value1, value2, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameNotBetween(String value1, String value2) {
            addCriterion("last_name not between", value1, value2, "lastName");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeIsNull() {
            addCriterion("passenger_type is null");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeIsNotNull() {
            addCriterion("passenger_type is not null");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeEqualTo(String value) {
            addCriterion("passenger_type =", value, "passengerType");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeNotEqualTo(String value) {
            addCriterion("passenger_type <>", value, "passengerType");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeGreaterThan(String value) {
            addCriterion("passenger_type >", value, "passengerType");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeGreaterThanOrEqualTo(String value) {
            addCriterion("passenger_type >=", value, "passengerType");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeLessThan(String value) {
            addCriterion("passenger_type <", value, "passengerType");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeLessThanOrEqualTo(String value) {
            addCriterion("passenger_type <=", value, "passengerType");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeLike(String value) {
            addCriterion("passenger_type like", value, "passengerType");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeNotLike(String value) {
            addCriterion("passenger_type not like", value, "passengerType");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeIn(List<String> values) {
            addCriterion("passenger_type in", values, "passengerType");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeNotIn(List<String> values) {
            addCriterion("passenger_type not in", values, "passengerType");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeBetween(String value1, String value2) {
            addCriterion("passenger_type between", value1, value2, "passengerType");
            return (Criteria) this;
        }

        public Criteria andPassengerTypeNotBetween(String value1, String value2) {
            addCriterion("passenger_type not between", value1, value2, "passengerType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeIsNull() {
            addCriterion("credential_type is null");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeIsNotNull() {
            addCriterion("credential_type is not null");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeEqualTo(String value) {
            addCriterion("credential_type =", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeNotEqualTo(String value) {
            addCriterion("credential_type <>", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeGreaterThan(String value) {
            addCriterion("credential_type >", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeGreaterThanOrEqualTo(String value) {
            addCriterion("credential_type >=", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeLessThan(String value) {
            addCriterion("credential_type <", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeLessThanOrEqualTo(String value) {
            addCriterion("credential_type <=", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeLike(String value) {
            addCriterion("credential_type like", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeNotLike(String value) {
            addCriterion("credential_type not like", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeIn(List<String> values) {
            addCriterion("credential_type in", values, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeNotIn(List<String> values) {
            addCriterion("credential_type not in", values, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeBetween(String value1, String value2) {
            addCriterion("credential_type between", value1, value2, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeNotBetween(String value1, String value2) {
            addCriterion("credential_type not between", value1, value2, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialNumIsNull() {
            addCriterion("credential_num is null");
            return (Criteria) this;
        }

        public Criteria andCredentialNumIsNotNull() {
            addCriterion("credential_num is not null");
            return (Criteria) this;
        }

        public Criteria andCredentialNumEqualTo(String value) {
            addCriterion("credential_num =", value, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialNumNotEqualTo(String value) {
            addCriterion("credential_num <>", value, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialNumGreaterThan(String value) {
            addCriterion("credential_num >", value, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialNumGreaterThanOrEqualTo(String value) {
            addCriterion("credential_num >=", value, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialNumLessThan(String value) {
            addCriterion("credential_num <", value, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialNumLessThanOrEqualTo(String value) {
            addCriterion("credential_num <=", value, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialNumLike(String value) {
            addCriterion("credential_num like", value, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialNumNotLike(String value) {
            addCriterion("credential_num not like", value, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialNumIn(List<String> values) {
            addCriterion("credential_num in", values, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialNumNotIn(List<String> values) {
            addCriterion("credential_num not in", values, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialNumBetween(String value1, String value2) {
            addCriterion("credential_num between", value1, value2, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialNumNotBetween(String value1, String value2) {
            addCriterion("credential_num not between", value1, value2, "credentialNum");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineIsNull() {
            addCriterion("credential_dead_line is null");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineIsNotNull() {
            addCriterion("credential_dead_line is not null");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineEqualTo(String value) {
            addCriterion("credential_dead_line =", value, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineNotEqualTo(String value) {
            addCriterion("credential_dead_line <>", value, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineGreaterThan(String value) {
            addCriterion("credential_dead_line >", value, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineGreaterThanOrEqualTo(String value) {
            addCriterion("credential_dead_line >=", value, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineLessThan(String value) {
            addCriterion("credential_dead_line <", value, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineLessThanOrEqualTo(String value) {
            addCriterion("credential_dead_line <=", value, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineLike(String value) {
            addCriterion("credential_dead_line like", value, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineNotLike(String value) {
            addCriterion("credential_dead_line not like", value, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineIn(List<String> values) {
            addCriterion("credential_dead_line in", values, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineNotIn(List<String> values) {
            addCriterion("credential_dead_line not in", values, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineBetween(String value1, String value2) {
            addCriterion("credential_dead_line between", value1, value2, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCredentialDeadLineNotBetween(String value1, String value2) {
            addCriterion("credential_dead_line not between", value1, value2, "credentialDeadLine");
            return (Criteria) this;
        }

        public Criteria andCountryIsNull() {
            addCriterion("country is null");
            return (Criteria) this;
        }

        public Criteria andCountryIsNotNull() {
            addCriterion("country is not null");
            return (Criteria) this;
        }

        public Criteria andCountryEqualTo(String value) {
            addCriterion("country =", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotEqualTo(String value) {
            addCriterion("country <>", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThan(String value) {
            addCriterion("country >", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThanOrEqualTo(String value) {
            addCriterion("country >=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThan(String value) {
            addCriterion("country <", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThanOrEqualTo(String value) {
            addCriterion("country <=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLike(String value) {
            addCriterion("country like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotLike(String value) {
            addCriterion("country not like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryIn(List<String> values) {
            addCriterion("country in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotIn(List<String> values) {
            addCriterion("country not in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryBetween(String value1, String value2) {
            addCriterion("country between", value1, value2, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotBetween(String value1, String value2) {
            addCriterion("country not between", value1, value2, "country");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(String value) {
            addCriterion("birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(String value) {
            addCriterion("birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(String value) {
            addCriterion("birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(String value) {
            addCriterion("birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(String value) {
            addCriterion("birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(String value) {
            addCriterion("birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLike(String value) {
            addCriterion("birthday like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotLike(String value) {
            addCriterion("birthday not like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<String> values) {
            addCriterion("birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<String> values) {
            addCriterion("birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(String value1, String value2) {
            addCriterion("birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(String value1, String value2) {
            addCriterion("birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(String value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(String value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(String value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(String value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(String value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(String value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLike(String value) {
            addCriterion("gender like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotLike(String value) {
            addCriterion("gender not like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<String> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<String> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(String value1, String value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(String value1, String value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}