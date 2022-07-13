package com.info.common.util;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 转换excel到对象封装类
 *
 */
public class ExcelToObject {
	
	static Logger logger = Logger.getLogger(ExcelToObject.class);
	
	public static ExcelToObject getInstance(){
		return  new ExcelToObject();
	}
	
	/**
	 * 解析excel输入流到对象
	 * @param is excel文件流（2010版本以下）
	 * @param fields 对应解析excel对应列到对象的映射字段数组
	 * @param clazz 要转换的对象class
	 * @return claz对应的对象列表
	 * @throws Exception
	 */
	public List<?> parseExcel(InputStream is,String[] fields,Class<?> clazz) throws Exception {
		
		logger.info("开始解析上传的xls文件，解析到字段："+StringUtils.join(fields, ",")+"，解析到对象："+clazz);
		Workbook wb = new HSSFWorkbook(is);
		Sheet s = wb.getSheetAt(0);
		
		int lastRow = s.getLastRowNum();
		int startRow = 1;
		if(lastRow < startRow){
			logger.error("当前excel未填写数据");
			return null;
		}
		List<Object> l = new ArrayList<Object>();
		for(;startRow <= lastRow; startRow++){
			int cells = s.getRow(startRow).getLastCellNum();
			Object o = clazz.newInstance();//调用对象的空构造器创建对象
			for(int i=0;i<cells;i++){
				if(i >= fields.length)
					break;
				Cell c = s.getRow(startRow).getCell(i);
				if(c == null) break;
				logger.info(fields[i] + "---" + this.getCellValue(c));
				Object value = this.convertValue(clazz, fields[i], this.getCellValue(c));
				try{
					PropertyUtils.setProperty(o, fields[i], value);
				}catch(Exception e){
					e.printStackTrace();
					logger.error("try to setProperty exception:"+e.getMessage(), e);
				}
			}
			l.add(o);
		}
		return l;
	}
	
	Object getCellValue(Cell c){
		switch(c.getCellType()){
			case Cell.CELL_TYPE_NUMERIC:
				Object value = "";
				if(HSSFDateUtil.isCellDateFormatted(c)){
				        //用于转化为日期格式
				        value = c.getDateCellValue();
				        value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
			    }else {
			        BigDecimal bigDecimal = new BigDecimal(c.getNumericCellValue());
			        value = bigDecimal.toString();
			    }
				return value;
			case Cell.CELL_TYPE_STRING:
				return c.getStringCellValue();
			case Cell.CELL_TYPE_FORMULA:
				c.setCellType(Cell.CELL_TYPE_NUMERIC);
				return c.getNumericCellValue();
			case Cell.CELL_TYPE_BLANK:
				return c.getStringCellValue();
			case Cell.CELL_TYPE_BOOLEAN:
				return c.getBooleanCellValue();
			default:
				return null;
		}
	}
	
	Object convertValue(Class<?> clazz,String field,Object value) {
		try{
			Class<?> c = clazz.getDeclaredField(field).getType();
			if(c.getSimpleName().equals("Integer")){
				BigDecimal d = new BigDecimal(value.toString());
				return d.intValue();
			}else if(c.getSimpleName().equals("Float")){
				return Float.valueOf(value.toString());
			}else if(c.getSimpleName().equals("String")){
				return value.toString();
			}else if(c.getSimpleName().equals("BigDecimal")){
				return new BigDecimal(value.toString());
			}else if(c.getSimpleName().equals("Date")){
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("can't find field in clazz:"+e.getMessage(), e);
		}
		return value;
	}
	
	public static void main(String[] args) throws Exception {
	}

}
