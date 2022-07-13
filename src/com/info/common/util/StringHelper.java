package com.info.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringHelper {

	public static final String convertStringNull(String strOrig) {
	    String strReturn= "";
	    if(StringUtils.isNotBlank(strOrig) && !"null".equals(strOrig)) {
	      strReturn= strOrig.trim();
	    }
	    return strReturn;
	}
	
	public static boolean isNumeric(String str){
		if(str==null || str.length()==0){
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches()){
			return false;
		}
		return true;
	}
	
	public static int convert2Int(Object obj)
	  {
	    int result = 0;
	    if (obj == null) return result;
	    if (obj instanceof Integer) {
	      result = ((Integer)obj).intValue();
	    }
	    else if (obj.toString().trim().length() > 0)
	      try {
	        result = Integer.parseInt(obj.toString());
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    return result;
	  }
	
	public static StringBuffer getDelResourceids(String[] resourceid){
		StringBuffer buffer=new StringBuffer();
		buffer.append("(");
		if(resourceid!=null){
			for(int i=0;i<resourceid.length;i++){
				buffer.append("'"+resourceid[i]+"'");
				if(i!=(resourceid.length-1)){
                	buffer.append(",");
                }
			}
		}
		buffer.append(")");
		return buffer;
	}
	
	/**
	 * 过滤字符串<,>,',",&
	 * 
	 * @param value
	 * @return
	 */
	public static String filterJavaScript(String value) {
		//过滤字符串
		value = scriptingFilter(value);
		if (value == null || "".equals(value))
			return null;
		String[] strs = null;
		boolean flag = false;
		if(value.indexOf("\r\n")!=-1) {
			strs = value.split("\r\n");
			flag = true;
		}else {
			strs = new String[] {value};
		}
		
		for(int i=0; strs!=null&&strs.length>0&&i<strs.length; i++) {
			if(strs[i]!=null) {
//				strs[i] = strs[i].replaceAll("\\|", "");
//				strs[i] = strs[i].replaceAll("&", "&amp;");
//				strs[i] = strs[i].replaceAll(";", "");
//				strs[i] = strs[i].replaceAll("'", "");
//				strs[i] = strs[i].replaceAll("\"", "");
//				strs[i] = strs[i].replaceAll("\\'", "");
//				strs[i] = strs[i].replaceAll("<", "&lt;");
//				strs[i] = strs[i].replaceAll(">", "&gt;");
//				strs[i] = strs[i].replaceAll("\\(", "（");
//				strs[i] = strs[i].replaceAll("\\)", "）");
				//strs[i] = strs[i].replaceAll("\\+", "");
				strs[i] = strs[i].replaceAll("\r", "");
				strs[i] = strs[i].replaceAll("\n", "");
				strs[i] = strs[i].replaceAll("%27", "");
				strs[i] = strs[i].replaceAll("%22", "");
				strs[i] = strs[i].replaceAll("%3E", "");
				strs[i] = strs[i].replaceAll("%3C", "");
				strs[i] = strs[i].replaceAll("%3D", "");
				strs[i] = strs[i].replaceAll("%2F", "");
				strs[i] = strs[i].replaceAll("%2B", "");
				strs[i] = strs[i].replaceAll("%28", "");
				strs[i] = strs[i].replaceAll("%29", "");
			}
		}
		
		if(flag) {
			StringBuffer sb = new StringBuffer();
			for(int j=0; strs!=null&&strs.length>0&&j<strs.length; j++) {
				sb.append(strs[j]+"\r\n");
			}
			value = sb.toString();
		}else {
			value = strs[0];
		}
		return value;
	}
	
	/**
	 * 过滤字符串
	 * @param value
	 * @return
	 *
	 * @author ljx@cndatacom.com
	 * @date   2014-9-25
	 */
	public static String scriptingFilter(String value) {
		if (value == null) { 
			return null; 
		}
		StringBuffer result =  new StringBuffer(value.length());  
		for(int i=0; i<value.length(); ++i) { 
			switch (value.charAt(i)) { 
			case '<' :  
				result.append("&lt;"); 
				break; 
			case '>' :  
				result.append("&gt;"); 
				break; 
			case '"' :  
				result.append("&quot;"); 
				break; 
			case '\'':  
				result.append("&#39;"); 
				break; 
			case '%' :  
				result.append("&#37;"); 
				break; 
			case ';' : 
				result.append("&#59;"); 
				break; 
			case '(' :  
				result.append("&#40;");
				break; 
			case ')' :  
				result.append("&#41;"); 
				break; 
			case '&' :  
				result.append("&amp;");
				break; 
			case '+' :  
				result.append("&#43;"); 
				break;
			case '|' :
				result.append("");
				break;
			case '~' :
				result.append("");
				break;
			default :  
				result.append(value.charAt(i)); 
				break; 
			} 
		}  
		return new String(result);  
	}
	/**
	 * sql语句注入
	 * @param url
	 * @param mustfilterList
	 * @return
	 */
	public static String filtrateParticularChar(String queryString, String[] mustfilterList) {
		if(StringHelper.convertStringNull(queryString).length()==0){
			return "";
		}
		for (int i = 0; i < mustfilterList.length; i++) {
			if (queryString.indexOf(mustfilterList[i])!=-1) {
				queryString = queryString.replaceAll(mustfilterList[i], "");
			}
		}
		return queryString;
	}
	
}
