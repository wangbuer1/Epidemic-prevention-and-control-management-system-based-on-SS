package com.info.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

public class DownFileHelper {

	static HashMap<String, String> fileType = new HashMap<String, String>();
	
	static{
		fileType.put("html", "text/html");
	    fileType.put("xml", "text/xml");
	    fileType.put("hta", "application/hta");
	    fileType.put("doc", "application/msword");
	    fileType.put("wps", "application/vnd.ms-works");
	    fileType.put("xls", "application/vnd.ms-excel");
	    fileType.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    fileType.put("htm", "text/html");
	    fileType.put("gif", "image/gif");
	    fileType.put("jpeg", "image/jpeg");
	    fileType.put("jpg", "image/jpeg");
	    fileType.put("mht", "message/rfc822");
	    fileType.put("mhtml", "message/rfc822");
	    fileType.put("pdf", "application/pdf");
	    fileType.put("ppt", "application/vnd.ms-powerpoint");
	    fileType.put("pps", "application/vnd.ms-powerpoint");
	    fileType.put("tif", "image/tiff");
	    fileType.put("tiff", "image/tiff");
	    fileType.put("txt", "text/plain");
	    fileType.put("zip", "application/zip");
	    fileType.put("rar", "application/rar");
	    fileType.put("class", "application/x-java-vm");
	    fileType.put("jar", "application/x-java-archive");
	    fileType.put("ser", "application/x-java-serialized");
	    fileType.put("exe", "application/octet-stream");
	    fileType.put("hdml", "text/x-hdml");
	    fileType.put("bmp", "image/bmp");
	    fileType.put("ico", "image/x-icon");
	    fileType.put("wml", "text/vnd.wap.wml");
	    fileType.put("wmls", "text/vnd.wap.wmlscript");
	    fileType.put("wmlc", "application/vnd.wap.wmlc");
	    fileType.put("wmlsc", "application/vnd.wap.wmlscript");
	    fileType.put("wbmp", "image/vnd.wap.wbmp");
	    fileType.put("csv", "application/msexcel");
	    fileType.put("vsd", "application/vnd.visio");
	    fileType.put("p7b", "application/x-pkcs7-certificates");
	    fileType.put("cer", "application/x-x509-ca-cert");
	    fileType.put("der", "application/x-x509-ca-cert");
	    fileType.put("apk", "application/octet-stream");
	    fileType.put("cab", "application/octet-stream");
	    fileType.put("png", "image/png");
	}
	
	public static String getFileType(String filePath) throws ServletException {
	    String extendName = getFileExtendName(filePath);
	    return ((String)fileType.get(extendName));
	}
	
	/**
	 * 根据文件名获得文件的扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExtendName(String fileName) {
		int i = fileName.lastIndexOf(".");
		if (i == -1)
			return "";
		return fileName.substring(i + 1, fileName.length()).toLowerCase();
	}
	
	public static void downFile(HttpServletResponse response, String filePath,String fileName){
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);// filename是下载的xls的名，建议最好用英文
		response.setContentType("text/plain;charset=UTF-8");// 设置类型
		response.setHeader("Pragma", "No-cache");// 设置头
		response.setHeader("Cache-Control", "no-cache");// 设置头
		response.setDateHeader("Expires", 0);// 设置日期头
		String direcotry = filePath + "\\" +fileName;
		String downType = "";
		try {
			downType = getFileType(direcotry);
		} catch (ServletException e2) {
			e2.printStackTrace();
		}
		// 先取文件名(带扩展名)/
		// String fileName = direcotry.substring(direcotry.lastIndexOf("\\")+1);
		File file = new File(direcotry);
		if (!file.exists()) {
			response.setContentType("text/html;charset=gbk");
			try {
				response
						.getWriter()
						.print(
								"<script language=\"javascript\">alert(\"对不起,附件不存在!\");history.back(-1);</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
	    
		if(fileName != null && fileName.length() > 0){
			fileName = file.getName();
		}
		
		if (!"".equals(downType)) {
			FileInputStream inStream = null;
			OutputStream outStream = null;
			try {
				inStream = new FileInputStream(file);
				if (inStream != null) {
					int outCount = 0;
					int streamOut = inStream.available();
					
					System.out.println("streamOut : " + streamOut);
					byte[] byteOut = new byte[streamOut];
					response.setContentType(downType);
					response.setContentLength(streamOut);
					outStream = response.getOutputStream();
					String encodeType = DownFileHelper.getEncoding(fileName);
					String titleNames =null;
					if("UTF-8".equals(encodeType)){
					   titleNames = new String(fileName.getBytes("UTF-8"),
							"iso8859-1");
					}else{
						titleNames=new String(fileName.getBytes("gb2312"), "iso8859-1");
					}
					
					response.setHeader("Content-Disposition",
							"attachment;filename=\"" + titleNames + "\"");
					byte[] buf = new byte[2048];
					int length = 0;
					while((length = inStream.read(buf)) > 0){
						//total += length;
						byte[] tmpBuf = new byte[length];
						System.arraycopy(buf, 0, tmpBuf, 0, length);
						outStream.write(tmpBuf);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (outStream != null) {
						outStream.flush();
						outStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (inStream != null) {
						inStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				response.setContentType("text/html;charset=UTF-8");
				response
						.getWriter()
						.print(
								"<script language=\"javascript\">alert(\"对不起,附件下载失败!\");history.back(-1);</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void downFile(HttpServletResponse response, String filePath) {
		downFile(response,filePath,"");
	}
	
	/**
	 * 输出图片
	 * @param response
	 * @param filePath
	 * @param fileName
	 */
	public static void outFile(HttpServletResponse response, String filePath,String fileName){
		String direcotry = filePath;
		String downType = "";
		try {
			downType = getFileType(direcotry);
		} catch (ServletException e2) {
			e2.printStackTrace();
		}
		// 先取文件名(带扩展名)/
		// String fileName = direcotry.substring(direcotry.lastIndexOf("\\")+1);
		File file = new File(direcotry);
		if (!file.exists()) {
			response.setContentType("text/html;charset=gbk");
			try {
				response
						.getWriter()
						.print(
								"<script language=\"javascript\">alert(\"对不起,附件不存在!\");history.back(-1);</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
	    
		if(fileName != null && fileName.length() > 0){
			fileName = file.getName();
		}
		
		if (!"".equals(downType)) {
			FileInputStream inStream = null;
			OutputStream outStream = null;
			try {
				inStream = new FileInputStream(file);
				if (inStream != null) {
					int streamOut = inStream.available();
					
					System.out.println("streamOut : " + streamOut);
					//byte[] byteOut = new byte[streamOut];
					response.setContentType(downType);
					response.setContentLength(streamOut);
					outStream = response.getOutputStream();
					byte[] buf = new byte[2048];
					int length = 0;
					while((length = inStream.read(buf)) > 0){
						//total += length;
						byte[] tmpBuf = new byte[length];
						System.arraycopy(buf, 0, tmpBuf, 0, length);
						outStream.write(tmpBuf);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (outStream != null) {
						outStream.flush();
						outStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (inStream != null) {
						inStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				response.setContentType("text/html;charset=UTF-8");
				response
						.getWriter()
						.print(
								"<script language=\"javascript\">alert(\"对不起,附件下载失败!\");history.back(-1);</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void outFile(HttpServletResponse response, String filePath) {
		outFile(response,filePath,"");
	}
	  /**
     * 判断字符串的编码
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    
    /**
	 * FileInputStream的read方法直接读入字节块到直接缓冲buf，然后每次读取一个字节。
       优点：速度最快，读写文件时最快的
       缺点： 占用了小量内存
	 * @param args
	 */
	public static void readFileInputStream(InputStream stream,String outPath){
		FileOutputStream out=null;
		try {
			File outFile=new File(outPath);
			File parentFile=outFile.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs(); 
			}
			out=new FileOutputStream(outFile);
			readFileInputStream(stream,out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * FileInputStream的read方法直接读入字节块到直接缓冲buf，然后每次读取一个字节。
       优点：速度最快，读写文件时最快的
       缺点： 占用了小量内存
	 * @param args
	 */
	public static void readFileInputStream(InputStream stream,FileOutputStream out){
		long l1=System.currentTimeMillis();
		try {
			byte buf[] = new byte[2048];
			int n;
			int cnt=(int)stream.available();
			while ((n = stream.read(buf)) >0) { // FileInputStream的read方法直接读入字节块到
				//直接缓冲buf，然后每次读取一个字节
				byte[] tms=new byte[n];
				System.arraycopy(buf ,0,tms,0,n);
				out.write(tms);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(out!=null){
					out.flush();
					out.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(stream!=null){
					stream.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
