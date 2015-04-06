package com.leadtone.mas.bizplug.schedule.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/schedule")
public class ScheduleAction extends BaseAction {

	@Action(value = "write", results = {
			@Result(name = SUCCESS, location = "/sms/schedule/jsp/schedule_write.jsp"),
			@Result(name = INPUT, location = "/login.jsp") })
	public String writeSms() {
		return SUCCESS;
	}
}
