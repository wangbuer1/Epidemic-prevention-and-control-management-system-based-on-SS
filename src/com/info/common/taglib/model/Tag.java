package com.info.common.taglib.model;

import java.util.List;

public class Tag {
	private String tagId;
	private String tagType;
	private List<DataList> dataList;
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public List<DataList> getDataList() {
		return dataList;
	}
	public void setDataList(List<DataList> dataList) {
		this.dataList = dataList;
	}
	public Tag() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Tag(String tagId, String tagType, List<DataList> dataList) {
		super();
		this.tagId = tagId;
		this.tagType = tagType;
		this.dataList = dataList;
	}
	@Override
	public String toString() {
		String str = "Tag [tagId=" + tagId + ", tagType=" + tagType;
		for (DataList d : dataList) {
			str += " , DataList = " + d;
		}
		return str;
	}
	
	
}
