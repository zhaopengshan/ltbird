package com.changyuan.misc.model.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbProvisionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbProvisionExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPlatuseridIsNull() {
            addCriterion("platUserId is null");
            return (Criteria) this;
        }

        public Criteria andPlatuseridIsNotNull() {
            addCriterion("platUserId is not null");
            return (Criteria) this;
        }

        public Criteria andPlatuseridEqualTo(Integer value) {
            addCriterion("platUserId =", value, "platuserid");
            return (Criteria) this;
        }

        public Criteria andPlatuseridNotEqualTo(Integer value) {
            addCriterion("platUserId <>", value, "platuserid");
            return (Criteria) this;
        }

        public Criteria andPlatuseridGreaterThan(Integer value) {
            addCriterion("platUserId >", value, "platuserid");
            return (Criteria) this;
        }

        public Criteria andPlatuseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("platUserId >=", value, "platuserid");
            return (Criteria) this;
        }

        public Criteria andPlatuseridLessThan(Integer value) {
            addCriterion("platUserId <", value, "platuserid");
            return (Criteria) this;
        }

        public Criteria andPlatuseridLessThanOrEqualTo(Integer value) {
            addCriterion("platUserId <=", value, "platuserid");
            return (Criteria) this;
        }

        public Criteria andPlatuseridIn(List<Integer> values) {
            addCriterion("platUserId in", values, "platuserid");
            return (Criteria) this;
        }

        public Criteria andPlatuseridNotIn(List<Integer> values) {
            addCriterion("platUserId not in", values, "platuserid");
            return (Criteria) this;
        }

        public Criteria andPlatuseridBetween(Integer value1, Integer value2) {
            addCriterion("platUserId between", value1, value2, "platuserid");
            return (Criteria) this;
        }

        public Criteria andPlatuseridNotBetween(Integer value1, Integer value2) {
            addCriterion("platUserId not between", value1, value2, "platuserid");
            return (Criteria) this;
        }

        public Criteria andMsgtypeIsNull() {
            addCriterion("MsgType is null");
            return (Criteria) this;
        }

        public Criteria andMsgtypeIsNotNull() {
            addCriterion("MsgType is not null");
            return (Criteria) this;
        }

        public Criteria andMsgtypeEqualTo(String value) {
            addCriterion("MsgType =", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeNotEqualTo(String value) {
            addCriterion("MsgType <>", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeGreaterThan(String value) {
            addCriterion("MsgType >", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeGreaterThanOrEqualTo(String value) {
            addCriterion("MsgType >=", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeLessThan(String value) {
            addCriterion("MsgType <", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeLessThanOrEqualTo(String value) {
            addCriterion("MsgType <=", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeLike(String value) {
            addCriterion("MsgType like", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeNotLike(String value) {
            addCriterion("MsgType not like", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeIn(List<String> values) {
            addCriterion("MsgType in", values, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeNotIn(List<String> values) {
            addCriterion("MsgType not in", values, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeBetween(String value1, String value2) {
            addCriterion("MsgType between", value1, value2, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeNotBetween(String value1, String value2) {
            addCriterion("MsgType not between", value1, value2, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnUIsNull() {
            addCriterion("MSISDN_U is null");
            return (Criteria) this;
        }

        public Criteria andMsisdnUIsNotNull() {
            addCriterion("MSISDN_U is not null");
            return (Criteria) this;
        }

        public Criteria andMsisdnUEqualTo(String value) {
            addCriterion("MSISDN_U =", value, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnUNotEqualTo(String value) {
            addCriterion("MSISDN_U <>", value, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnUGreaterThan(String value) {
            addCriterion("MSISDN_U >", value, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnUGreaterThanOrEqualTo(String value) {
            addCriterion("MSISDN_U >=", value, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnULessThan(String value) {
            addCriterion("MSISDN_U <", value, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnULessThanOrEqualTo(String value) {
            addCriterion("MSISDN_U <=", value, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnULike(String value) {
            addCriterion("MSISDN_U like", value, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnUNotLike(String value) {
            addCriterion("MSISDN_U not like", value, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnUIn(List<String> values) {
            addCriterion("MSISDN_U in", values, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnUNotIn(List<String> values) {
            addCriterion("MSISDN_U not in", values, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnUBetween(String value1, String value2) {
            addCriterion("MSISDN_U between", value1, value2, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnUNotBetween(String value1, String value2) {
            addCriterion("MSISDN_U not between", value1, value2, "msisdnU");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeIsNull() {
            addCriterion("MSISDN_U_UserIDType is null");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeIsNotNull() {
            addCriterion("MSISDN_U_UserIDType is not null");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeEqualTo(Integer value) {
            addCriterion("MSISDN_U_UserIDType =", value, "msisdnUUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeNotEqualTo(Integer value) {
            addCriterion("MSISDN_U_UserIDType <>", value, "msisdnUUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeGreaterThan(Integer value) {
            addCriterion("MSISDN_U_UserIDType >", value, "msisdnUUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("MSISDN_U_UserIDType >=", value, "msisdnUUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeLessThan(Integer value) {
            addCriterion("MSISDN_U_UserIDType <", value, "msisdnUUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeLessThanOrEqualTo(Integer value) {
            addCriterion("MSISDN_U_UserIDType <=", value, "msisdnUUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeIn(List<Integer> values) {
            addCriterion("MSISDN_U_UserIDType in", values, "msisdnUUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeNotIn(List<Integer> values) {
            addCriterion("MSISDN_U_UserIDType not in", values, "msisdnUUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeBetween(Integer value1, Integer value2) {
            addCriterion("MSISDN_U_UserIDType between", value1, value2, "msisdnUUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnUUseridtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("MSISDN_U_UserIDType not between", value1, value2, "msisdnUUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeIsNull() {
            addCriterion("MSISDN_U_PseudoCode is null");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeIsNotNull() {
            addCriterion("MSISDN_U_PseudoCode is not null");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeEqualTo(String value) {
            addCriterion("MSISDN_U_PseudoCode =", value, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeNotEqualTo(String value) {
            addCriterion("MSISDN_U_PseudoCode <>", value, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeGreaterThan(String value) {
            addCriterion("MSISDN_U_PseudoCode >", value, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeGreaterThanOrEqualTo(String value) {
            addCriterion("MSISDN_U_PseudoCode >=", value, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeLessThan(String value) {
            addCriterion("MSISDN_U_PseudoCode <", value, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeLessThanOrEqualTo(String value) {
            addCriterion("MSISDN_U_PseudoCode <=", value, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeLike(String value) {
            addCriterion("MSISDN_U_PseudoCode like", value, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeNotLike(String value) {
            addCriterion("MSISDN_U_PseudoCode not like", value, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeIn(List<String> values) {
            addCriterion("MSISDN_U_PseudoCode in", values, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeNotIn(List<String> values) {
            addCriterion("MSISDN_U_PseudoCode not in", values, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeBetween(String value1, String value2) {
            addCriterion("MSISDN_U_PseudoCode between", value1, value2, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnUPseudocodeNotBetween(String value1, String value2) {
            addCriterion("MSISDN_U_PseudoCode not between", value1, value2, "msisdnUPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDIsNull() {
            addCriterion("MSISDN_D is null");
            return (Criteria) this;
        }

        public Criteria andMsisdnDIsNotNull() {
            addCriterion("MSISDN_D is not null");
            return (Criteria) this;
        }

        public Criteria andMsisdnDEqualTo(String value) {
            addCriterion("MSISDN_D =", value, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDNotEqualTo(String value) {
            addCriterion("MSISDN_D <>", value, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDGreaterThan(String value) {
            addCriterion("MSISDN_D >", value, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDGreaterThanOrEqualTo(String value) {
            addCriterion("MSISDN_D >=", value, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDLessThan(String value) {
            addCriterion("MSISDN_D <", value, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDLessThanOrEqualTo(String value) {
            addCriterion("MSISDN_D <=", value, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDLike(String value) {
            addCriterion("MSISDN_D like", value, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDNotLike(String value) {
            addCriterion("MSISDN_D not like", value, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDIn(List<String> values) {
            addCriterion("MSISDN_D in", values, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDNotIn(List<String> values) {
            addCriterion("MSISDN_D not in", values, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDBetween(String value1, String value2) {
            addCriterion("MSISDN_D between", value1, value2, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDNotBetween(String value1, String value2) {
            addCriterion("MSISDN_D not between", value1, value2, "msisdnD");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeIsNull() {
            addCriterion("MSISDN_D_UserIDType is null");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeIsNotNull() {
            addCriterion("MSISDN_D_UserIDType is not null");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeEqualTo(Integer value) {
            addCriterion("MSISDN_D_UserIDType =", value, "msisdnDUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeNotEqualTo(Integer value) {
            addCriterion("MSISDN_D_UserIDType <>", value, "msisdnDUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeGreaterThan(Integer value) {
            addCriterion("MSISDN_D_UserIDType >", value, "msisdnDUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("MSISDN_D_UserIDType >=", value, "msisdnDUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeLessThan(Integer value) {
            addCriterion("MSISDN_D_UserIDType <", value, "msisdnDUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeLessThanOrEqualTo(Integer value) {
            addCriterion("MSISDN_D_UserIDType <=", value, "msisdnDUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeIn(List<Integer> values) {
            addCriterion("MSISDN_D_UserIDType in", values, "msisdnDUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeNotIn(List<Integer> values) {
            addCriterion("MSISDN_D_UserIDType not in", values, "msisdnDUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeBetween(Integer value1, Integer value2) {
            addCriterion("MSISDN_D_UserIDType between", value1, value2, "msisdnDUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnDUseridtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("MSISDN_D_UserIDType not between", value1, value2, "msisdnDUseridtype");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeIsNull() {
            addCriterion("MSISDN_D_PseudoCode is null");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeIsNotNull() {
            addCriterion("MSISDN_D_PseudoCode is not null");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeEqualTo(String value) {
            addCriterion("MSISDN_D_PseudoCode =", value, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeNotEqualTo(String value) {
            addCriterion("MSISDN_D_PseudoCode <>", value, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeGreaterThan(String value) {
            addCriterion("MSISDN_D_PseudoCode >", value, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeGreaterThanOrEqualTo(String value) {
            addCriterion("MSISDN_D_PseudoCode >=", value, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeLessThan(String value) {
            addCriterion("MSISDN_D_PseudoCode <", value, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeLessThanOrEqualTo(String value) {
            addCriterion("MSISDN_D_PseudoCode <=", value, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeLike(String value) {
            addCriterion("MSISDN_D_PseudoCode like", value, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeNotLike(String value) {
            addCriterion("MSISDN_D_PseudoCode not like", value, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeIn(List<String> values) {
            addCriterion("MSISDN_D_PseudoCode in", values, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeNotIn(List<String> values) {
            addCriterion("MSISDN_D_PseudoCode not in", values, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeBetween(String value1, String value2) {
            addCriterion("MSISDN_D_PseudoCode between", value1, value2, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andMsisdnDPseudocodeNotBetween(String value1, String value2) {
            addCriterion("MSISDN_D_PseudoCode not between", value1, value2, "msisdnDPseudocode");
            return (Criteria) this;
        }

        public Criteria andActionidIsNull() {
            addCriterion("ActionID is null");
            return (Criteria) this;
        }

        public Criteria andActionidIsNotNull() {
            addCriterion("ActionID is not null");
            return (Criteria) this;
        }

        public Criteria andActionidEqualTo(Integer value) {
            addCriterion("ActionID =", value, "actionid");
            return (Criteria) this;
        }

        public Criteria andActionidNotEqualTo(Integer value) {
            addCriterion("ActionID <>", value, "actionid");
            return (Criteria) this;
        }

        public Criteria andActionidGreaterThan(Integer value) {
            addCriterion("ActionID >", value, "actionid");
            return (Criteria) this;
        }

        public Criteria andActionidGreaterThanOrEqualTo(Integer value) {
            addCriterion("ActionID >=", value, "actionid");
            return (Criteria) this;
        }

        public Criteria andActionidLessThan(Integer value) {
            addCriterion("ActionID <", value, "actionid");
            return (Criteria) this;
        }

        public Criteria andActionidLessThanOrEqualTo(Integer value) {
            addCriterion("ActionID <=", value, "actionid");
            return (Criteria) this;
        }

        public Criteria andActionidIn(List<Integer> values) {
            addCriterion("ActionID in", values, "actionid");
            return (Criteria) this;
        }

        public Criteria andActionidNotIn(List<Integer> values) {
            addCriterion("ActionID not in", values, "actionid");
            return (Criteria) this;
        }

        public Criteria andActionidBetween(Integer value1, Integer value2) {
            addCriterion("ActionID between", value1, value2, "actionid");
            return (Criteria) this;
        }

        public Criteria andActionidNotBetween(Integer value1, Integer value2) {
            addCriterion("ActionID not between", value1, value2, "actionid");
            return (Criteria) this;
        }

        public Criteria andActionreasonidIsNull() {
            addCriterion("ActionReasonID is null");
            return (Criteria) this;
        }

        public Criteria andActionreasonidIsNotNull() {
            addCriterion("ActionReasonID is not null");
            return (Criteria) this;
        }

        public Criteria andActionreasonidEqualTo(Integer value) {
            addCriterion("ActionReasonID =", value, "actionreasonid");
            return (Criteria) this;
        }

        public Criteria andActionreasonidNotEqualTo(Integer value) {
            addCriterion("ActionReasonID <>", value, "actionreasonid");
            return (Criteria) this;
        }

        public Criteria andActionreasonidGreaterThan(Integer value) {
            addCriterion("ActionReasonID >", value, "actionreasonid");
            return (Criteria) this;
        }

        public Criteria andActionreasonidGreaterThanOrEqualTo(Integer value) {
            addCriterion("ActionReasonID >=", value, "actionreasonid");
            return (Criteria) this;
        }

        public Criteria andActionreasonidLessThan(Integer value) {
            addCriterion("ActionReasonID <", value, "actionreasonid");
            return (Criteria) this;
        }

        public Criteria andActionreasonidLessThanOrEqualTo(Integer value) {
            addCriterion("ActionReasonID <=", value, "actionreasonid");
            return (Criteria) this;
        }

        public Criteria andActionreasonidIn(List<Integer> values) {
            addCriterion("ActionReasonID in", values, "actionreasonid");
            return (Criteria) this;
        }

        public Criteria andActionreasonidNotIn(List<Integer> values) {
            addCriterion("ActionReasonID not in", values, "actionreasonid");
            return (Criteria) this;
        }

        public Criteria andActionreasonidBetween(Integer value1, Integer value2) {
            addCriterion("ActionReasonID between", value1, value2, "actionreasonid");
            return (Criteria) this;
        }

        public Criteria andActionreasonidNotBetween(Integer value1, Integer value2) {
            addCriterion("ActionReasonID not between", value1, value2, "actionreasonid");
            return (Criteria) this;
        }

        public Criteria andPnoIsNull() {
            addCriterion("Pno is null");
            return (Criteria) this;
        }

        public Criteria andPnoIsNotNull() {
            addCriterion("Pno is not null");
            return (Criteria) this;
        }

        public Criteria andPnoEqualTo(String value) {
            addCriterion("Pno =", value, "pno");
            return (Criteria) this;
        }

        public Criteria andPnoNotEqualTo(String value) {
            addCriterion("Pno <>", value, "pno");
            return (Criteria) this;
        }

        public Criteria andPnoGreaterThan(String value) {
            addCriterion("Pno >", value, "pno");
            return (Criteria) this;
        }

        public Criteria andPnoGreaterThanOrEqualTo(String value) {
            addCriterion("Pno >=", value, "pno");
            return (Criteria) this;
        }

        public Criteria andPnoLessThan(String value) {
            addCriterion("Pno <", value, "pno");
            return (Criteria) this;
        }

        public Criteria andPnoLessThanOrEqualTo(String value) {
            addCriterion("Pno <=", value, "pno");
            return (Criteria) this;
        }

        public Criteria andPnoLike(String value) {
            addCriterion("Pno like", value, "pno");
            return (Criteria) this;
        }

        public Criteria andPnoNotLike(String value) {
            addCriterion("Pno not like", value, "pno");
            return (Criteria) this;
        }

        public Criteria andPnoIn(List<String> values) {
            addCriterion("Pno in", values, "pno");
            return (Criteria) this;
        }

        public Criteria andPnoNotIn(List<String> values) {
            addCriterion("Pno not in", values, "pno");
            return (Criteria) this;
        }

        public Criteria andPnoBetween(String value1, String value2) {
            addCriterion("Pno between", value1, value2, "pno");
            return (Criteria) this;
        }

        public Criteria andPnoNotBetween(String value1, String value2) {
            addCriterion("Pno not between", value1, value2, "pno");
            return (Criteria) this;
        }

        public Criteria andSpserviceidIsNull() {
            addCriterion("SPServiceID is null");
            return (Criteria) this;
        }

        public Criteria andSpserviceidIsNotNull() {
            addCriterion("SPServiceID is not null");
            return (Criteria) this;
        }

        public Criteria andSpserviceidEqualTo(String value) {
            addCriterion("SPServiceID =", value, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpserviceidNotEqualTo(String value) {
            addCriterion("SPServiceID <>", value, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpserviceidGreaterThan(String value) {
            addCriterion("SPServiceID >", value, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpserviceidGreaterThanOrEqualTo(String value) {
            addCriterion("SPServiceID >=", value, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpserviceidLessThan(String value) {
            addCriterion("SPServiceID <", value, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpserviceidLessThanOrEqualTo(String value) {
            addCriterion("SPServiceID <=", value, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpserviceidLike(String value) {
            addCriterion("SPServiceID like", value, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpserviceidNotLike(String value) {
            addCriterion("SPServiceID not like", value, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpserviceidIn(List<String> values) {
            addCriterion("SPServiceID in", values, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpserviceidNotIn(List<String> values) {
            addCriterion("SPServiceID not in", values, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpserviceidBetween(String value1, String value2) {
            addCriterion("SPServiceID between", value1, value2, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpserviceidNotBetween(String value1, String value2) {
            addCriterion("SPServiceID not between", value1, value2, "spserviceid");
            return (Criteria) this;
        }

        public Criteria andSpidIsNull() {
            addCriterion("SPID is null");
            return (Criteria) this;
        }

        public Criteria andSpidIsNotNull() {
            addCriterion("SPID is not null");
            return (Criteria) this;
        }

        public Criteria andSpidEqualTo(String value) {
            addCriterion("SPID =", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidNotEqualTo(String value) {
            addCriterion("SPID <>", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidGreaterThan(String value) {
            addCriterion("SPID >", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidGreaterThanOrEqualTo(String value) {
            addCriterion("SPID >=", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidLessThan(String value) {
            addCriterion("SPID <", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidLessThanOrEqualTo(String value) {
            addCriterion("SPID <=", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidLike(String value) {
            addCriterion("SPID like", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidNotLike(String value) {
            addCriterion("SPID not like", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidIn(List<String> values) {
            addCriterion("SPID in", values, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidNotIn(List<String> values) {
            addCriterion("SPID not in", values, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidBetween(String value1, String value2) {
            addCriterion("SPID between", value1, value2, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidNotBetween(String value1, String value2) {
            addCriterion("SPID not between", value1, value2, "spid");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("Version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("Version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("Version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("Version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("Version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("Version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("Version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("Version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("Version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("Version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("Version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("Version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("Version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("Version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createtime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createtime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createtime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createtime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTransactionidIsNull() {
            addCriterion("TransactionID is null");
            return (Criteria) this;
        }

        public Criteria andTransactionidIsNotNull() {
            addCriterion("TransactionID is not null");
            return (Criteria) this;
        }

        public Criteria andTransactionidEqualTo(String value) {
            addCriterion("TransactionID =", value, "transactionid");
            return (Criteria) this;
        }

        public Criteria andTransactionidNotEqualTo(String value) {
            addCriterion("TransactionID <>", value, "transactionid");
            return (Criteria) this;
        }

        public Criteria andTransactionidGreaterThan(String value) {
            addCriterion("TransactionID >", value, "transactionid");
            return (Criteria) this;
        }

        public Criteria andTransactionidGreaterThanOrEqualTo(String value) {
            addCriterion("TransactionID >=", value, "transactionid");
            return (Criteria) this;
        }

        public Criteria andTransactionidLessThan(String value) {
            addCriterion("TransactionID <", value, "transactionid");
            return (Criteria) this;
        }

        public Criteria andTransactionidLessThanOrEqualTo(String value) {
            addCriterion("TransactionID <=", value, "transactionid");
            return (Criteria) this;
        }

        public Criteria andTransactionidLike(String value) {
            addCriterion("TransactionID like", value, "transactionid");
            return (Criteria) this;
        }

        public Criteria andTransactionidNotLike(String value) {
            addCriterion("TransactionID not like", value, "transactionid");
            return (Criteria) this;
        }

        public Criteria andTransactionidIn(List<String> values) {
            addCriterion("TransactionID in", values, "transactionid");
            return (Criteria) this;
        }

        public Criteria andTransactionidNotIn(List<String> values) {
            addCriterion("TransactionID not in", values, "transactionid");
            return (Criteria) this;
        }

        public Criteria andTransactionidBetween(String value1, String value2) {
            addCriterion("TransactionID between", value1, value2, "transactionid");
            return (Criteria) this;
        }

        public Criteria andTransactionidNotBetween(String value1, String value2) {
            addCriterion("TransactionID not between", value1, value2, "transactionid");
            return (Criteria) this;
        }

        public Criteria andLinkidIsNull() {
            addCriterion("LinkID is null");
            return (Criteria) this;
        }

        public Criteria andLinkidIsNotNull() {
            addCriterion("LinkID is not null");
            return (Criteria) this;
        }

        public Criteria andLinkidEqualTo(String value) {
            addCriterion("LinkID =", value, "linkid");
            return (Criteria) this;
        }

        public Criteria andLinkidNotEqualTo(String value) {
            addCriterion("LinkID <>", value, "linkid");
            return (Criteria) this;
        }

        public Criteria andLinkidGreaterThan(String value) {
            addCriterion("LinkID >", value, "linkid");
            return (Criteria) this;
        }

        public Criteria andLinkidGreaterThanOrEqualTo(String value) {
            addCriterion("LinkID >=", value, "linkid");
            return (Criteria) this;
        }

        public Criteria andLinkidLessThan(String value) {
            addCriterion("LinkID <", value, "linkid");
            return (Criteria) this;
        }

        public Criteria andLinkidLessThanOrEqualTo(String value) {
            addCriterion("LinkID <=", value, "linkid");
            return (Criteria) this;
        }

        public Criteria andLinkidLike(String value) {
            addCriterion("LinkID like", value, "linkid");
            return (Criteria) this;
        }

        public Criteria andLinkidNotLike(String value) {
            addCriterion("LinkID not like", value, "linkid");
            return (Criteria) this;
        }

        public Criteria andLinkidIn(List<String> values) {
            addCriterion("LinkID in", values, "linkid");
            return (Criteria) this;
        }

        public Criteria andLinkidNotIn(List<String> values) {
            addCriterion("LinkID not in", values, "linkid");
            return (Criteria) this;
        }

        public Criteria andLinkidBetween(String value1, String value2) {
            addCriterion("LinkID between", value1, value2, "linkid");
            return (Criteria) this;
        }

        public Criteria andLinkidNotBetween(String value1, String value2) {
            addCriterion("LinkID not between", value1, value2, "linkid");
            return (Criteria) this;
        }

        public Criteria andFeaturestrIsNull() {
            addCriterion("FeatureStr is null");
            return (Criteria) this;
        }

        public Criteria andFeaturestrIsNotNull() {
            addCriterion("FeatureStr is not null");
            return (Criteria) this;
        }

        public Criteria andFeaturestrEqualTo(String value) {
            addCriterion("FeatureStr =", value, "featurestr");
            return (Criteria) this;
        }

        public Criteria andFeaturestrNotEqualTo(String value) {
            addCriterion("FeatureStr <>", value, "featurestr");
            return (Criteria) this;
        }

        public Criteria andFeaturestrGreaterThan(String value) {
            addCriterion("FeatureStr >", value, "featurestr");
            return (Criteria) this;
        }

        public Criteria andFeaturestrGreaterThanOrEqualTo(String value) {
            addCriterion("FeatureStr >=", value, "featurestr");
            return (Criteria) this;
        }

        public Criteria andFeaturestrLessThan(String value) {
            addCriterion("FeatureStr <", value, "featurestr");
            return (Criteria) this;
        }

        public Criteria andFeaturestrLessThanOrEqualTo(String value) {
            addCriterion("FeatureStr <=", value, "featurestr");
            return (Criteria) this;
        }

        public Criteria andFeaturestrLike(String value) {
            addCriterion("FeatureStr like", value, "featurestr");
            return (Criteria) this;
        }

        public Criteria andFeaturestrNotLike(String value) {
            addCriterion("FeatureStr not like", value, "featurestr");
            return (Criteria) this;
        }

        public Criteria andFeaturestrIn(List<String> values) {
            addCriterion("FeatureStr in", values, "featurestr");
            return (Criteria) this;
        }

        public Criteria andFeaturestrNotIn(List<String> values) {
            addCriterion("FeatureStr not in", values, "featurestr");
            return (Criteria) this;
        }

        public Criteria andFeaturestrBetween(String value1, String value2) {
            addCriterion("FeatureStr between", value1, value2, "featurestr");
            return (Criteria) this;
        }

        public Criteria andFeaturestrNotBetween(String value1, String value2) {
            addCriterion("FeatureStr not between", value1, value2, "featurestr");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeIsNull() {
            addCriterion("Send_DeviceType is null");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeIsNotNull() {
            addCriterion("Send_DeviceType is not null");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeEqualTo(Integer value) {
            addCriterion("Send_DeviceType =", value, "sendDevicetype");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeNotEqualTo(Integer value) {
            addCriterion("Send_DeviceType <>", value, "sendDevicetype");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeGreaterThan(Integer value) {
            addCriterion("Send_DeviceType >", value, "sendDevicetype");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("Send_DeviceType >=", value, "sendDevicetype");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeLessThan(Integer value) {
            addCriterion("Send_DeviceType <", value, "sendDevicetype");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeLessThanOrEqualTo(Integer value) {
            addCriterion("Send_DeviceType <=", value, "sendDevicetype");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeIn(List<Integer> values) {
            addCriterion("Send_DeviceType in", values, "sendDevicetype");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeNotIn(List<Integer> values) {
            addCriterion("Send_DeviceType not in", values, "sendDevicetype");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeBetween(Integer value1, Integer value2) {
            addCriterion("Send_DeviceType between", value1, value2, "sendDevicetype");
            return (Criteria) this;
        }

        public Criteria andSendDevicetypeNotBetween(Integer value1, Integer value2) {
            addCriterion("Send_DeviceType not between", value1, value2, "sendDevicetype");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidIsNull() {
            addCriterion("Send_DeviceID is null");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidIsNotNull() {
            addCriterion("Send_DeviceID is not null");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidEqualTo(String value) {
            addCriterion("Send_DeviceID =", value, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidNotEqualTo(String value) {
            addCriterion("Send_DeviceID <>", value, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidGreaterThan(String value) {
            addCriterion("Send_DeviceID >", value, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidGreaterThanOrEqualTo(String value) {
            addCriterion("Send_DeviceID >=", value, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidLessThan(String value) {
            addCriterion("Send_DeviceID <", value, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidLessThanOrEqualTo(String value) {
            addCriterion("Send_DeviceID <=", value, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidLike(String value) {
            addCriterion("Send_DeviceID like", value, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidNotLike(String value) {
            addCriterion("Send_DeviceID not like", value, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidIn(List<String> values) {
            addCriterion("Send_DeviceID in", values, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidNotIn(List<String> values) {
            addCriterion("Send_DeviceID not in", values, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidBetween(String value1, String value2) {
            addCriterion("Send_DeviceID between", value1, value2, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andSendDeviceidNotBetween(String value1, String value2) {
            addCriterion("Send_DeviceID not between", value1, value2, "sendDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeIsNull() {
            addCriterion("Dest_DeviceType is null");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeIsNotNull() {
            addCriterion("Dest_DeviceType is not null");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeEqualTo(Integer value) {
            addCriterion("Dest_DeviceType =", value, "destDevicetype");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeNotEqualTo(Integer value) {
            addCriterion("Dest_DeviceType <>", value, "destDevicetype");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeGreaterThan(Integer value) {
            addCriterion("Dest_DeviceType >", value, "destDevicetype");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("Dest_DeviceType >=", value, "destDevicetype");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeLessThan(Integer value) {
            addCriterion("Dest_DeviceType <", value, "destDevicetype");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeLessThanOrEqualTo(Integer value) {
            addCriterion("Dest_DeviceType <=", value, "destDevicetype");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeIn(List<Integer> values) {
            addCriterion("Dest_DeviceType in", values, "destDevicetype");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeNotIn(List<Integer> values) {
            addCriterion("Dest_DeviceType not in", values, "destDevicetype");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeBetween(Integer value1, Integer value2) {
            addCriterion("Dest_DeviceType between", value1, value2, "destDevicetype");
            return (Criteria) this;
        }

        public Criteria andDestDevicetypeNotBetween(Integer value1, Integer value2) {
            addCriterion("Dest_DeviceType not between", value1, value2, "destDevicetype");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidIsNull() {
            addCriterion("Dest_DeviceID is null");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidIsNotNull() {
            addCriterion("Dest_DeviceID is not null");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidEqualTo(String value) {
            addCriterion("Dest_DeviceID =", value, "destDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidNotEqualTo(String value) {
            addCriterion("Dest_DeviceID <>", value, "destDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidGreaterThan(String value) {
            addCriterion("Dest_DeviceID >", value, "destDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidGreaterThanOrEqualTo(String value) {
            addCriterion("Dest_DeviceID >=", value, "destDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidLessThan(String value) {
            addCriterion("Dest_DeviceID <", value, "destDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidLessThanOrEqualTo(String value) {
            addCriterion("Dest_DeviceID <=", value, "destDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidLike(String value) {
            addCriterion("Dest_DeviceID like", value, "destDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidNotLike(String value) {
            addCriterion("Dest_DeviceID not like", value, "destDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidIn(List<String> values) {
            addCriterion("Dest_DeviceID in", values, "destDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidNotIn(List<String> values) {
            addCriterion("Dest_DeviceID not in", values, "destDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidBetween(String value1, String value2) {
            addCriterion("Dest_DeviceID between", value1, value2, "destDeviceid");
            return (Criteria) this;
        }

        public Criteria andDestDeviceidNotBetween(String value1, String value2) {
            addCriterion("Dest_DeviceID not between", value1, value2, "destDeviceid");
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