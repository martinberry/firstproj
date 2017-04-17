package com.ztravel.mediaserver.client;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @Author  : simon
 * @version : Jun 14, 2014 3:55:49 PM
 *
 **/
public class MediaDownloadUtilTest {

	public static void main(String[] args) {
		try {
			MediaDownloadUtil.download("52d8ef82e4b05a200d457f8b", "/tmp/pic.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

}
