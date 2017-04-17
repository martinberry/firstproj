package com.ztravel.search.test.autocomp;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKTest {
	
	@Test
    public void testIKAnalyzer() throws Exception {
        String keyWord = "阿 阿富汗";
        IKAnalyzer analyzer = new IKAnalyzer();
        //使用智能分词
        analyzer.setUseSmart(true);
        //打印分词结果
        printAnalysisResult(analyzer,keyWord);
    }


    private void printAnalysisResult(Analyzer analyzer, String keyWord) throws Exception {
        System.out.println("当前使用的分词器：" + analyzer.getClass().getSimpleName());
        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(keyWord));
        tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println(new String(charTermAttribute.buffer()));
        }
    }

}
