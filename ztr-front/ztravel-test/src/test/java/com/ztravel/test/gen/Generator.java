package com.ztravel.test.gen;

import java.sql.SQLException;
import java.util.*;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;

public class Generator {

	public static void main(String[] args) {

		Configuration config = null;
		boolean overwrite = true;
		List<String> warnings = new ArrayList<String>();
		String filePath = "/Users/liuzhuo/Work/tz-ztravel/ztr-front/ztravel-test/src/test/resources/mybatis/generator/generatorConfig.xml";
//		String filePath = "/home/zhaopengfei/git/tz/tops-fit/tops-fit-order/src/main/resources/mybatis/generator/generatorConfig.xml";
		try {
			ConfigurationParser cp = new ConfigurationParser(warnings);

			for(String warning : warnings) {
				System.out.println("start*******" + warning);
			}

			File inputFile = new File(filePath);
			config = cp.parseConfiguration(inputFile);
		    DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator generator = new MyBatisGenerator(config, callback, warnings);
			generator.generate(null);

			for(String warning : warnings) {
				System.out.println("end*******" + warning);
			}
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLParserException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
