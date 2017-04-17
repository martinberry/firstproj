package com.ztravel.search.autoComplete.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.swordfish.thrift.client.annotation.ThriftServiceEndpoint;
import com.ztravel.search.autoComplete.index.AutoCompIndexHolder;
import com.ztravel.sraech.client.service.IAutoComplete;


@Service(value="AutoCompleteService")
@ThriftServiceEndpoint
public class AutoCompleteService implements IAutoComplete{
	
	
	private static final Logger LOG = RequestIdentityLogger.getLogger(AutoCompleteService.class);

	@Override
	public List<String> countryAutoComplete(String searchKey, int limit) {
		try {
			
			LOG.info("begin to search country ,serach key is :" + searchKey);
			
			SearcherManager searcherManager = new SearcherManager(AutoCompIndexHolder.dir, null);
		    IndexSearcher isearcher = searcherManager.acquire();
		    
		    
		    /*search example one
		     * 
		    String queryS = {searchKey, searchKey+"*~", searchKey+"*~"};
		    String[] fields = {"nameCn", "nameEn", "threeLetterCode"};
		    BooleanClause.Occur[] flags = { BooleanClause.Occur.SHOULD,
		    		                  		BooleanClause.Occur.SHOULD,
		    		                  		BooleanClause.Occur.SHOULD};
		    Query query = MultiFieldQueryParser.parse(searchKey,fields ,flags, IndexHolder.analyzer);
		    
		    */
		    
		    /*
		    Query nameCnQ  = new FuzzyQuery(new Term("nameCn", searchKey+"*"));
		    Query nameEnQ = new FuzzyQuery(new Term("nameEn", searchKey+"*"));
		    Query threeLetterCodeQ = new FuzzyQuery(new Term("threeLetterCode", searchKey+"*"));
		    
		    BooleanQuery query = new BooleanQuery();
		    query.add(nameCnQ, BooleanClause.Occur.SHOULD);
		    query.add(nameEnQ, BooleanClause.Occur.SHOULD);
		    query.add(threeLetterCodeQ, BooleanClause.Occur.SHOULD);
		    */
		    
		    QueryParser parser = new QueryParser("nameCnSplit", AutoCompIndexHolder.analyzer);
		    Query query = parser.parse(searchKey);
		    
		    TopDocs topDocs = isearcher.search(query, limit);
		    
		    List<String> result = buildResultByDocs(topDocs, isearcher);
		    
		    searcherManager.release(isearcher);
		    
			LOG.info("finish search country ,result key is :\n" + result);
		    return result;
		} catch (IOException e) {
			LOG.error(TZMarkers.p2, "国家自动补全索查询IO异常", e);
		} 
		catch (ParseException e) {
			LOG.error(TZMarkers.p2, "国家自动补全索查询parse异常", e);
		}
	    

		return null;
	}
	
	private List<String> buildResultByDocs(TopDocs topDocs,IndexSearcher isearcher) throws IOException {
		List<String> result = new LinkedList<String>();
		
		for(ScoreDoc scoreDoc :topDocs.scoreDocs) {
			JSONObject temp = new JSONObject();
			temp.put("nameCn", isearcher.doc(scoreDoc.doc).get("nameCn").toString().replaceAll(" ", ""));
			temp.put("nameEn", isearcher.doc(scoreDoc.doc).get("nameEn"));
			temp.put("threeLetterCode", isearcher.doc(scoreDoc.doc).get("threeLetterCode"));
			result.add(temp.toString());
		}
		
		return result;
	}

}
