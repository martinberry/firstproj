package com.ztravel.search.autoComplete.index;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.Version;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.github.jmkgreen.morphia.Datastore;
import com.mongodb.DBObject;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.util.WebEnv;

@Component
public class AutoCompIndexHolder {
	
	@Resource
	Datastore datastore;
	
	private static final RedisClient redisClient = RedisClient.getInstance();
	
	private static final Logger LOG = LoggerFactory.getLogger(AutoCompIndexHolder.class);
	
	private static final String COUNTRY_AREA_COLLECTION_NAME = "countryArea";
	
	public static Directory dir =  null;
	
	public static final Analyzer analyzer = new IKAnalyzer();
	
	public static IndexWriter writer = null;
	
	private static IndexWriterConfig config = null;
	
	public static DirectoryReader reader = null;
	
	public static final String DIR_PATH = "/data/lucene/mmapDirectory/autoComplete/country";
	
	@PostConstruct
	public void initIndex() {
		try {
			LOG.info("开始初始化自动补全全局变量");
			dir = new MMapDirectory(new File(DIR_PATH));
			config = new IndexWriterConfig(Version.LATEST, analyzer);
			config.setOpenMode(OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(dir, config);
			LOG.info("判断是否需要构建自动补全索引");
//			if(checkNeedBuildIndex()) {
			LOG.info("开始构建自动补全索引");
			buildIndex();
			LOG.info("构建自动补全索引结束");
//			}
		} catch (Exception e) {
			LOG.error(TZMarkers.p2, "国家自动补全索引初始化失败", e);
		} finally{
			try {
				//IndexerWriter写索引操作关闭，提交写索引（如没关闭会造成索引无法完整创建，查询时出错）
				writer.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 判断是否需要构建自动补全索引
	 * 现根据配置文件判断是否需要新建，再去redis拿锁,保证只有一个节点执行了新建索引的操作
	 */
	public boolean checkNeedBuildIndex() {
		String need = WebEnv.get("needBuildCountryIndex");
		
		if(!Boolean.parseBoolean(need)) {
			return false;
		}
		
		if(redisClient.setnx("needBuildCountryIndex", "") == 0) {
			return false;
		}else if (redisClient.setnx("needBuildCountryIndex", "") == 1){
			redisClient.expire("needBuildCountryIndex", 60 * 30);
		}
		return true;
	}
	
	
	/**
	 * 构建索引
	 * @throws IOException
	 */
	private void buildIndex() throws IOException {
		
		 List<DBObject> countryList = datastore.getDB().getCollection(COUNTRY_AREA_COLLECTION_NAME).find().toArray();
		 List<Document> docList = new LinkedList<Document>();
		 for(DBObject obj : countryList) {
			Document doc =  buildDocByDbObject(obj);
			docList.add(doc);
		 }
		 
		 writer.addDocuments(docList, analyzer);
		 writer.commit();
		
	}
	
	/**
	 * 根据dbobject 构建document
	 * @param obj
	 * @return
	 */
	private Document buildDocByDbObject(DBObject obj) {
		Document doc = new Document();
		doc.add(new TextField("nameCn", obj.get("nameCn").toString(), Store.YES));
		doc.add(new TextField("nameCnSplit", buildNameCnSplitField(obj.get("nameCn").toString()), Store.YES));		
		doc.add(new TextField("nameEn", obj.get("namaEn").toString(), Store.YES));
		doc.add(new TextField("threeLetterCode", obj.get("threeLetterCode").toString(), Store.YES));		
		return doc;
	}
	
	/**
	 * 国家分割
	 * 如 中国  --> 中 中国
	 * @param nameCn
	 * @return
	 */
	private String buildNameCnSplitField(String nameCn) {
		LinkedList<String> spiltArray = new LinkedList<String>();
		
		for(int i = 1; i <= nameCn.length(); i++) {
			for(int j = 0; j < i; j++){
				spiltArray.add(nameCn.substring(j, i));
			}
		}
		
		return StringUtil.join(spiltArray, " ");
	}
	
}
