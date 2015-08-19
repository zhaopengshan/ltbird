package com.changyuan.misc.dao;

import javax.annotation.Resource;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.changyuan.misc.model.mapper.TbUserMapper;

/**
 * 结算表 操作DAO
 *
 * @author sunyadong
 * @Company leadtone.com 2015年8月13日
 *
 */
// @Transactional
@Repository(value = "tbUserDao")
public class TbUserDao extends SqlSessionDaoSupport {
    @Resource
    private TbUserMapper tbUserMapper;
}
