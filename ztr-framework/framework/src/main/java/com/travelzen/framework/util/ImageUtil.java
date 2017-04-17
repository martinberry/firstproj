/**
 *
 */
package com.travelzen.framework.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;

/**
 *
 * @author peilv
 *
 */
public class ImageUtil {
	/***
	 * 将图片进行scale。操作超大图片的时候会出现内存溢出的错误
	 * @param inFilePath
	 * @param outFilePath
	 * @param width
	 * @param height
	 */

	private static Logger LOGGER = RequestIdentityLogger.getLogger(ImageUtil.class);

	public static void imageOp(String inFilePath, String outFilePath, int width,
			int height) {

		File tempFile = new File(inFilePath);

		Image image = null;

		try {

			image = ImageIO.read(tempFile);

		} catch (IOException e) {

			System.out.println("file path error...");

		}

		int originalImageWidth = image.getWidth(null);

		int originalImageHeight = image.getHeight(null);

		BufferedImage originalImage = new BufferedImage(

		originalImageWidth,

		originalImageHeight,

		BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g2d = originalImage.createGraphics();

		g2d.drawImage(image, 0, 0, null);

		BufferedImage changedImage =

		new BufferedImage(

		width,

		height,

		BufferedImage.TYPE_3BYTE_BGR);

		double widthBo = (double) width / originalImageWidth;

		double heightBo = (double) height / originalImageHeight;

		AffineTransform transform = new AffineTransform();

		transform.setToScale(widthBo, heightBo);

		AffineTransformOp ato = new AffineTransformOp(transform, null);

		ato.filter(originalImage, changedImage);

		File fo = new File(outFilePath); // 将要转换出的小图文件

		try {

			ImageIO.write(changedImage, "jpeg", fo);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * 图片按尺寸压缩
	 * @param in
	 * @param width
	 * @param height
	 * @return
	 */
	public static InputStream imgCompress(InputStream in, int width, int height) {

		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // 获得缩放的比例
        double ratio = 1.0;
        // 判断如果高、宽都不大于设定值，则不处理
        if (image.getHeight() > height || image.getWidth() > width) {
            if (image.getHeight() > image.getWidth()) {
                ratio = image.getHeight() / height;
            } else {
                ratio = image.getWidth() / width;
            }
        }
        // 计算新的图面宽度和高度
        int newWidth = (int) (image.getWidth() / ratio);
        int newHeight = (int) (image.getHeight() / ratio);

        BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
                BufferedImage.TYPE_INT_RGB);
        bfImage.getGraphics().drawImage(
                image.getScaledInstance(newWidth, newHeight,
                        Image.SCALE_SMOOTH), 0, 0, null);



        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
			ImageIO.write(bfImage, "jpg", baos);
//			ImageIO.write(bfImage, "jpg", new File("/home/zhaopengfei/图片/compressNew.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        InputStream is = new ByteArrayInputStream(baos.toByteArray());

        return is;

	}


	/**
	 * 图片按尺寸压缩
	 * @param in
	 * @param width
	 * @param height
	 * @return
	 */
	public static InputStream imgCompressNew(InputStream in, int width, int height) {
		LOGGER.info("开始压缩图片,压缩后的图片分辨率:[{}]x[{}]",width,height);
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			LOGGER.error("读取图片错误",e);
		}
        // 获得缩放的比例
        double ratio = 1.0;
        // 判断如果高、宽都不大于设定值，则不处理
        if (image.getHeight() > height || image.getWidth() > width) {
            if (image.getHeight() > image.getWidth()) {
                ratio = image.getHeight() / height;
            } else {
                ratio = image.getWidth() / width;
            }
        }
        // 计算新的图面宽度和高度
        int newWidth = (int) (image.getWidth() / ratio);
        int newHeight = (int) (image.getHeight() / ratio);

        BufferedImage bfImage = new BufferedImage(newWidth, newHeight,BufferedImage.TYPE_INT_RGB);
        bfImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight,Image.SCALE_SMOOTH), 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
			ImageIO.write(bfImage, "jpg", baos);
		} catch (IOException e) {
			LOGGER.error("压缩图片错误",e);
		}
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        LOGGER.info("压缩图片结束,压缩后的图片分辨率:[{}]x[{}]",width,height);
        return is;
	}

	/**
	 * 图片压缩与剪切
	 * */
	public static InputStream compressAndCutImage(String imageType,InputStream in, int width, int height,int widthAfterCut,int heightAfterCut){
		InputStream inCut = imgCut(imageType,in, widthAfterCut, heightAfterCut);
		InputStream inCompress = imgCompressNew(inCut, width, height);
		return inCompress;
	}


	/**
	 * 根据裁减后的图片宽度对原油图片居中裁剪
	 * @param width :裁减后的图片宽度
	 * @param height : 裁剪后的图片高度
	 * */
	public static InputStream imgCut(String imageType,InputStream in, int width, int height) {
		LOGGER.info("开始裁剪图片,裁剪的图片格式为[{}]",imageType);
		Iterator<ImageReader> iterator = null;
		if(imageType.equals("jpg") || imageType.equals("jpeg") || imageType.equals("JPG") || imageType.equals("JPEG")){
			iterator = ImageIO.getImageReadersByFormatName("jpg");
		}else if(imageType.equals("png") || imageType.equals("PNG")){
			iterator = ImageIO.getImageReadersByFormatName("png");
		}
        ImageReader reader = iterator.next();
        ImageInputStream iis;
        ByteArrayOutputStream bs = null;
		try {
			bs = new ByteArrayOutputStream();
			iis = ImageIO.createImageInputStream(in);
			reader.setInput(iis, true);
	        ImageReadParam param = reader.getDefaultReadParam();
	        int imageIndex = 0;
	        //根据裁剪宽度以及图片宽度，居中裁剪
	        Rectangle rect =  null;
	        if(reader.getWidth(imageIndex) > width && reader.getHeight(imageIndex) >= (height + 220)){//裁剪高度，宽度小于图片自身宽度
	        	rect = new Rectangle((reader.getWidth(imageIndex)-width)/2, (reader.getHeight(imageIndex)- height)-220, width, height);
	        }else{//图片比裁剪的尺寸小,不裁剪
	        	rect = new Rectangle(0, 0, reader.getWidth(imageIndex), reader.getHeight(imageIndex));
	        }
	        param.setSourceRegion(rect);
	        BufferedImage bi = reader.read(0,param);
	        if(imageType.equals("jpg") || imageType.equals("jpeg") || imageType.equals("JPG") || imageType.equals("JPEG")){
	        	ImageIO.write(bi, "jpg", bs);
			}else if(imageType.equals("png") || imageType.equals("PNG")){
				ImageIO.write(bi, "png", bs);
			}

		} catch (IOException e) {
			LOGGER.error("剪切图片错误", e);
		}
		LOGGER.info("裁剪图片结束＝＝＝＝＝＝＝＝＝");
		return new ByteArrayInputStream(bs.toByteArray());
	}

	public static void main(String[] args) {
//			imageOp("/Users/liuzhuo/Documents/media-local-compress.png", "/Users/liuzhuo/Documents/media-local-compress-co.png",480, 160);
		try {
			InputStream in = imgCut("png",new FileInputStream("/home/zhaopengfei/图片/imageservice.png"), 1280, 680);
			imgCompressNew(in,480,255);
//			imgCompress(new FileInputStream("/home/zhaopengfei/图片/imageservice (5).jpg"),480,240);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
