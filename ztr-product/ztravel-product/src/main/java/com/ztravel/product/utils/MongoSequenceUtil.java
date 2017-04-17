package com.ztravel.product.utils;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.github.jmkgreen.morphia.utils.LongIdEntity.StoredId;
import com.travelzen.framework.spring.web.context.SpringApplicationContext;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.po.pieces.common.PieceProduct;

public class MongoSequenceUtil {

    private static Datastore datastore = null ;

    //酒店自动生成ID前缀
  	public static final String ZTRAVEL_HOTEL_SEQUENCE_PREFIX = "J";
  	//产品自动生成PID前缀
  	public static final String ZTRAVEL_PRODUCT_SEQUENCE_PREFIX = "Z";
    //碎片化产品自动生成PID前缀
    public static final String ZTRAVEL_PIECE_PRODUCT_SEQUENCE_PREFIX = "S";

    public static <T> Long generateLongId(Class<T> clazz) {
    	datastore = SpringApplicationContext.getApplicationContext().getBean("ztravelDatastore", Datastore.class) ;
        if(datastore != null) {
            String collName = datastore.getCollection(clazz).getName();
            Query<StoredId> q = datastore.find(StoredId.class, "_id", collName);
            UpdateOperations<StoredId> uOps = datastore.createUpdateOperations(StoredId.class).inc("value");
            StoredId newId = datastore.findAndModify(q, uOps);
            if (newId == null) {
                newId = new StoredId(collName);
                datastore.save(newId);
            }
            return newId.getValue() ;
        }else{
                return null ;
        }
    }

    public static String generateHotelEntityId(){
    	return ZTRAVEL_HOTEL_SEQUENCE_PREFIX + (10000 + generateLongId(HotelEntity.class)) ;
    }
    public static String generateProductEntityPid(){
    	return ZTRAVEL_PRODUCT_SEQUENCE_PREFIX + (10000 + generateLongId(Product.class)) ;
    }
    public static String generatePieceProductEntityPid(){
        return ZTRAVEL_PIECE_PRODUCT_SEQUENCE_PREFIX + String.valueOf((100000 + generateLongId(PieceProduct.class))).substring(1) ;
    }
    

}
