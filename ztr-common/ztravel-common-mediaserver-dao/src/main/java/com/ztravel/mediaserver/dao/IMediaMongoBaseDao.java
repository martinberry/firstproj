package com.ztravel.mediaserver.dao;

import com.travelzen.mongo.dao.IImageBasicDao;
import com.ztravel.mediaserver.db.projo.Media;

public interface IMediaMongoBaseDao extends IImageBasicDao {
    Object addMedia(Media media);

    Media getMedia(Object mediaId);

    Object addContract(Media contract);

    Media getContract(Object contractId);
}
