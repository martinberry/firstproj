package com.ztravel.weixin.back.service;

import java.util.List;

import com.ztravel.weixin.back.entity.MaterialMediaEntity;
import com.ztravel.weixin.back.entity.ShowMaterialEntity;
import com.ztravel.weixin.bean.BaseMessageBean;

public interface IMaterialMediaService {

    /**
     * 处理全量同步数据，不存在就新增，存在则修改
     * @param list
     */
    public void collectMaterialMediaForAll(List<MaterialMediaEntity> list);

    /**
     * 处理增量数据，不存在就新增，存在则不处理
     * @param list
     */
    public void collectMaterialMediaForAdd(List<MaterialMediaEntity> list);
    public ShowMaterialEntity mediaConvert(MaterialMediaEntity media);
    public  MaterialMediaEntity selectmediabyid(String mediaId);
    public Integer countMedia(String name) throws Exception;
    public List<ShowMaterialEntity> MediaShow(MaterialMediaEntity mediap,int templimit,int newstotalPageCount);
	public BaseMessageBean buildMessageByKey(String eventKey);

	public void save(MaterialMediaEntity materialMedia) throws Exception;
	public void deleteAllMedia();

}