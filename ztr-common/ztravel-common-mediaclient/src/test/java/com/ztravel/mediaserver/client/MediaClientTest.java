package com.ztravel.mediaserver.client;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.picture.TZPhotoUtil;
import com.travelzen.framework.util.HashUtils;

public class MediaClientTest {

    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory.getLogger(MediaClientTest.class);

    @SuppressWarnings("unused")
    private String testMediaId = "7034482887350049844";
    private MediaClient mdaClient;

    @Before
    public void initMediaClient() {
        mdaClient = new MediaClient();
        mdaClient.setHost("192.168.130.89", 8080, "/tops-mediaserver/uploadImageService");
//        mdaClient.setHost("127.0.0.1", 8080, "/tops-mediaserver/uploadImageService");
    }

    @Test
    public void testUploadPhotoWithHeight() throws IOException {
        byte[] content = TZPhotoUtil.getBytesFromFile(new File("/home/jiangningcui/workspace/tz/tz-dev/ImageMagick/1.jpg"));
        boolean result = mdaClient.uploadPhotoWithHeight(content, "22333313421342134444555_heigth", "image", 200);
        assertTrue(result);
    }

    @Test
    public void testUploadPhotoWithSquareWidth() throws IOException {
        byte[] content = TZPhotoUtil.getBytesFromFile(new File("/home/jiangningcui/workspace/tz/tz-dev/ImageMagick/1.jpg"));
        boolean result = mdaClient.uploadPhotoWithSquareWidth(content, "22333313421342134444_square", "image", 100);
        assertTrue(result);
    }

    @Test
    public void testUploadPhotoWithWidth() throws IOException {
        byte[] content = TZPhotoUtil.getBytesFromFile(new File("/home/jiangningcui/workspace/tz/tz-dev/ImageMagick/1.jpg"));
        boolean result = mdaClient.uploadPhotoWithWidth(content, "22333313421342134444_width", "image", 100);
        assertTrue(result);
    }

    @Test
    public void testUploadContract() throws IOException {
        String filename = "/home/simon/Downloads/125957-1280.1024png";
        String mediaStringId = filename.replaceAll("/", "");
        if (mediaStringId.length() < 10) {
            return;
        }
        mediaStringId = mediaStringId.substring(mediaStringId.length() - 7, mediaStringId.length() - 1);
        String mediaId = "" + HashUtils.murmurHash2(mediaStringId);
        boolean result = mdaClient.uploadPhoto(filename, mediaId, "contract");
        assertTrue(result);
    }

}
