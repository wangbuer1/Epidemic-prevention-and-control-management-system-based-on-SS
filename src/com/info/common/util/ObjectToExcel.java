package com.info.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.info.common.staticvalue.StaticValue;

public class ObjectToExcel {
	
	private static Logger logger = Logger.getLogger(ObjectToExcel.class);
	
	private String[] headers;//表头
	private String[] fields;//填入列数据
	private List<Object> list;//填入对象
	private String fileName;//附件表存放的中文文件名
	private String dateFormat = "yyyy-MM-dd";//日期类型转换格式,默认yyyy-MM-dd
	
	public static ObjectToExcel getInstance(PagerList pagelet,List<Object> list,String[] headers,String[] fields,String fileName,String dateFormat){
		ObjectToExcel instance = null;
		if(pagelet != null){
			instance = new ObjectToExcel(pagelet, headers, fields);
		}else{
			instance = new ObjectToExcel(list, headers, fields);
		}
		instance.setFileName(fileName);
		if(StringUtils.isNotBlank(dateFormat))
			instance.setDateFormat(dateFormat);
		return instance;
	}
	
	private ObjectToExcel(){
		
	}
	
	private ObjectToExcel(PagerList pagelet,String[] headers,String[] fields){
		this.headers = headers;
		this.fields = fields;
		if(pagelet != null)
			this.list = pagelet.getPageList();
	}
	
	private ObjectToExcel(List<Object> list,String[] headers,String[] fields){
		this.headers = headers;
		this.fields = fields;
		this.list = list;
	}
	
	/**
	 * 生成Excel
	 * @return 文件存储名称
	 */
	public String convertToExcel(){
		
		logger.info("start to convertToExcel");
		
		String root = StaticValue.ATTACH_PATH + File.separator;
		String attachid = GUIDGenerator.genRandomGUID().toLowerCase();
		File excel = new File(root + attachid + ".xls");
		
		try{
			OutputStream os = new FileOutputStream(excel);
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet(this.fileName == null ? "sheet" : this.fileName, 0);
			
			int startRow = 0;
			int startCell = 0;
			
			startRow = this.writeHeader(sheet, startRow, startCell);
			startRow++;
			
			startCell = 0;
			startRow = this.writeData(sheet, startRow, startCell);
			
			wwb.write();
			wwb.close();
			os.flush();
			os.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		logger.info("end to convertToExcel");
		return attachid + ".xls";
	}
	
	protected int writeHeader(WritableSheet sheet,int startRow,int startCell) throws Exception {
		// 设置单元格的文字格式
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
						WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直居中
		wcf.setAlignment(Alignment.CENTRE);//水平居中
		for(String header : this.headers){
			sheet.addCell(new Label(startCell++, startRow, header, wcf));
		}
		return startRow;
	}
	
	protected int writeData(WritableSheet sheet,int startRow,int startCell) throws Exception{
		// 设置单元格的文字格式
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
						WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直居中
		wcf.setAlignment(Alignment.LEFT);//水平居中
		
		if(this.list == null || this.list.size() <= 0){
			logger.error("当前导出对象列表为空");
			return startRow;
		}
		
		for(Object o : this.list){
			for(String field : fields){
				sheet.addCell(new Label(startCell++, startRow, this.getProperty(field, o), wcf));
			}
			startRow++;
			startCell = 0;
		}
		return startRow;
	}
	
	private String getProperty(String field,Object o){
		try{
			Field f = o.getClass().getDeclaredField(field);
			if(f != null){
				f.setAccessible(true);
				Object value = f.get(o);
				return ObjectToString(value);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	private String ObjectToString(Object o){
		if(o != null){
			String type = o.getClass().getSimpleName();
			if("Integer".equals(type) || "int".equals(type)){
				return ((Integer)o).toString();
			}else if("Long".equals(type) || "long".equals(type)){
				return ((Long)o).toString();
			}else if("Double".equals(type) || "double".equals(type)){
				return ((Double)o).toString();
			}else if("Float".equals(type) || "float".equals(type)){
				return ((Float)o).toString();
			}else if("BigDecimal".equals(type)){
				return ((BigDecimal)o).toString();
			}else if(o instanceof java.lang.String){
				return o.toString();
			}else if(o instanceof java.util.Date){
				return new SimpleDateFormat(dateFormat).format((java.util.Date)o);
			}
		}
		return "";
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
