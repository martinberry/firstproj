package com.ztravel.search.product;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.Version;
import org.springframework.context.annotation.Configuration;
import org.wltea.analyzer.lucene.IKAnalyzer;


@Configuration
public class ProductIndexHolder {
	
	public static Directory dir =  null;
	
	public static final Analyzer analyzer = new IKAnalyzer();
	
	public static IndexWriter writer = null;
	
	private static IndexWriterConfig config = null;
	
	public static DirectoryReader reader = null;
	
	private static final String DIR_PATH = "/data/lucene/mmapDirectory/product";
	
	@PostConstruct
	public void initIndex() {
		try {
			dir = new MMapDirectory(new File(DIR_PATH));
			config = new IndexWriterConfig(Version.LATEST, analyzer);
			config.setOpenMode(OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(dir, config);
		} catch (IOException e) {
			e.printStackTrace();
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
	 * 暂时未想到办法安全的关闭reader，废弃
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public static synchronized DirectoryReader getReader() throws IOException {
		if(null == reader) {
			reader = DirectoryReader.open(dir);
		}else {

				DirectoryReader newReader = DirectoryReader.openIfChanged(reader);
				
				if(null != newReader) {
					
//					reader.close();
					reader = newReader;							
			
				}

			
		}
		return reader;
	}

}
