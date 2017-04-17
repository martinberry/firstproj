package org.apache.ibatis.ibator;

import org.apache.ibatis.ibator.api.Ibator;
import org.apache.ibatis.ibator.config.IbatorConfiguration;
import org.apache.ibatis.ibator.config.xml.IbatorConfigurationParser;
import org.apache.ibatis.ibator.exception.XMLParserException;
import org.apache.ibatis.ibator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IbatorRunTest_shenzn {
	public static void main(String... strings) {
		try {
			List<String> warnings = new ArrayList<String>();
			//File configFile = new File("/home/shenzuoning/workspace/code/git/tz/tops-finance/tops-finance-dao/src/test/resources/ibatis/generator/generatorConfig_shenzn.xml");
			File configFile = new File("/home/shenzuoning/workspace/code/git/tz-ztravel/ztr-paygate/ztravel-paygate-core/src/test/resources/ibatis/generator/generatorConfig.xml");
			IbatorConfigurationParser cp = new IbatorConfigurationParser(warnings);
			for (String warning : warnings)
				System.out.println("1---:" + warning);
			IbatorConfiguration config = cp.parseIbatorConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(true);
			Ibator ibator = new Ibator(config, callback, warnings);
			ibator.generate(null);
			for (String warning : warnings)
				System.out.println("2---:" + warning);
		} catch (XMLParserException xex) {
			System.out.println(xex.getErrors().toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
