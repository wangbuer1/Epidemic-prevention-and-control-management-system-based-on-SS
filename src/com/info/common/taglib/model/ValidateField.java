package com.info.common.taglib.model;

public class ValidateField {
	public ValidateField() {
		super();
	}
	public ValidateField(String filedName, String filedText,
			String validateType, Integer maxValue, String format,Integer minStr) {
		super();
		this.filedName = filedName;
		this.filedText = filedText;
		this.validateType = validateType;
		this.maxValue = maxValue;
		this.format = format;
		this.minStr = minStr;
	}
	private Integer minStr;    
	private String filedName; 
	private String filedText;     
	private String validateType;   
	private Integer maxValue;                
	private String format;   
	public String getFiledName() {
		return filedName;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	public String getFiledText() {
		return filedText;
	}
	public void setFiledText(String filedText) {
		this.filedText = filedText;
	}
	public String getValidateType() {
		return validateType;
	}
	public void setValidateType(String validateType) {
		this.validateType = validateType;
	}
	public Integer getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Integer getMinStr() {
		return minStr;
	}
	public void setMinStr(Integer minStr) {
		this.minStr = minStr;
	}
}
