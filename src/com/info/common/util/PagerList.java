package com.info.common.util;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
/**
 * 分页辅助对象
 * @createTime 2010-01-30 17:56
 * @param request
 */
public class PagerList  {
	private  Logger logger = Logger.getLogger(PagerList.class);
	
	private int pageIndex;   //分页码
	private int pageSize;    //分页大小
	private int pageStrart;  //起始分页位置
	private long totalRecord;//总条数
	private List pageList;   //分页结果集
	private String pageName; //分页名称
	private Object obj;      //备用属性：防止以后方法中增加查询参数避免service和dao中的方法增加 
    //该构造方法是支持查询分页
	public PagerList(HttpServletRequest request) {
		this.pageIndex = this.getPageIndex(request);// 分页码
		this.pageSize = this.getPageSize(request);// 分页大小
		this.pageStrart=(pageIndex-1) * pageSize;   //起始分页位置
		logger.info("pageSize : " + pageSize);
		logger.info("pageIndex : " + pageIndex);
	}
	
	public PagerList(HttpServletRequest request,int pageSize) {
		this.pageIndex = this.getPageIndex(request);// 分页码
		this.pageSize = pageSize;// 分页大小
		this.pageStrart=(pageIndex-1)*pageSize;   //起始分页位置
		logger.info("pageSize : " + pageSize);
		logger.info("pageIndex : " + pageIndex);
	}
	
    //该构造方法是查询所有的列表数据或者是导出excel功能
	//利用pageSize=0来做判断是分页还是查询所有，导出excel功能
	public PagerList() {

	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	protected int getPageIndex(HttpServletRequest request) {
		int pageIndex = StringHelper.convert2Int(request.getParameter("pageIndex"));
		if (pageIndex == 0)
			pageIndex = 1;
		return pageIndex;
	}

	protected int getPageSize(HttpServletRequest request) {
		int pageSize = StringHelper.convert2Int(request
				.getParameter("pageSize"));
		if (pageSize == 0)
			pageSize = 10;
		request.setAttribute("pageSize", Integer.valueOf(pageSize));
		return pageSize;
	}

	public int getPageStrart() {
		return pageStrart;
	}

	public void setPageStrart(int pageStrart) {
		this.pageStrart = pageStrart;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public List getPageList() {
		return pageList;
	}
	public void setPageList(List pageList) {
		this.pageList = pageList;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
}
