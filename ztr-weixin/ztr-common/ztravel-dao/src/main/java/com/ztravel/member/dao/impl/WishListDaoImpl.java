package com.ztravel.member.dao.impl;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Key;
import com.github.jmkgreen.morphia.query.Query;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.ztravel.member.dao.IWishListDao;
import com.ztravel.member.po.WishEntity;

@Repository
public class WishListDaoImpl implements IWishListDao {

	@Resource
	Datastore datastore;

	@Override
	public String insertWish(String memberId, WishEntity wish) {
		wish.setMemberId(memberId);
		wish.setCreatedTime(new DateTime());
		Key<WishEntity> result = datastore.save(wish);
		return result.getId() != null ? result.getId().toString() : null ;
	}

	@Override
	public List<WishEntity> queryWishs(String memberId, String productId){

		Query<WishEntity> query = datastore.createQuery(WishEntity.class);

		if(StringUtils.isNotBlank(memberId)){
			query.field("memberId").equal(memberId);
		}

		if(StringUtils.isNotBlank(productId)){
			query.field("productId").equal(productId);
		}

		List<WishEntity> list = query.asList();

		return list;
	}

	@Override
	public List<WishEntity> queryWishsByMemberId(String memberId){

		Query<WishEntity> query = datastore.createQuery(WishEntity.class);

		if(StringUtils.isNotBlank(memberId)){
			query.field("memberId").equal(memberId);
		}

		List<WishEntity> list = query.order("-createdTime").asList();

		return list;

	}
	
	@Override
	public Query<WishEntity> queryWishsByProductId(String productId){

		Query<WishEntity> query = datastore.createQuery(WishEntity.class).order("-createdTime");

		if(StringUtils.isNotBlank(productId)){
			query.field("productId").equal(productId);
		}

		return query;

	}

	@Override
	public void deleteWish(String wishId){

		datastore.delete(WishEntity.class, new ObjectId(wishId));

	}

	@Override
	public void deleteWishByProductId(String productId){

		Query<WishEntity> query = datastore.createQuery(WishEntity.class);

		if(StringUtils.isNotBlank(productId)){
			query.field("productId").equal(productId);
		}

		datastore.delete(query);

	}
	
	@Override
	public DBCursor queryWishMembers(String oriPName){
		//Map过程，通过emit函数实现，第一个参数表示用于分组的字段，
        //第二个参数表示分组字段所对应的值
        String map="function() {" +
            "emit(this.productId," +
            "{count: 1, productId: this.productId, recentTime: this.createdTime}); " +
            "}";
        //Reduce过程，遍历values，填充相应业务逻辑。
       String reduce="function(key, values) {" +
            "var reduced = {count:0, productId:key, recentTime:values[0].recentTime};" +
            "values.forEach(function(val) {" +
            "reduced.count += 1;" +
            "var c = val.recentTime;" +
            "if(c>reduced.recentTime) reduced.recentTime = c; });" +
            "return reduced;}";

       String pName = oriPName.replace(".", "\\.").replace("|", "\\|").replace("*", "[\\*]").replace("+", "\\+").replace("\\", "\\\\").replace("/", "\\/")
    		   .replace("?", "\\?").replace("(", "\\(").replace(")", "\\)").replace("[", "\\[").replace("]", "\\]").replace("{", "\\{").replace("}", "\\}");
       Pattern pattern = Pattern.compile("^.*"+pName+".*$", Pattern.CASE_INSENSITIVE);
       DBObject query = new BasicDBObject("pName", pattern);
       MapReduceCommand cmd = new MapReduceCommand(datastore.getCollection(WishEntity.class) , map, reduce,
    		   "wishProduct", MapReduceCommand.OutputType.REPLACE, query);
       MapReduceOutput out = datastore.getCollection(WishEntity.class).mapReduce(cmd);

       return out.getOutputCollection().find();

	}

}
