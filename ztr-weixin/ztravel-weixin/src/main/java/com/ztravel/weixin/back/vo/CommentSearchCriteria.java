package com.ztravel.weixin.back.vo;

import com.ztravel.common.entity.PaginationEntity;

public class CommentSearchCriteria extends PaginationEntity{
private String commentId;
private String sortType;
private String topicId;


public String getTopicId() {
	return topicId;
}

public void setTopicId(String topicId) {
	this.topicId = topicId;
}

public String getSortType() {
	return sortType;
}

public void setSortType(String sortType) {
	this.sortType = sortType;
}

public String getCommentId() {
	return commentId;
}

public void setCommentId(String commentId) {
	this.commentId = commentId;
}

}
