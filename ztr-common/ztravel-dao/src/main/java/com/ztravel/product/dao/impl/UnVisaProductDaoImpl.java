package com.ztravel.product.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.github.jmkgreen.morphia.query.UpdateResults;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.dao.IUnVisaProductDao;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;

@Repository
public class UnVisaProductDaoImpl extends PieceProductDaoImpl implements IUnVisaProductDao {
	@Override
	public List<UnVisaProduct> selectByMap(Map<String,String> map) throws Exception {
		Query<UnVisaProduct> lvQuery = datastore.createQuery(UnVisaProduct.class);
		List<UnVisaProduct> products = null;
		if (null != map) {
			for (Entry<String, String> entry : map.entrySet()) {
				if(StringUtils.isNotBlank(entry.getValue())){
					if(entry.getKey().equals("status")){
						lvQuery.field(entry.getKey()).equal(ProductStatus.valueOf(entry.getValue()));
					}else if(entry.getKey().equals("toCountry")){
						lvQuery.field("basicInfo.toCountry").hasThisOne(entry.getValue());
					}else if(entry.getKey().equals("toContinent")){
						lvQuery.field("basicInfo.toContinent").hasThisOne(entry.getValue());
					}else{
						lvQuery.field(entry.getKey()).contains(entry.getValue());
					}
				}
			}
			lvQuery.order("-updateTime");
			products = lvQuery.asList();
		}
		return products;
	}

	@Override
	public Boolean delete(Nature nature, String id){
		Query<UnVisaProduct> query = datastore.createQuery(UnVisaProduct.class).filter("_id", new ObjectId(id)) ;
		return datastore.delete(query).getN() == 1;
	}
	@Override
	public UnVisaProduct getProductById(String id) {
		return datastore.createQuery(UnVisaProduct.class).filter("_id", new ObjectId(id)).get();
	}


    @Override
    public int updateUnVisaInfo(UnVisaProduct unVisaProduct) {
        Query<UnVisaProduct> query = datastore.createQuery(UnVisaProduct.class);
        query.field("id").equal(unVisaProduct.getId());

        UpdateOperations<UnVisaProduct> ups = datastore.createUpdateOperations(UnVisaProduct.class);
        if (unVisaProduct.getPid() != null) ups.set("pid", unVisaProduct.getPid());
        ups.set("updateTime", DateTime.now());
        ups.set("updator", redisClient.get(OperatorSidHolder.get()));
        if (unVisaProduct.getStatus() != null) ups.set("status", unVisaProduct.getStatus());
        if (unVisaProduct.getDestinationType() != null) ups.set("destinationType", unVisaProduct.getDestinationType());
        if (unVisaProduct.getNature() != null) ups.set("nature", unVisaProduct.getNature());
        if (unVisaProduct.getProgress() != null) ups.set("progress", unVisaProduct.getProgress());
        if (unVisaProduct.getDetailInfo() != null) ups.set("detailInfo", unVisaProduct.getDetailInfo());
        if (unVisaProduct.getAdditionalInfos() != null) ups.set("additionalInfos", unVisaProduct.getAdditionalInfos());

        UpdateResults<UnVisaProduct> results = datastore.update(query, ups);
        return results.getUpdatedCount();
    }

	@Override
	public UnVisaProduct getUnvisaProductByPid(String pid) {
		return datastore.createQuery(UnVisaProduct.class).filter("pid",pid).get();
	}
}
