package com.ztravel.search.product;

import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class IndexServiceImpl implements IndexService{

	

	@Override
	public void writeIndex(JSONObject product) throws Exception {
		
		QueryParser parser = new QueryParser("id", ProductIndexHolder.analyzer);
		Query query = parser.parse(product.get("id").toString());
		ProductIndexHolder.writer.deleteDocuments(query);
		
		Document doc = new Document();
		doc.add(new StringField("fromCity", product.get("fromCity").toString(), Store.YES));
		doc.add(new StringField("toCity",product.get("toCity").toString(),Store.YES));
		doc.add(new StringField("id", product.get("id").toString(), Store.YES));
		doc.add(new StringField("productContent", product.toString(), Store.YES));
		
		ProductIndexHolder.writer.addDocument(doc);
		ProductIndexHolder.writer.commit();
		
	}

	@SuppressWarnings("null")
	@Override
	public List<JSONObject> readIndex(JSONObject searchParams) throws Exception {
		
		List<JSONObject> result = new LinkedList<JSONObject>();
		
		
//	    IndexSearcher isearcher = new IndexSearcher(IndexHolder.getReader());
		
		
		SearcherManager searcherManager = new SearcherManager(ProductIndexHolder.dir, null);
	    
	    IndexSearcher isearcher = searcherManager.acquire();
	    
	    searcherManager.maybeRefresh();
	    
	    /**
	     * multi field search example
	     */
	    String[] fields = {"fromCity","toCity"};
	    String[] queries = {searchParams.get("fromCity").toString(), searchParams.get("toCity").toString()};
	    
	    Query query = MultiFieldQueryParser.parse(queries, fields, ProductIndexHolder.analyzer);
	    
	    /**
	     * filter example
	     */
	    Filter filter = null;
//	    filter = NumericRangeFilter.newLongRange("price", 1000l, 8000l, true, true);
	    
	    /**
	     * pagination example
	     */
	    Integer pageSize = searchParams.getInteger("pageSize");
	    Integer pageIndex = searchParams.getInteger("pageIndex");
	    TopScoreDocCollector collector = TopScoreDocCollector.create(pageSize, true);  
	    int startIndex = (pageSize -1) * pageIndex;  
	    isearcher.search(query, filter, collector);
	    TopDocs hits = collector.topDocs(startIndex, pageSize);
	    
	    
	    
	    JSONObject temp = null;
	    for (int i = 0; i < hits.scoreDocs.length; i++) {
	      Document hitDoc = isearcher.doc(hits.scoreDocs[i].doc);
	      temp.put("fromCity", hitDoc.get("fromCity"));
	      temp.put("toCity", hitDoc.get("toCity"));
	      temp.put("id", hitDoc.get("id"));
	      temp.put("productContent", hitDoc.get("productContent"));
	      
	      result.add(temp);
	    }
		
	    
	    searcherManager.release(isearcher);
	    searcherManager.close();
		return result;
	}

}
