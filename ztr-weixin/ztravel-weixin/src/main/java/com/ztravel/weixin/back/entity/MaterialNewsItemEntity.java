package com.ztravel.weixin.back.entity;

import org.joda.time.DateTime;

import com.ztravel.common.entity.PaginationEntity;

public class MaterialNewsItemEntity extends PaginationEntity{

    private String id;

    private String title;

    private String thumbMediaId;

    private String showCoverPic;

    private String author;

    private String digest;

    private String url;

    private String contentSourceUrl;

    private String relatedMediaId;

    private int priority;

    private DateTime lastModifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(String showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentSourceUrl() {
        return contentSourceUrl;
    }

    public void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl;
    }

    public String getRelatedMediaId() {
        return relatedMediaId;
    }

    public void setRelatedMediaId(String relatedMediaId) {
        this.relatedMediaId = relatedMediaId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public DateTime getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(DateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

}
