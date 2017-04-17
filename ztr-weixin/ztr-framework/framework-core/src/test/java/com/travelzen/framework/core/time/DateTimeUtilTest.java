package com.travelzen.framework.core.time;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class DateTimeUtilTest {

	@Test
	public void test_parseDate8() {
		System.out.println(DateTimeUtil.parseDate8("20121201").toDate());
		System.out.println(DateTimeUtil.addDay(DateTimeUtil.parseDate8("20121201"), 1).toDate());
	}
	@Test
	public void test_getBeginDateTime() {
		System.out.println(DateTimeUtil.getBeginDateTime(DateTimeUtil.parseDate8("20121201").toDate()));
		System.out.println(DateTimeUtil.getBeginDateTime(new Date()));
		System.out.println(new Date());
	}
	@Test
	public void diffInDay() {
		Date a = DateTimeUtil.parseDate8("20121201").toDate();
		Date b = DateTimeUtil.parseDate8("20130101").toDate();
		org.junit.Assert.assertEquals(31, DateTimeUtil.diffInDay(a, b));
	}

	@Test
	public void dayOfWeek(){
		assertEquals(4, DateTimeUtil.parseDate8("20150226").getDayOfWeek());
	}
	@Test
	public void parse(){
		DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMMyyyy");
		DateTime dateTime = formatter.parseDateTime("27FEB2015");
		assertEquals("20150227", DateTimeUtil.date8(dateTime));
		dateTime = formatter.parseDateTime("06APR2015");
		assertEquals("20150406", DateTimeUtil.date8(dateTime));
	}
}
