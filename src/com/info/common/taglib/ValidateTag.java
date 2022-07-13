package com.info.common.taglib;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.info.common.taglib.model.ValidateField;




/**
 * 验证
 */
public class ValidateTag extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9039121023823904238L;
	private String verifyFile;

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			List<ValidateField> fields = resolveXML(verifyFile);
 String str=   "<script type='text/javascript'>\n" +
				   "	var fv = {\n" +
				   "		checkValue : function() { \n" +
			 	   "			return Validator.vd(); \n" +
				   "		} \n" +
				   "	} \n";
				str += "function onload_validate() {" +
				   "	var obj;";
				if(fields != null && fields.size() > 0){
					for (int i = 0; i < fields.size(); i++) {
						ValidateField field = fields.get(i);
		  str +=   "	obj = { \n" +
					   "		name : '"+ field.getFiledName() +"', \n" +
					   "		text : '"+ field.getFiledText() +"', \n" +
					   "		validateType : '"+ field.getValidateType() +"', \n" +
					   "		enableErrorTip : true, \n" +
					   "		enableInfoTip : false, \n" +
					   "		errorTip : '', \n" +
					   "		infoTip : '', \n" +
					   "		status : '', \n" +
					   "		prefix : false \n";
					   if(field.getMaxValue() > 0){
						   str += ",\n		max : '"+ field.getMaxValue() +"' \n";
					   }
					   
					   if(field.getMinStr() > 0){
						   str += ",\n      min : '"+ field.getMinStr() +"' \n";
					   }
					   str+=  "	};\n" +
					   "	Validator.add(obj);\n";
					}
				}
	  str += "Validator.load();\n}\nValidator.addEvent(window, 'load', onload_validate);\n</script>";
	  JspWriter out = pageContext.getOut();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	@Override
	public void release() {
		super.release();
	}
	
	@SuppressWarnings("unchecked")
	private List<ValidateField> resolveXML(String verifyFile){
		System.out.println(verifyFile);
		List<ValidateField> fields = null;
		try {
			String url = ValidateTag.class.getClassLoader().getResource("/").toURI().getPath();
			url += "fields/" + verifyFile;
			url = url.substring(url.indexOf("/")+1, url.length());
			fields = new ArrayList<ValidateField>();
			FileInputStream inputStream = new FileInputStream(url);
			SAXBuilder builder = new SAXBuilder();
			Document xmlRequest = builder.build(inputStream);
			Element root = xmlRequest.getRootElement();
			List<Element> rootList = root.getChildren();
			for (int i = 0; i < rootList.size(); i++) {
				Element rootElement = rootList.get(i);
				String filedName = rootElement.getAttributeValue("name");
				String filedText = rootElement.getAttributeValue("text");
				String validateType = rootElement.getAttributeValue("validateType");
				String maxStr = rootElement.getAttributeValue("max");
				String minStr = rootElement.getAttributeValue("min");
				Integer maxValue = 0;
				if(maxStr != null && !"".equals(maxStr)){
					 maxValue = Integer.parseInt(maxStr);
				} else {
					maxValue = -1;
				}
				Integer minValue = 0;
				if(minStr != null && !"".endsWith(minStr)){
					minValue = Integer.parseInt(minStr);
				} else {
					minValue = -1;
				}
				String format = rootElement.getAttributeValue("format");
				ValidateField vf = new ValidateField(filedName, filedText, validateType, maxValue, format,minValue);
				fields.add(vf);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return fields;
	}

	public String getVerifyFile() {
		return verifyFile;
	}

	public void setVerifyFile(String verifyFile) {
		this.verifyFile = verifyFile;
	}
}
