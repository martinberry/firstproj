package com.ztravel.search.product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.Filter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class ProductIndexDemo {
	
	
	private static List<String> cityArray = new ArrayList<String>();
	
	private static List<String> cityArrayPy = new ArrayList<String>();
	
	private static final Analyzer analyzer = new IKAnalyzer(false);
	
	private static Directory index =  null;;
	
	private static IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
	
	static {
		
		try {
			index = new MMapDirectory(new File("/Users/liuzhuo/Lucene/mmapDirectory/demo"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cityArray.add("中 中国");
		cityArray.add("白俄罗斯");
		cityArray.add("新加坡");
		cityArray.add("巴西");
		cityArray.add("波黑");
		cityArray.add("玻利维亚");
		cityArray.add("澳大利亚阿根廷");
		cityArray.add("荆州");
		cityArray.add("阿斯顿马丁");
		cityArray.add("阿富汗战争");
		
		cityArrayPy.add("beijing");
		cityArrayPy.add("shanghai");
		cityArrayPy.add("wuhan");
		cityArrayPy.add("tianmen");
		cityArrayPy.add("haikou");
		cityArrayPy.add("xiamen");
		cityArrayPy.add("quznhou");
		cityArrayPy.add("jingzhou");
		cityArrayPy.add("qianjiang");
		cityArrayPy.add("hangzhou");
	}
	
	private static void wirterIndex() {
		IndexWriter writer = null;
		try {
			writer  = new IndexWriter(index, config);
			for(int i = 0; i < 10; i++) {
				Document document = new Document();
				document.add(new TextField("city",cityArray.get(i),Store.YES));
				document.add(new TextField("cityPy",cityArrayPy.get(i),Store.YES));
				document.add(new LongField("price",1000*i,Store.YES));
				writer.addDocument(document);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.commit();
				writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void searchIndex() {  
	    DirectoryReader ireader;
		try {
//		    IndexWriter writerS  = new IndexWriter(index, config);
			ireader = DirectoryReader.open(index);
		    IndexSearcher isearcher = new IndexSearcher(ireader);
		    
		    String[] fields = {"city","cityPy"};
		    String searchKey = "shanghai";
		    BooleanClause.Occur[] flags = { BooleanClause.Occur.SHOULD,
              		BooleanClause.Occur.SHOULD};
		    
		    
		    Query query = null;
		    query = MultiFieldQueryParser.parse(searchKey, fields,flags, analyzer);
		    
		    QueryParser parser = new QueryParser("city", analyzer);
		    parser.setAllowLeadingWildcard(true);
		    query = parser.parse("阿斯顿");
		    
//		    query = new FuzzyQuery(new Term("city", "汗"));
		    
		    Filter filter = null;
//    		filter = NumericRangeFilter.newLongRange("price", 1000l, 8000l, true, true);
		    
		    
		   
		    ScoreDoc[] hits = isearcher.search(query, filter, 1000).scoreDocs;
		    for (int i = 0; i < hits.length; i++) {
		      Document hitDoc = isearcher.doc(hits[i].doc);
		      System.out.println(hitDoc.get("city"));
		      System.out.println(hitDoc.get("cityPy"));
		      System.out.println(hitDoc.get("price"));
		    }
		    ireader.close();
		    index.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}  
	
	
	public static void getAllDoc() {
		
	    DirectoryReader reader;
		try {
		    reader = DirectoryReader.open(index);
			for (int i=0; i<reader.maxDoc(); i++) { 
	
			    Document doc = reader.document(i); 
			    
			    String city = doc.get("city");
			    System.out.println(city);
	
			    // do something with docId here... 
			} 
		}catch(Exception e) {
			
		}	
	}
	
	public static void main(String[] args) {
//		wirterIndex();
		searchIndex();
//		getAllDoc();
	}
		

		

}
