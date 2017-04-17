package com.ztravel.mediaserver.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ztravel.mediaserver.client.MediaClientUtil.MediaType;
import com.ztravel.mediaserver.mediaid.MediaId;

public class MediaClientUtilTest {

    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory.getLogger(MediaClientUtilTest.class);

    @Test
    public void testUploadImage() throws IOException,Exception {
    	String mediaId = MediaClientUtil.upload("/home/wangmeng/Downloads/yiping2.jpg");
    	System.out.println(mediaId);
    }
    @Test
    public void testUploadByteImage() throws IOException,Exception {
    	String mediaId = MediaClientUtil.upload(getBytes("/home/wangmeng/Downloads/yiping2.jpg"));
    	System.out.println(mediaId);
    }
    
    @Test
    public void testUploadSquareImage() throws IOException,Exception {
    	String mediaId = MediaClientUtil.upload(getBytes("/home/wangmeng/Downloads/yiping2.jpg"),MediaType.IMAGE,20);
    	System.out.println(mediaId);
    }
    
    @Test
    public void testUploadWithWidth() throws IOException,Exception {
    	String mediaId = MediaClientUtil.uploadWithWidth(getBytes("/home/wangmeng/Downloads/yiping2.jpg"),MediaType.IMAGE,5);
    	System.out.println(mediaId);
    }

    public static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }  
    @Test
    public void testCreateObjectId() {
    	new MediaId("000000000000000000000000");
    }
    

}
