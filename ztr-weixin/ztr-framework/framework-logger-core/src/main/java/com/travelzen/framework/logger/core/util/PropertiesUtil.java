package com.travelzen.framework.logger.core.util;

import java.io.*;
import java.util.*;

/**
 * 读取和设置Property文件
 * <p/>
 * Created by IntelliJ IDEA.
 * User: 杨果
 * JDate: 11-9-13
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesUtil {
    public static Map<String, String> getProperty(String filePath) {
        Map map = new HashMap();
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (Exception e) {
            System.out.println("文件读取失败");
        }
        Set set = p.keySet();

        for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            map.put(key, p.get(key));
        }
        return map;
    }

    public static void setProperty(String filePath, String key, String value) throws IOException {
        File file = new File(filePath);
        Properties properties = new Properties();
        if (!file.exists())
            file.createNewFile();
        InputStream inputStream = new FileInputStream(file);
        properties.load(inputStream);
        //修改之前必须关闭
        inputStream.close();
        OutputStream outputStream = new FileOutputStream(filePath);
        properties.setProperty(key, value);
        properties.store(outputStream, "Update '" + key + "' value");
        outputStream.close();
    }
}
