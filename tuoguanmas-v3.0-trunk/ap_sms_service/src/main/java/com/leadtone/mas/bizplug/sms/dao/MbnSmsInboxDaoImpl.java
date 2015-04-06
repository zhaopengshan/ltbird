package com.leadtone.mas.bizplug.sms.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSendVO;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseDaoImpl;

@Repository(value = "mbnSmsInboxDao")
@SuppressWarnings("unchecked")
public class MbnSmsInboxDaoImpl extends SmsBaseDaoImpl<MbnSmsInbox, Long> implements MbnSmsInboxDao {

    protected static final String REPLYPAGE = ".replyPage";
    protected static final String REPLYPAGECOUNT = ".replyPageCount";
    protected static final String GETBYPKS = ".getByPks";
    protected static final String GETINBOXBYCODING = ".getIndoxBycoding";
    protected static final String FOLLOWPAGE = ".followPage";
    protected static final String EXPORT = ".export";
    protected static final String GETINBOXALLINFO = ".getIndoxAllInfo";

    @Override
    public List<MbnSmsInbox> followPage(HashMap<String, Object> page) {
        return getSqlMapClientTemplate()
                .queryForList(this.sqlMapNamespace + FOLLOWPAGE, page);
    }

    @Override
    public List<MbnSmsInbox> getByPks(Long[] ids) {
        return getSqlMapClientTemplate()
                .queryForList(this.sqlMapNamespace + GETBYPKS, ids);
    }

    @Override
    public Page pageVO(PageUtil pageUtil) {
        Integer recordes = this.pageCount(pageUtil);
        Object results = null;
        if (recordes > 0) {
            results = getSqlMapClientTemplate()
                    .queryForList(this.sqlMapNamespace + ".pageVO", pageUtil);
        }
        return new Page(pageUtil.getPageSize(), pageUtil.getStart(), recordes, results);
    }

    @Override
    public Page replyPage(PageUtil pageUtil) {
        // 总数
        int resultCount = replyPageCount(pageUtil);
        Object data = null;
        if (resultCount != 0) {
            data = getSqlMapClientTemplate().queryForList(this.sqlMapNamespace + REPLYPAGE, pageUtil);
        }
        return new Page(pageUtil.getPageSize(), pageUtil.getStart(), resultCount, data);
    }

    @Override
    public Integer replyPageCount(PageUtil pageUtil) {
        //
        return (Integer) getSqlMapClientTemplate().queryForObject(this.sqlMapNamespace + REPLYPAGECOUNT, pageUtil);
    }

    @Override
    public List<MbnSmsInbox> getIndoxBycoding(String coding) {
        //  
        return getSqlMapClientTemplate().queryForList(this.sqlMapNamespace + GETINBOXBYCODING, coding);
    }

    @Override
    public Page export(PageUtil pageUtil) {
        Object data = null;
        data = getSqlMapClientTemplate().queryForList(this.sqlMapNamespace + EXPORT, pageUtil);
        return new Page(pageUtil.getPageSize(), pageUtil.getStart(), 0, data);
    }

    public List<MbnSmsInbox> getInboxAllInfo() {
        return getSqlMapClientTemplate().queryForList(this.sqlMapNamespace + GETINBOXALLINFO);
    }

    @Override
    public Page statisticQuery(HashMap<String, Object> param) {
        Integer recordes = (Integer) getSqlMapClientTemplate().queryForObject(
                this.sqlMapNamespace + ".pageCountSummary", param);
        Object results = null;
        if (recordes > 0) {
            results = getSqlMapClientTemplate()
                    .queryForList(this.sqlMapNamespace + ".pageSummary", param);
        }
        return new Page((Integer) param.get("pageSize"), (Integer) param.get("startPage"), recordes, results);
    }

    @Override
    public List<MbnSmsInbox> statisticSummary(HashMap<String, Object> param) {
        return getSqlMapClientTemplate().queryForList(this.sqlMapNamespace + ".pageSummary", param);
    }
}
