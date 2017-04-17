package com.ztravel.product.back.freetravel.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.util.WebEnv;
import com.ztravel.mediaserver.client.CompressType;
import com.ztravel.mediaserver.client.MediaClientUtil;
import com.ztravel.mediaserver.client.MediaClientUtil.MediaType;

@Controller
@RequestMapping("/upload")
public class UploadImageController {

	@RequestMapping("/image")
	@ResponseBody
	public AjaxResponse changeHead(@RequestParam(value = "imageInputer", required = true) MultipartFile file, HttpServletRequest request){
		//判断图片大小是否大于2M
	    if (file.getSize() > 2 * 1024 * 1024 || file.getSize() == 0) {
	    	return AjaxResponse.instance(ProductCons.IMG_SIZE_ERROR_CODE, ProductCons.IMG_SIZE_ERROR_MSG) ;
	    }
	    String fileName = file.getOriginalFilename();
		String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		//判断格式是否合法
		if(ProductCons.SAFE_IMAGE_EXT_NAME.indexOf(extensionName.toLowerCase()) == -1){
			return AjaxResponse.instance(ProductCons.IMG_EXTNAME_ERROR_CODE, ProductCons.IMG_EXTNAME_ERROR_MSG) ;
		}
		String imageId = "";
		try {
//			imageId = MediaClientUtil.upload(file.getBytes(), MediaType.IMAGE);
			imageId = MediaClientUtil.uploadAndCompress(file.getBytes(), MediaType.IMAGE,fileName,CompressType.Cut);
		} catch (Exception e) {
			return AjaxResponse.instance(ProductCons.IMG_SERVER_ERROR_CODE, ProductCons.IMG_SERVER_ERROR_MSG) ;
		}
		return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, imageId) ;
	}

	@RequestMapping("/compress")
	@ResponseBody
	public AjaxResponse compressOnly(@RequestParam(value = "imageInputer", required = true) MultipartFile file, HttpServletRequest request){
		//判断图片大小是否大于2M
	    if (file.getSize() > 2 * 1024 * 1024 || file.getSize() == 0) {
	    	return AjaxResponse.instance(ProductCons.IMG_SIZE_ERROR_CODE, ProductCons.IMG_SIZE_ERROR_MSG) ;
	    }
	    String fileName = file.getOriginalFilename();
		String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		//判断格式是否合法
		if(ProductCons.SAFE_IMAGE_EXT_NAME.indexOf(extensionName.toLowerCase()) == -1){
			return AjaxResponse.instance(ProductCons.IMG_EXTNAME_ERROR_CODE, ProductCons.IMG_EXTNAME_ERROR_MSG) ;
		}
		String imageId = "";
		try {
//			imageId = MediaClientUtil.upload(file.getBytes(), MediaType.IMAGE);
			imageId = MediaClientUtil.uploadAndCompress(file.getBytes(), MediaType.IMAGE,fileName,CompressType.Normal);
		} catch (Exception e) {
			return AjaxResponse.instance(ProductCons.IMG_SERVER_ERROR_CODE, ProductCons.IMG_SERVER_ERROR_MSG) ;
		}
		return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, imageId) ;
	}

	@RequestMapping("/kindFile")
	@ResponseBody
	public Map<String,Object> uploadFile(@RequestParam(value = "imgFile", required = true) MultipartFile file, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		//判断图片大小是否大于2M
	    if (file.getSize() > 2 * 1024 * 1024 || file.getSize() == 0) {
	    	map.put("error", 1);
	    	map.put("message", "文件大小限制2M");
	    	return map;
	    }
	    String fileName = file.getOriginalFilename();
		String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		//判断格式是否合法
		if(ProductCons.SAFE_FILE_EXT_NAME.indexOf(extensionName.toLowerCase()) == -1){
			map.put("error", 1);
			map.put("message", "文件格式不合法");
			return map;
		}
		String imageId = "";
		try {
			imageId = MediaClientUtil.upload(file.getBytes(), MediaType.IMAGE);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", 1);
			map.put("message", "文件服务器出错");
			return map;
		}
		map.put("error", 0);
		String mediaServer = WebEnv.get("server.path.media","");
		String url = mediaServer +"imageservice?mediaImageId="+ imageId +"&mediaName="+ fileName +"&mediaType=download";
		map.put("url", url);
		return map;
	}


    @RequestMapping("/uploadAttachment")
    @ResponseBody
    public AjaxResponse uploadAttachment(@RequestParam(value = "addAttachment", required = true) MultipartFile file, HttpServletRequest request){
        //判断图片大小是否大于2M
        if (file.getSize() > 2 * 1024 * 1024 || file.getSize() == 0) {
            return AjaxResponse.instance(ProductCons.FILE_SIZE_ERROR_CODE, ProductCons.FILE_SIZE_ERROR_MSG) ;
        }
        String fileName = file.getOriginalFilename();
        String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //判断格式是否合法
        if(ProductCons.SAFE_FILE_EXT_NAME.indexOf(extensionName.toLowerCase()) == -1){
            return AjaxResponse.instance(ProductCons.FILE_EXTNAME_ERROR_CODE, ProductCons.FILE_EXTNAME_ERROR_MSG) ;
        }
        String imageId = "";
        try {
            imageId = MediaClientUtil.uploadFile(file.getBytes(), MediaType.IMAGE.value(), fileName);
        } catch (Exception e) {
            return AjaxResponse.instance(ProductCons.FILE_SERVER_ERROR_CODE, ProductCons.FILE_SERVER_ERROR_MSG) ;
        }
        return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, imageId) ;
    }
}
