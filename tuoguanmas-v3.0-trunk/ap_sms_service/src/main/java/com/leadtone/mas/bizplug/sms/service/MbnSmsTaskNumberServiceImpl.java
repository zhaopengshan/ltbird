package com.leadtone.mas.bizplug.sms.service;

import java.text.DecimalFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsTaskNumber;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsTaskNumberDao;

@Service("mbnSmsTaskNumberService")
@Transactional
public class MbnSmsTaskNumberServiceImpl implements MbnSmsTaskNumberService {
	@Resource
	private MbnSmsTaskNumberDao mbnSmsTaskNumberDao;

	@Override
	public String getTaskNumber(Long merchantPin, String coding) {
		MbnSmsTaskNumber number = mbnSmsTaskNumberDao.getMaxUsed(merchantPin,
				coding);
		// 未使用，返回001
		if (number == null) {
			return "001";
		}
		// 已使用，判断是否<1000
		if (!number.getTaskNumber().equals("999")) {
			String tmp = number.getTaskNumber();
			Long num = Long.parseLong(tmp);
			num++;
			return String.format("%03d", num);
		}
		// 查询已经过期的最小的一个
		MbnSmsTaskNumber lastNum = mbnSmsTaskNumberDao.getExpireLastUsed(
				merchantPin, coding, new Date());
		if (lastNum != null) {
			lastNum.setState(0);
			mbnSmsTaskNumberDao.update(lastNum);
			return lastNum.getTaskNumber();
		}
		// 查询未过期的最小的一个
		MbnSmsTaskNumber minNum = mbnSmsTaskNumberDao.getLastUsed(merchantPin, coding);
		if (minNum != null) {
			return minNum.getTaskNumber();
		}

		return "001";
	}


	@Override
	public String getTaskNumber2(Long merchantPin, String userExtCode) {
		MbnSmsTaskNumber number = mbnSmsTaskNumberDao.getMaxUsed(merchantPin,
				userExtCode);
		// 未使用，返回001
		if (number == null) {
			return "01";
		}
		// 已使用，判断是否<100
		if (!number.getTaskNumber().equals("99")) {
			String tmp = number.getTaskNumber();
			Long num = Long.parseLong(tmp);
			num++;
			return String.format("%02d", num);
		}
		// 查询已经过期的最小的一个
		MbnSmsTaskNumber lastNum = mbnSmsTaskNumberDao.getExpireLastUsed(
				merchantPin, userExtCode, new Date());
		if (lastNum != null) {
			lastNum.setState(0);
			mbnSmsTaskNumberDao.update(lastNum);
			return lastNum.getTaskNumber();
		}
		// 查询未过期的最小的一个
		MbnSmsTaskNumber minNum = mbnSmsTaskNumberDao.getLastUsed(merchantPin, userExtCode);
		if (minNum != null) {
			return minNum.getTaskNumber();
		}

		return "01";
	}
	
	@Override
	public Integer addTaskNumber(MbnSmsTaskNumber num) {
		return mbnSmsTaskNumberDao.insert(num);
	}

}
