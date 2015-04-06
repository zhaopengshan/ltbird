package com.leadtone.mas.bizplug.security.bean;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class Resources {

    private Long id;
    private String name;
    private String url;
    private String icon;
    private Integer isManagementFun;
    private Long parentId;
    private Long orderNumber;
    private Date createTime;
    private Long createBy;
    private Set<Resources> subResources;
    // 用于页面排序显示
    private List<Resources> sortedSubRes;
    private Integer isMobile;

    @JSONFieldBridge(impl = StringBridge.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Set<Resources> getSubResources() {
        return subResources;
    }

    public void setSubResources(Set<Resources> subResources) {
        this.subResources = subResources;
    }

    public List<Resources> getSortedSubRes() {
        return sortedSubRes;
    }

    public void setSortedSubRes(List<Resources> sortedSubRes) {
        this.sortedSubRes = sortedSubRes;
    }

    public Integer getIsManagementFun() {
        return isManagementFun;
    }

    public void setIsManagementFun(Integer isManagementFun) {
        this.isManagementFun = isManagementFun;
    }

    @Override
    public boolean equals(Object obj) {
        boolean flag = false;
        if (obj != null && Resources.class.isAssignableFrom(obj.getClass())) {
            Resources resource = (Resources) obj;
            flag = new EqualsBuilder()
                    .append(id, resource.getId())
                    .append(name, resource.getName())
                    .isEquals();
        }
        return flag;
    }

    public Integer getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Integer isMobile) {
        this.isMobile = isMobile;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Resources [icon=" + icon + ", id=" + id + ", name=" + name
                + ", orderNumber=" + orderNumber + ", parentId=" + parentId
                + ", subResources=" + subResources + ", url=" + url + "]";
    }
}
