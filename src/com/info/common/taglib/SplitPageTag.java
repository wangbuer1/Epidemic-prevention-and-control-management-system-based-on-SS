package com.info.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 *分页标签
 */
public class SplitPageTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4816367582529075039L;
	private String formName = null; //要提交的表单的名称
	private Integer pageIndex ; //当前页数
	private Integer pageCount ; //总条数
	private Integer pageSize;    //分页大小
	private  Logger logger = Logger.getLogger(SplitPageTag.class);

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		if(pageCount % pageSize != 0){
			pageCount = (pageCount / pageSize) + 1;
		} else {
			pageCount = (pageCount / pageSize);
		}
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<div style='text-align:center;'>");
			buffer.append("<script>" +
							"function splitPageSubmitForm(splitSize){\n" +
									"var splitPageForm = document.getElementById('"+ formName +"');\n" +
									"var input=document.createElement('input');\n" +
									"input.type='hidden';\n" +
									"input.name='pageIndex';\n" +
									"input.value=splitSize;\n" +
									"splitPageForm.appendChild(input);" +
									"splitPageForm.submit();" +
							"}" +
						 "</script>");
			buffer.append("")
				  .append("<input type='hidden' id='pageSize' name='pageSize' value='"+ pageSize +"'>");
			if(pageIndex == 1){
				buffer.append("&lt;&lt;&nbsp;<a href='javascript:void(0);' disabled='disabled' style='font-size:15px; color:#808080;'>首页</a>&nbsp;&nbsp;");   //第一页首页禁用
				buffer.append("&lt;&nbsp;<a href='javascript:void(0);' disabled='disabled' style='font-size:15px; color:#808080;'>上一页</a>&nbsp;&nbsp;");      //上一页
			} else {
				buffer.append("&lt;&lt;&nbsp;<a href='javascript:void(0);' style='font-size:15px;' onclick=\"splitPageSubmitForm('1')\">首页</a>&nbsp;&nbsp;");   //首页
				buffer.append("&lt;&nbsp;<a href='javascript:void(0);' style='font-size:15px;' onclick=\"splitPageSubmitForm('"+ ( pageIndex == 1 ? 1 : pageIndex-1) +"')\">上一页</a>&nbsp;&nbsp;");      //上一页
			}
			
			
			
			buffer.append("<select id='pageSelect' name='pageSelect' onchange='splitPageSubmitForm(this.value)'>");  //中间选择框
			for (int i = 0; i < pageCount; i++) {
				if((i+1) == pageIndex){
					buffer.append("<option value='"+(i+1)+"' selected='selected'>第"+ (i+1) +"页&nbsp;&nbsp;</option>");
				}else{
					buffer.append("<option value='"+(i+1)+"'>第"+ (i+1) +"页&nbsp;&nbsp;</option>");
				}
			}
			buffer.append("</select>&nbsp;&nbsp;");
			if(pageCount.intValue() == pageIndex.intValue()){
				buffer.append("<a href='javascript:void(0);' disabled='disabled' style='font-size:15px; color:#808080;'>下一页</a>&nbsp;&gt;&nbsp;&nbsp;");      //下一页
				buffer.append("<a href='javascript:void(0);' disabled='disabled' style='font-size:15px; color:#808080;'>最后一页</a>&nbsp;&gt;&gt;&nbsp;&nbsp;");      //最后一页
			} else {
				buffer.append("<a href='javascript:void(0);' style='font-size:15px;' onclick=\"splitPageSubmitForm('"+(pageIndex.intValue() == pageCount.intValue() ? pageCount : pageIndex + 1) + "')\">下一页</a>&nbsp;&gt;&nbsp;&nbsp;");      //下一页
				buffer.append("<a href='javascript:void(0);' style='font-size:15px;' onclick=\"splitPageSubmitForm('"+ pageCount +"')\">最后一页</a>&nbsp;&gt;&gt;&nbsp;&nbsp;");      //最后一页
			}
			buffer.append("</div>");
			out.println(buffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.debug("分页异常!!!");
		}
		return SKIP_BODY;
	}

	@Override
	public void release() {
		super.release();
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
