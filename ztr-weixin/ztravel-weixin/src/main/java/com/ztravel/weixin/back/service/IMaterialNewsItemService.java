package com.ztravel.weixin.back.service;

import java.util.List;

import com.ztravel.weixin.back.entity.MaterialNewsItemEntity;
import com.ztravel.weixin.back.entity.ShowMaterialEntity;

public interface IMaterialNewsItemService {

    /**
     * 处理全量同步数据，不存在就新增，存在则修改
     * @param list
     */
    public void collectNewsItemForAll(List<MaterialNewsItemEntity> list);

    /**
     * 处理增量数据，不存在就新增，存在则不处理
     * @param list
     */
    public void collectNewsItemForAdd(List<MaterialNewsItemEntity> list);
    public ShowMaterialEntity newsConvert(MaterialNewsItemEntity news,List<String> titlelist);
    public List<MaterialNewsItemEntity> selectrelated(String mediaId);
    public List<ShowMaterialEntity> NewsShow(MaterialNewsItemEntity newsp);
    public Integer countNews(String title) throws Exception;
    public List<String> selectRelated(String title);
    public void deleteAllNews();
}