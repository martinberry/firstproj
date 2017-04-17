package com.ztravel.search.test.autocomp;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.MMapDirectory;
import org.jsoup.helper.StringUtil;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.ztravel.common.util.WebEnv;
import com.ztravel.search.autoComplete.index.AutoCompIndexHolder;

public class CountryTest {

	@Test
	public void test() {

		try {
			
			SearcherManager searcherManager = new SearcherManager(new MMapDirectory(new File(AutoCompIndexHolder.DIR_PATH)), null);
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
		    
		    
		    Query nameCnQ  = new TermQuery(new Term("nameCn", "国"));
		    Query nameEnQ = new FuzzyQuery(new Term("nameEn", "ch"));
		    Query threeLetterCodeQ = new FuzzyQuery(new Term("threeLetterCode", "9"));
		    
		    BooleanQuery query = new BooleanQuery();
		    query.add(nameCnQ, BooleanClause.Occur.SHOULD);
		    query.add(nameEnQ, BooleanClause.Occur.SHOULD);
		    query.add(threeLetterCodeQ, BooleanClause.Occur.SHOULD);
		    
		    TopDocs topDocs = isearcher.search(query, 5);
		    
		    
		    
//		    System.out.println(isearcher.getIndexReader().maxDoc());
//		    
//		    for (int i=0; i<isearcher.getIndexReader().maxDoc(); i++) { 
//
//		        Document doc = isearcher.getIndexReader().document(i); 
//		        System.out.println(doc.get("nameCn"));
//		    } 

		    
			for(ScoreDoc scoreDoc :topDocs.scoreDocs) {
				JSONObject temp = new JSONObject();
				temp.put("nameCn", isearcher.doc(scoreDoc.doc).get("nameCn"));
				temp.put("nameEn", isearcher.doc(scoreDoc.doc).get("nameEn"));
				temp.put("threeLetterCode", isearcher.doc(scoreDoc.doc).get("threeLetterCode"));
				System.out.println(temp.toString());
			}
		    searcherManager.release(isearcher);
		    
		} catch (IOException e) {
			e.printStackTrace();
		} 
//		catch (ParseException e) {
//			LOG.error(TZMarkers.p2, "国家自动补全索查询parse异常", e);
//		}
	    

	
	}
	

	private String buildNameCnField(String nameCn) {
		LinkedList<String> spiltArray = new LinkedList<String>();
		
		for(int i = 1; i <= nameCn.length(); i++) {
			spiltArray.add(nameCn.substring(0, i));
		}
		
		return StringUtil.join(spiltArray, " ");
	}
	
	@Test
	public void buildNameCnTest() {
		System.out.println(buildNameCnField("阿富汗"));
	}
	
	
}
