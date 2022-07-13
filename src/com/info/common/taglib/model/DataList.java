package com.info.common.taglib.model;

public class DataList {
	public DataList(String feildName, String feildValue) {
		super();
		this.feildName = feildName;
		this.feildValue = feildValue;
	}
	private String feildName;
	private String feildValue;
	public String getFeildName() {
		return feildName;
	}
	public void setFeildName(String feildName) {
		this.feildName = feildName;
	}
	public DataList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFeildValue() {
		return feildValue;
	}
	public void setFeildValue(String feildValue) {
		this.feildValue = feildValue;
	}
	@Override
	public String toString() {
		return "DataList [feildName=" + feildName + ", feildValue="
				+ feildValue + "]";
	}
	
	
}
