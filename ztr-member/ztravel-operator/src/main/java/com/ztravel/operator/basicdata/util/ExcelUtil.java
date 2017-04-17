package com.ztravel.operator.basicdata.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.TZMarkers;
import com.ztravel.operator.basicdata.entity.CountryAreaEntity;



/**
 *
 * @author liuzhuo
 *
 */
public class ExcelUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 解析A，B库excle
	 * @param inp
	 * @param libAList
	 * @param libBList
	 */
	public static void resolveNicknameABExcel(InputStream inp, LinkedList<String> libAList, LinkedList<String> libBList) {
		Workbook workbook = null;

		try {
			workbook = WorkbookFactory.create(inp);
			Sheet sheetA = workbook.getSheetAt(0);
			Sheet sheetB = workbook.getSheetAt(1);


			for(Row row :sheetA) {
				Cell cell = row.getCell(0);
				String cellValue = cell.getStringCellValue();
				if(StringUtils.isNotBlank(cellValue)) {
					libAList.add(cellValue);
				}
//				libAList.add(row.getCell(0).getStringCellValue());
			}

			for(Row row :sheetB) {
				Cell cell = row.getCell(0);
				String cellValue = cell.getStringCellValue();
				if(StringUtils.isNotBlank(cellValue)) {
					libBList.add(cellValue);
				}
//				libBList.add(row.getCell(0).getStringCellValue());
			}

		} catch (InvalidFormatException | IOException e) {
			LOGGER.error(TZMarkers.p2, "AB库解析异常", e);
		}
	}

	/**
	 * 解析国家地区excle
	 */
	public static void resolveCountryExcel(InputStream inp, LinkedList<CountryAreaEntity> countryList) {

		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(inp);
			Sheet sheetA = workbook.getSheetAt(0);

			for(Row row :sheetA) {
				CountryAreaEntity temp = new CountryAreaEntity();
				if(null != row.getCell(0)) {
					Cell cell = row.getCell(0);
					if(cell.getCellType() == 0) {
						temp.setNameCn(row.getCell(1).toString());;
						temp.setNamaEn(row.getCell(2).toString());
						temp.setFullNameEn(row.getCell(3).toString());
						temp.setTwoLetterCode(row.getCell(4).toString());
						temp.setThreeLetterCode(row.getCell(5).toString());
						temp.setNumberCode(row.getCell(6).toString());
					}else {
						continue;
					}
					System.out.println(temp.toString());
					countryList.add(temp);
				}
			}
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析国际目的地信息(国内精选放在同一个excel)
	 * @param inp
	 * @return
	 */
	public static List<JSONObject> resolveDestinationExcel(InputStream inp) {
		List<JSONObject> result = new LinkedList<JSONObject>();
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(inp);
			Sheet sheetA = workbook.getSheetAt(0);

			String currentArea = "";
			String currentCountry = "";

			for(Row row :sheetA) {
				JSONObject temp = new JSONObject();

				if(row.getCell(0) == null || StringUtils.isEmpty(row.getCell(0).toString())) {
					temp.put("area", currentArea);
				}else {
					temp.put("area", row.getCell(0).toString());
					currentArea = row.getCell(0).toString();
				}

				if(row.getCell(1) == null || StringUtils.isEmpty(row.getCell(1).toString())) {
					temp.put("country", currentCountry);
				}else {
					temp.put("country", row.getCell(1).toString());
					currentCountry = row.getCell(1).toString();
				}
				//末尾空行会被遍历,需加判空
				if(row.getCell(2) != null && StringUtils.isNotEmpty(row.getCell(2).toString())){
					temp.put("region", row.getCell(2).toString());
				}else{
					break;
				}
				result.add(temp);
			}
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}


	public static void testResolveExcel(String[] args) {

		try {
			InputStream inp = new FileInputStream(new File("/Users/liuzhuo/Downloads/名称AB库.xlsx"));

			LinkedList<String> libAList = new LinkedList<String>();
			LinkedList<String> libBList = new LinkedList<String>();
			resolveNicknameABExcel(inp, libAList, libBList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void testResolveCountryExcel(String[] args) {

		LinkedList<CountryAreaEntity> countryList = new LinkedList<CountryAreaEntity>();
		try {
			InputStream inp = new FileInputStream(new File("/Users/liuzhuo/Downloads/国家地区列表.xls"));

			resolveCountryExcel(inp, countryList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			InputStream inp = new FileInputStream(new File("/Users/wanhaofan/Downloads/目的地.xlsx"));
			List<JSONObject> result = resolveDestinationExcel(inp);

			for(JSONObject json : result) {
				System.out.println(json);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


}
