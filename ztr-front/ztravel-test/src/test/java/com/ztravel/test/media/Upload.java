package com.ztravel.test.media;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.ztravel.mediaserver.client.CompressType;
import com.ztravel.mediaserver.client.MediaClientUtil;
import com.ztravel.mediaserver.client.MediaClientUtil.MediaType;
import com.ztravel.mediaserver.client.MediaDownloadUtil;

public class Upload {
	
//	@Test
	public void uploadNormal() {
		String filePath = "/Users/liuzhuo/Documents/avatar-01.jpg";
		MediaType mediaType = MediaType.IMAGE;
		try {
			String mediaId = MediaClientUtil.upload(filePath, mediaType);
			System.out.println(mediaId);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void uploadAndCompress() {

		String filePath = "/Users/zhaopengfei/Downloads/风景图8.jpg";
		MediaType mediaType = MediaType.IMAGE;
		try {
			String mediaId = MediaClientUtil.uploadAndCompress(filePath, mediaType, CompressType.Normal);
			System.out.println(mediaId);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}	
	
	
//	@Test
	public void download() {
		try {
			MediaDownloadUtil.download("s155ba1e19d4c6ce32810b1f40", "/Users/liuzhuo/Documents/media-local-compress2.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
