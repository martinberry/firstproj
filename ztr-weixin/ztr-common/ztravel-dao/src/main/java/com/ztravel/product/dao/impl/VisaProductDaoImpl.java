package com.ztravel.product.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.github.jmkgreen.morphia.query.UpdateResults;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.dao.IVisaProductDao;
import com.ztravel.product.po.pieces.visa.VisaProduct;

@Repository
public class VisaProductDaoImpl extends PieceProductDaoImpl implements IVisaProductDao{

	@Override
	public List<VisaProduct> selectByMap(Map<String,String> map) throws Exception {
		List<VisaProduct> products = null;
		products = selectByMapAndPage(map, null, null);
		return products;
	}

	@Override
	public Boolean delete(Nature nature, String id){
		Query<VisaProduct> query = datastore.createQuery(VisaProduct.class).filter("_id", new ObjectId(id)) ;
		return datastore.delete(query).getN() == 1;
	}

	@Override
	public VisaProduct getProductById(String id) throws Exception{
		return datastore.createQuery(VisaProduct.class).filter("_id", new ObjectId(id)).get();
	}
	@Override
	public VisaProduct getProductByPid(String pid) throws Exception{
		return datastore.createQuery(VisaProduct.class).filter("pid", pid).get();
	}

	@Override
	public List<VisaProduct> selectByMapAndPage(Map<String, String> map,Integer pageNum, Integer pageSize) throws Exception {
		Query<VisaProduct> lvQuery = datastore.createQuery(VisaProduct.class);
		List<VisaProduct> products = null;
		if (!CollectionUtils.isEmpty(map)) {
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
		}
		if(null != pageNum && null != pageSize){
			pageSize = pageSize < 1 ? 1 : pageSize;
			pageNum = pageNum < 0 ? 10 : pageNum;
			int offset = (pageNum - 1) * pageSize;
			lvQuery.offset(offset);
			lvQuery.limit(pageNum);
		}
		lvQuery.order("-updateTime");
		products = lvQuery.asList();
		return products;
	}

    @Override
    public int updateVisaInfo(VisaProduct visaProduct) {
        Query<VisaProduct> query = datastore.createQuery(VisaProduct.class);
        query.field("id").equal(visaProduct.getId());

        UpdateOperations<VisaProduct> ups = datastore.createUpdateOperations(VisaProduct.class);
        if (visaProduct.getPid() != null) ups.set("pid", visaProduct.getPid());
        ups.set("updateTime", DateTime.now());
        ups.set("updator", redisClient.get(OperatorSidHolder.get()));
        if (visaProduct.getStatus() != null) ups.set("status", visaProduct.getStatus());
        if (visaProduct.getDestinationType() != null) ups.set("destinationType", visaProduct.getDestinationType());
        if (visaProduct.getNature() != null) ups.set("nature", visaProduct.getNature());
        if (visaProduct.getProgress() != null) ups.set("progress", visaProduct.getProgress());
        if (visaProduct.getDetailInfo() != null) ups.set("detailInfo", visaProduct.getDetailInfo());
        if (visaProduct.getAdditionalInfos() != null) ups.set("additionalInfos", visaProduct.getAdditionalInfos());
        if (visaProduct.getMaterias() != null) ups.set("materias", visaProduct.getMaterias());
        if (!CollectionUtils.isEmpty(visaProduct.getProcesses())) ups.set("processes", visaProduct.getProcesses());

        UpdateResults<VisaProduct> results = datastore.update(query, ups);
        return results.getUpdatedCount();
    }
    @Override
	public List<VisaProduct> search(Map<String,String> params) throws Exception {
		Query<VisaProduct> query = datastore.createQuery(VisaProduct.class);
		if( params.containsKey("from") ){
			query.field("from").equal(params.get("from"));
		}
		if( params.containsKey("toContinent")){
			query.field("toContinent").hasThisOne(params.get("toContinent"));
		}
		if( params.containsKey("toCountry") ){
			query.field("toCountry").hasThisOne(params.get("toCountry"));
		}
		query.field("status").equal(ProductStatus.RELEASED);
		query.order("-updatedTime");
		return query.asList();
	}
}
