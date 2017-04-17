package com.ztravel.media.compress.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.ImageUtil;
import com.ztravel.mediaserver.dao.IMediaMongoBaseDao;
import com.ztravel.mediaserver.db.projo.Media;
import com.ztravel.mq.handler.IMqHandler;


/**
 * 图片压缩实现
 * @author liuzhuo
 *
 */
@Service
public class MedisCompressHandler implements IMqHandler{

	@Resource
	IMediaMongoBaseDao mediaMongoBaseDao;

	private static Logger LOGGER = RequestIdentityLogger.getLogger(MedisCompressHandler.class);

	@Override
	public void handle(String message) {
		JSONObject msgJson = JSON.parseObject(message);
		LOGGER.info("图片压缩消息[{}]",message);
		String compressType = msgJson.getString("compressType");
		String mediaId = msgJson.getString("mediaId");
		Media oriMedia = mediaMongoBaseDao.getMedia(mediaId);
		try {
			oriMedia.setMediaId("s1" + mediaId);//所有压缩与裁剪后的图片都加上前缀s1
			LOGGER.info("开始压缩裁剪图片[{}]",oriMedia.getFilename());
			if(compressType.equals("s2")){//剪切压缩
				oriMedia.setInputStream(ImageUtil.compressAndCutImage(oriMedia.getType(),oriMedia.getInputStream(), 480, 255,1280,680));
			}else if(compressType.equals("s1")){//只压缩
				oriMedia.setInputStream(ImageUtil.imgCompress(oriMedia.getInputStream(), 480, 240));
			}
			String imageId = mediaMongoBaseDao.addMedia(oriMedia).toString();
			LOGGER.info("图片[{}]压缩裁剪完成,裁剪压缩后图片ID为[{}]",mediaId,imageId);
		} catch (Exception e) {
			LOGGER.error("压缩裁剪图片[{}]失败",oriMedia.getFilename(),e);
		}

	}

}
