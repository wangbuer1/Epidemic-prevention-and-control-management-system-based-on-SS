package com.info.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.info.common.taglib.model.DataList;
import com.info.common.taglib.model.Tag;

public class ResolveTagXML {
	private static Map<String,Tag> map = null;
	
	@SuppressWarnings("unchecked")
	private static void resolveXML(){
		String baseURL = "";
		try {
			//
			baseURL = ResolveTagXML.class.getClassLoader().getResource("/")
				.toURI().getPath() + "\\tag\\tag.xml";
			//baseURL = "D:\\work\\lesson_manager\\resources\\tag\\tag.xml";
			FileInputStream inputStream = new FileInputStream(baseURL);
			SAXBuilder builder = new SAXBuilder();
			Document xmlRequest = builder.build(inputStream);
			Element root = xmlRequest.getRootElement();
			List<Element> rootList = root.getChildren();
			Tag tag = null;
			for (int i = 0,len = rootList.size(); i < len; i++) {
				Element rootElement = rootList.get(i);
				String tagId = rootElement.getChildText("tagId");
				String tagType = rootElement.getChildText("tagType");
				List<Element> dataRootList = rootElement.getChildren("dataList");
				List<DataList> list = new ArrayList<DataList>();
				for (Element element : dataRootList) {
					List<Element> datas = element.getChildren("data");
					for (Element element2 : datas) {
						String feildName = element2.getChildText("feildName");
						String feildValue = element2.getChildText("feildValue");
						DataList d = new DataList(feildName, feildValue);
						list.add(d);
					}
				}
				tag = new Tag(tagId, tagType, list);
				map.put(tagId, tag);
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
		
	
	public static Tag getTag(String tagId) {
		if(map == null){
			map = new HashMap<String, Tag>();
			resolveXML();
		}
		return map.get(tagId);
	}
	
	public static void main(String[] args) {
		Tag t = getTag("sex");
		System.out.println(t);
	}
}
