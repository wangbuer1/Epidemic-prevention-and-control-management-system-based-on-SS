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

/**
 * 验证标签
 */
public class RadioTag extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9039121023823904238L;

	private String defaultValue;   //默认值
	private String dictionaryType;  //字典类型
	private String onchange;       //选择事件
	private String onclick; 
	
	private boolean disabled;       //禁用   false不禁用 true 禁用
	private String name;           //name
	
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		Tag tag = findTag(dictionaryType);
		
		StringBuffer buffer = new StringBuffer(100);
		List<DataList> dataList = tag.getDataList();
		for (int i = 0,len = dataList.size(); i < len; i++) {
			DataList dictionary = dataList.get(i);
			String dname = dictionary.getFeildName();
			String value = dictionary.getFeildValue();
			
			buffer.append("<label><input type='radio' ");
			if(StringUtils.isNotBlank(name)){
				buffer.append(" name='").append(name).append("'");
			}
			
			if(disabled){
				buffer.append(" disabled='disabled'");
			}
			
			if(StringUtils.isNotBlank(onchange)){
				buffer.append(" onchange='").append(onchange).append("'");
			}
			
			if(StringUtils.isNotBlank(onclick)){
				buffer.append(" onclick='").append(onclick).append("'");
			}
			if(StringUtils.isNotBlank(defaultValue)){
				if(defaultValue.equals(value)){
					buffer.append(" value='").append(value).append("' checked='checked'>").append(dname).append("</label>");
				} else {
					buffer.append(" value='").append(value).append("'>").append(dname).append("</label>");
				}
			} else {
				buffer.append(" value='").append(value).append("'>").append(dname).append("</label>");
			} 
		}
		
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


	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

}
