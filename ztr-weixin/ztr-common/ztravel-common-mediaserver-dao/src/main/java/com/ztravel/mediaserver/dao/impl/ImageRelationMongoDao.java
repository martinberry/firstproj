package com.ztravel.mediaserver.dao.impl;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.ztravel.mediaserver.db.projo.ImageRelation;
import com.ztravel.mediaserver.exception.MediaServerException;

@Repository
public class ImageRelationMongoDao extends RelationMongoBaseDao<ImageRelation, ObjectId> {

	public String updateOrCreate(ImageRelation imageRelation) throws MediaServerException {

		ImageRelation resultRelation = super.getImageUrl("mediaId", imageRelation.getMediaId());

		if (resultRelation == null) {
			return super.addImageUrl(imageRelation);
		} else if (imageRelation.getImageUrl().equals(resultRelation.getImageUrl())) {
			return resultRelation.getId().toString();
		} else {
			super.deleteById(resultRelation.getId());
			return super.addImageUrl(imageRelation);
		}
	}
}
