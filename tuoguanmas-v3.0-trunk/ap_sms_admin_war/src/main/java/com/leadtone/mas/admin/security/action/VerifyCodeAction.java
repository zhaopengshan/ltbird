/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.admin.security.action;

import com.leadtone.mas.admin.util.VerifyCodeUtil;
import com.opensymphony.xwork2.ActionContext;
import java.io.ByteArrayInputStream;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

/**
 *
 * @author blueskybluesea
 */
@ParentPackage("json-default")
@Namespace("/security")
public class VerifyCodeAction extends BaseAction {

    private ByteArrayInputStream inputStream;

    @Action(value = "verifyCode", results = {
        @Result(name = SUCCESS, type = "stream",
        params = {"inputName", "inputStream", "contentType", "image/jpeg"}),
        @Result(name = ERROR, type = "json",
        params = {"root", "entityMap", "contentType", "text/html"})})
    public String generateVerifyCode() {
        try {
            VerifyCodeUtil verifyCodeUtil = VerifyCodeUtil.Instance();
            ActionContext.getContext().getSession().put("verifyCode", verifyCodeUtil.getVerifyCode());
            this.setInputStream(verifyCodeUtil.getVerifyImage());
        } catch (Exception e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public ByteArrayInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }
}
