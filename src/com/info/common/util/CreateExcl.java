package com.info.common.util;

import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CreateExcl {
	
	
	public void excl(String[] cellNames,List<Object[]> list,HttpServletResponse response,String rootPath,String filePath){
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet();
		sheet.setDefaultColumnWidth((short) 15);
		sheet.autoSizeColumn((short)0);
		
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeightInPoints(20);
		HSSFFont f = wb.createFont();
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		f.setFontHeightInPoints((short)12);
		
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setFont(f);

		for (int i = 0; i < cellNames.length; i++) {
			HSSFCell cell = row.createCell((short) i);
			cell.setCellValue(cellNames[i]);
			cell.setCellStyle(style);
		}
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			row.setHeightInPoints(20);
			Object[] bean = (Object[]) list.get(i);
			// 第四步，创建单元格，并设置值
			for (int j = 0; j < bean.length; j++) {
				row.createCell((short) j).setCellValue(bean[j].toString());
			}
			
		}
		// 第六步，将文件存到指定位置
		try {
			FileOutputStream fout = new FileOutputStream(rootPath + filePath);
			wb.write(fout);
			fout.close();
			DownFileHelper.downFile(response, rootPath,filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
