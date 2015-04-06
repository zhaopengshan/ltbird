package com.leadtone.mas.bizplug.smssend.util;

import java.util.ArrayList;
import java.util.List;

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.common.ApSmsConstants;

public class ContactsContainer {
	private List<Contacts> ltList = new ArrayList<Contacts>();

	private List<Contacts> ydOtherList = new ArrayList<Contacts>();
	private List<Contacts> ydSelfList = new ArrayList<Contacts>();
	
	private List<Contacts> ydList = new ArrayList<Contacts>();

	private List<Contacts> dxList = new ArrayList<Contacts>();
	
	private List<Contacts> tdList = new ArrayList<Contacts>();

	/**
	 * 
	 * @param contacts
	 * @param vonderCode
	 * @return
	 */
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
		}else{
			addTdContacts(contacts);
			return true;
		}
	}
	/**
	 * 
	 * @param contacts
	 * @param vonderCode
	 * @return
	 */
	public boolean removeContacts(Contacts contacts, String vonderCode){
		if(ApSmsConstants.YD_CODE.equalsIgnoreCase(vonderCode)){
			removeYdContacts(contacts);
			return true;
		}else if(ApSmsConstants.LT_CODE.equalsIgnoreCase(vonderCode)){
			removeLtContacts(contacts);
			return true;
		}else if(ApSmsConstants.DX_CODE.equalsIgnoreCase(vonderCode)){
			removeDxContacts(contacts);
			return true;
		}else{
			removeTdContacts(contacts);
			return true;
		}
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

	public void addTdContacts(Contacts contacts) {
		this.tdList.add(contacts);
	}
	
	public void removeLtContacts(Contacts contacts) {
		this.ltList.remove(contacts);
	}

	public void removeYdContacts(Contacts contacts) {
		this.ydList.remove(contacts);
	}

	public void removeDxContacts(Contacts contacts) {
		this.dxList.remove(contacts);
	}

	public void removeTdContacts(Contacts contacts) {
		this.tdList.remove(contacts);
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

	public List<Contacts> getTdList() {
		return tdList;
	}

	public void setTdList(List<Contacts> tdList) {
		this.tdList = tdList;
	}
	public List<Contacts> getYdOtherList() {
		return ydOtherList;
	}
	public void setYdOtherList(List<Contacts> ydOtherList) {
		this.ydOtherList = ydOtherList;
	}
	public List<Contacts> getYdSelfList() {
		return ydSelfList;
	}
	public void setYdSelfList(List<Contacts> ydSelfList) {
		this.ydSelfList = ydSelfList;
	}
	
	public void addYdOtherList(Contacts contacts) {
		this.ydOtherList.add(contacts);
	}
	
	public void addYdSelfList(Contacts contacts) {
		this.ydSelfList.add(contacts);
	}

}
