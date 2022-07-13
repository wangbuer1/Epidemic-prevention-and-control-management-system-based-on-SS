package com.info.common.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.info.common.taglib.model.DataList;
import com.info.common.taglib.model.Tag;
import com.info.common.util.ResolveTagXML;

public class DropDownTag extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8208902074630842476L;
	
	
	private String defaultValue;     //默认值
	private String dictionaryType;   //字典类型
	private String onchange;         //选择事件
	private String styleClass;       //样式
	private boolean disabled;        //禁用   false不禁用 true 禁用
	private String styleId;          //Id
	private String name;             //name
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDictionaryType() {
		return dictionaryType;
	}
	public void setDictionaryType(String dictionaryType) {
		this.dictionaryType = dictionaryType;
	}
	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int doStartTag() throws JspException {
		Tag tag = findTag(dictionaryType);
		
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("<select ");
		if(StringUtils.isNotBlank(styleId)){
			buffer.append(" id='").append(styleId).append("'");
		}
		
		if(StringUtils.isNotBlank(name)){
			buffer.append(" name='").append(name).append("'");
		}
		
		if(disabled){
			buffer.append(" disabled='disabled'");
		}
		
		if(StringUtils.isNotBlank(styleClass)){
			buffer.append(" style='").append(styleClass).append("'");
		}
		
		if(StringUtils.isNotBlank(onchange)){
			buffer.append(" onchange='").append(onchange).append("'");
		}
		buffer.append(" >");
		buffer.append("<option value=''>请选择</option>");
		if(tag != null){
			List<DataList> dataList = tag.getDataList();
			for (int i = 0,len = dataList.size(); i < len; i++) {
				DataList dictionary = dataList.get(i);
				String dname = dictionary.getFeildName();
				String value = dictionary.getFeildValue();
				
				if(StringUtils.isNotBlank(defaultValue)){
					if(defaultValue.equals(value)){
						buffer.append("<option selected='selected' value='").append(value).append("'>").append(dname).append("</option>");
					} else {
						buffer.append("<option value='").append(value).append("'>").append(dname).append("</option>");
					}
				} else {
					buffer.append("<option value='").append(value).append("'>").append(dname).append("</option>");
				} 
			}
		}
		buffer.append("</select>");
		JspWriter out = pageContext.getOut();
		try {
			out.write(buffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	private Tag findTag(String type) {
		return ResolveTagXML.getTag(type);
	}


	@Override
	public void release() {
		super.release();
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
