package com.info.common.util;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;

import com.info.view.person.model.Person;
import com.info.view.user.model.User;
import com.info.view.work.model.Work;

public class InitEntity {
	
	public <T> T initAddInfo(T t,HttpSession session) throws Exception{
		String role = (String)session.getAttribute("role");
		if("admin".equals(role)){
			Person person = (Person)session.getAttribute("personSession");
			PropertyUtils.setProperty(t, "creatorId", person.getPersonId());
			PropertyUtils.setProperty(t, "creatorName", person.getPersonName());
			PropertyUtils.setProperty(t, "createTime", new Date());
		} 
		if("user".equals(role)){
			User user = (User)session.getAttribute("userSession");
			PropertyUtils.setProperty(t, "creatorId", user.getResourceId());
			PropertyUtils.setProperty(t, "creatorName", user.getUserName());
			PropertyUtils.setProperty(t, "createTime", new Date());
		}
		if("work".equals(role)){
			Work work = (Work)session.getAttribute("workSession");
			PropertyUtils.setProperty(t, "creatorId", work.getResourceId());
			PropertyUtils.setProperty(t, "creatorName", work.getWorkName());
			PropertyUtils.setProperty(t, "createTime", new Date());
		}
		return t;
	}
	
	
	public <T> T initUpdateInfo(T t,HttpSession session) throws Exception{
		String role = (String)session.getAttribute("role");
		if("admin".equals(role)){
			Person person = (Person)session.getAttribute("personSession");
			PropertyUtils.setProperty(t, "updaterId", person.getPersonId());
			PropertyUtils.setProperty(t, "updaterName", person.getPersonName());
			PropertyUtils.setProperty(t, "updateTime", new Date());
		} 
		if("user".equals(role)){
			User user = (User)session.getAttribute("userSession");
			PropertyUtils.setProperty(t, "updaterId", user.getResourceId());
			PropertyUtils.setProperty(t, "updaterName", user.getUserName());
			PropertyUtils.setProperty(t, "updateTime", new Date());
		}
		if("work".equals(role)){
			Work work = (Work)session.getAttribute("workSession");
			PropertyUtils.setProperty(t, "updaterId", work.getResourceId());
			PropertyUtils.setProperty(t, "updaterName", work.getWorkName());
			PropertyUtils.setProperty(t, "updateTime", new Date());
		}
		return t;
	}
}
