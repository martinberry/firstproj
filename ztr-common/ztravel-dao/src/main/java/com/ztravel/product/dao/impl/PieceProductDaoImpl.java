package com.ztravel.product.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Key;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.github.jmkgreen.morphia.query.UpdateResults;
import com.google.common.collect.Lists;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.PieceType;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.dao.IPieceProductDao;
import com.ztravel.product.po.pieces.common.BasicInfo;
import com.ztravel.product.po.pieces.common.PieceProduct;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

@Repository
public class PieceProductDaoImpl implements IPieceProductDao{

    @Resource
    Datastore datastore ;

    protected static final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public String save(PieceProduct product) throws Exception {
		Key<PieceProduct> result = datastore.save(product);
		if (result.getId() != null) {
			return result.getId().toString();
		} else {
			throw new RuntimeException("产品保存失败");
		}
	}

	@Override
	public Boolean delete(Nature nature, String id) {
	    if (nature != null && Nature.VISA.equals(nature)) {
	        Query<VisaProduct> query = datastore.createQuery(VisaProduct.class).filter("_id", new ObjectId(id));
	        return datastore.delete(query).getN() == 1;
	    } else if (nature != null && Nature.UNVISA.equals(nature)) {
	        Query<UnVisaProduct> query = datastore.createQuery(UnVisaProduct.class).filter("_id", new ObjectId(id));
	        return datastore.delete(query).getN() == 1;
	    }
		return false;
	}


    @Override
    public String insertPieceBasicInfo(PieceProduct product) {
        product.setCreateTime(DateTime.now());
        product.setCreator(redisClient.get(OperatorSidHolder.get()));
        product.setUpdateTime(product.getCreateTime());
        product.setUpdator(product.getCreator());
        String id = null;
        if (product.getBasicInfo() != null && PieceType.VISA.equals(product.getBasicInfo().getType())) {
            VisaProduct visaProduct = (VisaProduct) product;
            Key<VisaProduct> result = datastore.save(visaProduct);
            if (result.getId() != null) {
                id = result.getId().toString();
            }
        } else {
            UnVisaProduct unVisaProduct = (UnVisaProduct) product;
            Key<UnVisaProduct> result = datastore.save(unVisaProduct);
            if (result.getId() != null) {
                id = result.getId().toString();
            }
        }
        return id;
    }

    @Override
    public int updatePiecePublicInfo(PieceProduct product) {

        if (product.getNature() != null && Nature.VISA.equals(product.getNature())) {
            return updatePieceProduct(product, VisaProduct.class);
        } else {
            return updatePieceProduct (product, UnVisaProduct.class);
        }
    }

    @Override
    public <T> int updatePieceProduct(PieceProduct product, Class<T> clazz) {

        Query<T> query = datastore.createQuery(clazz);
        query.field("id").equal(product.getId());

        UpdateOperations<T> ups = datastore.createUpdateOperations(clazz);
        if (product.getPid() != null) ups.set("pid", product.getPid());
        ups.set("updateTime", DateTime.now());
        ups.set("updator", redisClient.get(OperatorSidHolder.get()));
        if (product.getStatus() != null) ups.set("status", product.getStatus());
        if (product.getDestinationType() != null) ups.set("destinationType", product.getDestinationType());
        if (product.getNature() != null) ups.set("nature", product.getNature());
        if (product.getProgress() != null) ups.set("progress", product.getProgress());
        if (product.getBasicInfo() != null) ups.set("basicInfo", product.getBasicInfo());
        if (product.getPriceInfos() != null) ups.set("priceInfos", product.getPriceInfos());

        UpdateResults<T> results = datastore.update(query, ups);
        return results.getUpdatedCount();
    }

    @Override
    public PieceProduct queryPieceProductById(String id, String nature) {
        if (Nature.VISA.name().equals(nature)) {
            return queryVisaProductById(id);
        } else if (Nature.UNVISA.name().equals(nature)) {
            return queryUnVisaProductById(id);
        }
        return null;
    }

    @Override
    public VisaProduct queryVisaProductById(String id) {
        Query<VisaProduct> query = datastore.createQuery(VisaProduct.class).filter("_id", new ObjectId(id));
        return query.get();
    }


    @Override
    public UnVisaProduct queryUnVisaProductById(String id) {
        Query<UnVisaProduct> query = datastore.createQuery(UnVisaProduct.class).filter("_id", new ObjectId(id));
        return query.get();
    }

    @Override
    public Boolean updatePieceStatus(String id, Nature nature, ProductStatus status) {
        if (Nature.VISA.equals(nature)) {
            Query<VisaProduct> q = datastore.createQuery(VisaProduct.class).filter("_id", new ObjectId(id));
            UpdateOperations<VisaProduct> ops = datastore.createUpdateOperations(VisaProduct.class).set("status", status);
            ops.set("updateTime", DateTime.now());
            ops.set("updator", redisClient.get(OperatorSidHolder.get()));
            return datastore.findAndModify(q, ops) != null;
        } else if (Nature.UNVISA.equals(nature)) {
            Query<UnVisaProduct> q = datastore.createQuery(UnVisaProduct.class).filter("_id", new ObjectId(id));
            UpdateOperations<UnVisaProduct> ops = datastore.createUpdateOperations(UnVisaProduct.class).set("status", status);
            ops.set("updateTime", DateTime.now());
            ops.set("updator", redisClient.get(OperatorSidHolder.get()));
            return datastore.findAndModify(q, ops) != null;
        }
        return false;
    }

    @Override
    public Long getCountByConditions(Map<String, Map<String, String>> conditions) {
        Long count = 0L;
        List<VisaProduct> visaProductList = findVisaByConditions(conditions);
        count += visaProductList.size();
        List<UnVisaProduct> unVisaProductList = findUnVisaByConditions(conditions);
        count += unVisaProductList.size();

        return count;
    }

    @Override
    public List<PieceProduct> findByConditions(Map<String, Map<String, String>> conditions, int pageNum, int pageSize) {
        List<PieceProduct> pieceProductList = Lists.newArrayList();

        List<PieceProduct> allPieceProductList = Lists.newArrayList();
        List<VisaProduct> visaProductList = findVisaByConditions(conditions);
        if (CollectionUtils.isNotEmpty(visaProductList)) {
            for (VisaProduct visaProduct : visaProductList) {
                allPieceProductList.add(visaProduct);
            }
        }
        List<UnVisaProduct> unVisaProductList = findUnVisaByConditions(conditions);
        if (CollectionUtils.isNotEmpty(unVisaProductList)) {
            if (CollectionUtils.isNotEmpty(allPieceProductList)) {
                int start_index = 0;
                for (UnVisaProduct unVisaProduct : unVisaProductList) {
                    for (int i = start_index; i < allPieceProductList.size(); i ++) {
                        if (unVisaProduct.getUpdateTime().isAfter(allPieceProductList.get(i).getUpdateTime())) {
                            allPieceProductList.add(i, unVisaProduct);
                            start_index = i + 1;
                            break ;
                        }
                    }
                }
            } else {
                for (UnVisaProduct unVisaProduct : unVisaProductList) {
                    allPieceProductList.add(unVisaProduct);
                }
            }
        }

        int allSize = allPieceProductList.size();

        if (pageNum > 0 && pageSize > 0) {
            int startInt = (pageNum - 1) * pageSize;
            int endInt = (pageNum * pageSize) > allSize ? allSize : pageNum * pageSize ;
            if (allSize > 0 && allSize >= startInt) {
                for (int i = startInt; i < endInt; i++) {
                    pieceProductList.add(allPieceProductList.get(i));
                }
            }
        }

        return pieceProductList;
    }

    @Override
    public List<VisaProduct> findVisaByConditions(Map<String, Map<String, String>> conditions) {
        Query<VisaProduct> visaQuery = datastore.createQuery(VisaProduct.class).order("-updateTime");
        if (null == conditions) {
            return visaQuery.asList();
        }

        if (conditions.get("sub") != null) {
            Map<String, String> subParams = conditions.get("sub");
            for (Entry<String, String> entry : subParams.entrySet()) {
                if (StringUtils.isNotBlank(entry.getValue())) {
                    visaQuery.field(entry.getKey()).contains(entry.getValue());
                }
            }
        }
        List<VisaProduct> visaResult = visaQuery.asList();
        if (conditions.get("basic") != null && CollectionUtils.isNotEmpty(visaResult)) {
            Map<String, String> basicParams = conditions.get("basic");
            Iterator<VisaProduct> it = visaResult.iterator();
            while (it.hasNext()) {
                VisaProduct visaProduct = it.next();
                BasicInfo basicInfo = visaProduct.getBasicInfo();
                if (StringUtils.isNotBlank(basicParams.get("pname")) && (StringUtils.isBlank(basicInfo.getPname()) || !basicInfo.getPname().contains(basicParams.get("pname")))) {
                    it.remove();
                    continue ;
                }
                basicInfo.setPname(basicInfo.getPname().replaceAll("nbsp;", "n"));
                if (StringUtils.isNotBlank(basicParams.get("type")) && (basicInfo.getType() == null || !basicParams.get("type").equals(basicInfo.getType().name()))) {
                    it.remove();
                    continue ;
                }
                if (StringUtils.isNotBlank(basicParams.get("toContinent")) && (CollectionUtils.isEmpty(basicInfo.getToContinent()) || !basicInfo.getToContinent().contains(basicParams.get("toContinent")))) {
                    it.remove();
                    continue ;
                }
                if (StringUtils.isNotBlank(basicParams.get("toCountry")) && (CollectionUtils.isEmpty(basicInfo.getToCountry()) || !basicInfo.getToCountry().contains(basicParams.get("toCountry")))) {
                    it.remove();
                    continue ;
                }
                if (StringUtils.isNotBlank(basicParams.get("toCity")) && (CollectionUtils.isEmpty(basicInfo.getToCity()) || !basicInfo.getToCity().contains(basicParams.get("toCity")))) {
                    it.remove();
                    continue ;
                }
            }
        }
        return visaResult;
    }

    @Override
    public List<UnVisaProduct> findUnVisaByConditions(Map<String, Map<String, String>> conditions) {
        Query<UnVisaProduct> unVisaQuery = datastore.createQuery(UnVisaProduct.class).order("-updateTime");
        if (null == conditions) {
            return unVisaQuery.asList();
        }

        if (conditions.get("sub") != null) {
            Map<String, String> subParams = conditions.get("sub");
            for (Entry<String, String> entry : subParams.entrySet()) {
                if (StringUtils.isNotBlank(entry.getValue())) {
                    unVisaQuery.field(entry.getKey()).contains(entry.getValue());
                }
            }
        }
        List<UnVisaProduct> unVisaResult = unVisaQuery.asList();
        if (conditions.get("basic") != null && CollectionUtils.isNotEmpty(unVisaResult)) {
            Map<String, String> basicParams = conditions.get("basic");
            Iterator<UnVisaProduct> it = unVisaResult.iterator();
            while (it.hasNext()) {
                UnVisaProduct unVisaProduct = it.next();
                BasicInfo basicInfo = unVisaProduct.getBasicInfo();
                if (StringUtils.isNotBlank(basicParams.get("pname")) && (StringUtils.isBlank(basicInfo.getPname()) || !basicInfo.getPname().contains(basicParams.get("pname")))) {
                    it.remove();
                    continue ;
                }
                if (StringUtils.isNotBlank(basicParams.get("type")) && (basicInfo.getType() == null || !basicParams.get("type").equals(basicInfo.getType().name()))) {
                    it.remove();
                    continue ;
                }
                if (StringUtils.isNotBlank(basicParams.get("toContinent")) && (CollectionUtils.isEmpty(basicInfo.getToContinent()) || !basicInfo.getToContinent().contains(basicParams.get("toContinent")))) {
                    it.remove();
                    continue ;
                }
                if (StringUtils.isNotBlank(basicParams.get("toCountry")) && (CollectionUtils.isEmpty(basicInfo.getToCountry()) || !basicInfo.getToCountry().contains(basicParams.get("toCountry")))) {
                    it.remove();
                    continue ;
                }
                if (StringUtils.isNotBlank(basicParams.get("toCity")) && (CollectionUtils.isEmpty(basicInfo.getToCity()) || !basicInfo.getToCity().contains(basicParams.get("toCity")))) {
                    it.remove();
                    continue ;
                }
            }
        }
        return unVisaResult;
    }
}
