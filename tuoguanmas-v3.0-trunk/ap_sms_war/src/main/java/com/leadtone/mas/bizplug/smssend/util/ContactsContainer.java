package com.leadtone.mas.bizplug.smssend.util;

import java.util.ArrayList;
import java.util.List;

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.common.ApSmsConstants;

public class ContactsContainer {
	private List<Contacts> ltList = new ArrayList<Contacts>();

	private List<Contacts> ydList = new ArrayList<Contacts>();

	private List<Contacts> dxList = new ArrayList<Contacts>();

	public boolean addContacts(Contacts contacts, String vonderCode){
		if(ApSmsConstants.YD_CODE.equalsIgnoreCase(vonderCode)){
			addYdContacts(contacts);
			return true;
		}else if(ApSmsConstants.LT_CODE.equalsIgnoreCase(vonderCode)){
			addLtContacts(contacts);
			return true;
		}else if(ApSmsConstants.DX_CODE.equalsIgnoreCase(vonderCode)){
			addDxContacts(contacts);
			return true;
		}
		return false;
	}
	
	public void addLtContacts(Contacts contacts) {
		this.ltList.add(contacts);
	}

	public void addYdContacts(Contacts contacts) {
		this.ydList.add(contacts);
	}

	public void addDxContacts(Contacts contacts) {
		this.dxList.add(contacts);
	}

	public List<Contacts> getLtList() {
		return ltList;
	}

	public void setLtList(List<Contacts> ltList) {
		this.ltList = ltList;
	}

	public List<Contacts> getYdList() {
		return ydList;
	}

	public void setYdList(List<Contacts> ydList) {
		this.ydList = ydList;
	}

	public List<Contacts> getDxList() {
		return dxList;
	}

	public void setDxList(List<Contacts> dxList) {
		this.dxList = dxList;
	}

}
