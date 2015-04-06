package com.leadtone.mas.bizplug.security.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.leadtone.mas.bizplug.util.MasPasswordTool;

public class MasPasswordEncoder implements PasswordEncoder {
	@Override
	public String encodePassword(String rawPass, Object salt)
			throws DataAccessException {
		String result = MasPasswordTool.getEncString(rawPass, salt.toString());
		return result;
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws DataAccessException {
		String encode = encodePassword(rawPass, salt);
		return encPass.equals(encode);
	}

}
