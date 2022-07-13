package com.info.common.controller;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.info.common.staticvalue.StaticValue;
import com.info.view.file.model.FileUpload;

//import com.info.view.file.model.FileUpload;

@Controller
public class BaseController {
    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            public void setAsText(String value) {
                try {
                    if (StringUtils.isNotBlank(value)) {
                        setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
                    }
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }

            public String getAsText() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) getValue());
            }

        });
    }


    public FileUpload uploadFile(HttpServletRequest request, CommonsMultipartFile file, String id) {
        com.info.view.file.model.FileUpload upFile = null;
        String path = StaticValue.IMAGE_PATH;
        String root1 = request.getSession().getServletContext().getRealPath("/");
        path = root1 + "image//";
        System.out.println("测试++===========" + root1);
        System.out.println(this.getClass().getResource("").getPath());
        System.out.println(this.getClass().getResource("/").getPath());
        System.out.println(this.getClass().getClassLoader().getResource("").getPath());
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());


        InputStream is = null;
        OutputStream os = null;
        if (!"".equals(file.getOriginalFilename())) {
            try {
                is = file.getInputStream();
                String oldFileNames = file.getOriginalFilename();
                String oldFileName = oldFileNames.substring(0, oldFileNames.lastIndexOf("."));
                String fileSuffix = oldFileNames.substring(oldFileNames.lastIndexOf(".") + 1, oldFileNames.length());
                String newFileName = UUID.randomUUID().toString().replace("-", "");
                os = new FileOutputStream(new File(path, newFileName + "." + fileSuffix));
                int len = 0;
                byte[] buff = new byte[400];
                while ((len = is.read(buff)) != -1) {
                    os.write(buff, 0, len);
                }
                upFile = new FileUpload();
                upFile.setResourceId(UUID.randomUUID().toString().replace("-", ""));
                upFile.setFileNewName(newFileName);
                upFile.setFileOldName(oldFileName);
                upFile.setFilePath(path + "\\");
                upFile.setFileSuffix(fileSuffix);
                upFile.setId(id);
                return upFile;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                    if (os != null) os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
