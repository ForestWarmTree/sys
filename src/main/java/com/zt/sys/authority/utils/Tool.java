package com.zt.sys.authority.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tool {
	private static SimpleDateFormat longfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat shortfmt = new SimpleDateFormat("yyyy-MM-dd");

	private static SimpleDateFormat timefmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	private static SimpleDateFormat hhmmfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static SimpleDateFormat hhfmt = new SimpleDateFormat("yyyy-MM-dd HH");
	private static SimpleDateFormat shortfmtsimple = new SimpleDateFormat("yyyyMMdd");

	public static String getDateLong(){
		Date tdate = new Date();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tdate);
	}
	public static String getDateLong(Date tdate){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tdate);
	}

	public static String getDate1(){
		Date tdate = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String date = fmt.format(tdate)+" 00:00:00";
		return date;
	}
	public static Date getYesterDay(){
		Calendar clast = Calendar.getInstance();
		clast.roll(Calendar.DAY_OF_MONTH, -1);
		clast.set(Calendar.HOUR_OF_DAY,0);
		clast.set(Calendar.MINUTE, 0);
		clast.set(Calendar.SECOND, 0);
		clast.set(Calendar.MILLISECOND, 0);
		Date tdate = new Date();
		tdate.setTime(clast.getTimeInMillis());
		return tdate;
	}
	public static Date getNextDay(String date_){
		Date cdate = parseDate(date_);
		Calendar clast = Calendar.getInstance();
		clast.setTime(cdate);
		clast.roll(Calendar.DAY_OF_MONTH, 1);
		clast.set(Calendar.HOUR_OF_DAY,0);
		clast.set(Calendar.MINUTE, 0);
		clast.set(Calendar.SECOND, 0);
		clast.set(Calendar.MILLISECOND, 0);
		
		Date tdate = new Date();
		tdate.setTime(clast.getTimeInMillis());
		return tdate;
	}
	public static String getNextDayLong(String date_){
		return getDateLong(getNextDay(date_));
	}
	
	public static String now(){
		Date tdate = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tdate);
		return date;
	}

	public static Date parseDateShortSimple(String date_){
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMdd").parse(date_);
		} catch (Exception e) {
		}
		return date;
	}
	public static Date parseDateShort(String date_){
		Date date = null;
		//("yyyy-MM-dd");
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(date_);
		} catch (Exception e) {
		}
		return date;
	}
	public static Date parseDateLong(String date_){
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return date;
	}
	public static Date parseDateHH(String date_){
		Date date = null;
		//("yyyy-MM-dd HH");
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH").parse(date_);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return date;
	}
	public static Date parseDateHHmm(String date_){
		Date date = null;
		//("yyyy-MM-dd HH:mm");
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date_);
		} catch (Exception e) {
		//	e.printStackTrace();
		}
		return date;
	}
	//日期检查
	public static boolean checkDate(String date_){
		if(date_.indexOf("/")>=0){
			date_= date_.replaceAll("/","-");
		}
		Date date = Tool.parseDateLong(date_);
		if(null!=date)return true;
		date = Tool.parseDateHHmm(date_);
		if(null!=date)return true;
		date = Tool.parseDateHH(date_);
		if(null!=date)return true;
		date = Tool.parseDateShort(date_);
		if(null!=date)return true;
		return false;
	}
	public static Date parseDate(String date_){
		if(date_.indexOf("/")>=0){
			date_= date_.replaceAll("/","-");
		}
		Date date = Tool.parseDateLong(date_);
		if(null!=date)return date;
		date = Tool.parseDateHHmm(date_);
		if(null!=date)return date;
		date = Tool.parseDateHH(date_);
		if(null!=date)return date;
		date = Tool.parseDateShort(date_);
		if(null!=date)return date;
		date = Tool.parseDateShortSimple(date_);
		if(null!=date)return date;
		return null;
	}
	public static String getDateStringLong(String date_){
		if(date_.indexOf("/")>=0){
			date_= date_.replaceAll("/","-");
		}
		Date date = Tool.parseDateLong(date_);
		if(null!=date){
			return Tool.getDateLong(date);
		}
		
		date = Tool.parseDateHHmm(date_);
		//if(null!=date)return date_.trim()+":00";
		if(null!=date){
			return Tool.getDateLong(date);
		}
		date = Tool.parseDateHH(date_);
		//if(null!=date)return date_.trim()+":00:00";
		if(null!=date){
			return Tool.getDateLong(date);
		}
		date = Tool.parseDateShort(date_);
		//if(null!=date)return date_.trim()+" 00:00:00";
		if(null!=date){
			return Tool.getDateLong(date);
		}
		return date_;
	}

	//返回国定长度的字符串：数字+1，前面补0
	public static String getNextNumStr(String num){
     	return getFixLenthNum(num,10);
    }
	public static String getFixLenthNum(String num, int totalLen){
     	String outNum ="";
     	try {
				int inNum = Integer.parseInt(num);
				inNum++;
				outNum =String.valueOf(inNum);
				int len =outNum.length();
				for(int i=0;i<totalLen-len;i++){
					outNum ="0"+outNum;
				}
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
     	return outNum;
     }
	/* 返回指定格式的日期字符串
	 * @param date_ Date
	 * @param String formater
	 * @return 格式化的日期串
	 */
	public static String formateDate(Date date_, String formater) {
		if(date_ != null) {
			SimpleDateFormat fmt = new SimpleDateFormat(formater);
			return fmt.format(date_);
		}
		return null;
	}
	public static String getFixDataStr(String date_, String formater){
		Date date = parseDate(date_);
		return formateDate(date,formater);
	}
	public static String getYestodayStr(){
		return new SimpleDateFormat("yyyy-MM-dd").format(getYesterDay());
	}
	public static String getYestodaySimple(){
		return new SimpleDateFormat("yyyyMMdd").format(getYesterDay());
	}
	public static String getMD5(byte[] content){
		MessageDigest md5 =null;
		String hashString = "";
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(md5!=null) {
			byte[] digest = md5.digest(content);
			hashString = new BigInteger(1, digest).toString(16);// 获取MD5值
		}
		return hashString.toLowerCase();
	}
	public static String getGeojsonPoint(String x, String y) {
		return "{\"type\":\"Point\",\"coordinates\":["+x+","+y+"]}";
	}
	public static double parseDouble(String num) {
		if (num == null || "".equals(num))
			return -1;
		double num_ = 0;
		try {
			num_ = Double.parseDouble(num.trim());
		} catch (NumberFormatException e) {
			num_ = -1;
			e.printStackTrace();
		}
		return num_;
	}

	//是否是数字
	public static boolean isNum(String num) {
		if (num == null || "".equals(num))
			return false;
		double num_ = 0;
		try {
			num_ = Double.parseDouble(num.trim());
		} catch (NumberFormatException e) {
			return  false;
		}
		return true;
	}


	public static int parseInt(String num) {
		if (num == null || "".equals(num.trim()))
			return -1;
		int num_ = 0;
		try {
			num_ = Integer.parseInt(num);
		} catch (NumberFormatException e) {
			num_ = -1;
			e.printStackTrace();
		}
		return num_;
	}

	public static float parseFloat(String num) {
		if (num == null || "".equals(num))
			return -1;
		float num_ = 0;
		try {
			num_ = Float.parseFloat(num.trim());
		} catch (NumberFormatException e) {
			num_ = -1;
			e.printStackTrace();
		}
		return num_;
	}

	public static String getTime() {
		Date tdate = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return fmt.format(tdate);
	}


	public static String sysTime() {
		Date tdate = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(tdate);
	}

	public static String getDate() {
		Date tdate = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(tdate);
	}

	public static String getYearMonth() {
		Date tdate = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
		return fmt.format(tdate);
	}
	public static String getYearMonthNext_() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, 1);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM");
		return fmt.format(c.getTime());
	}
	public static String getYearMonthNext_(String yearMonth) {
		String year  = yearMonth.substring(0,4);
		String month = yearMonth.substring(5);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR,parseInt(year));
		c.set(Calendar.MONTH,parseInt(month));
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, 1);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM");
		return fmt.format(c.getTime());
	}
	public static String getYearMonth_before(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		Date m = c.getTime();
		String mon = format.format(m);
		return mon;
	}
	public static String getYearMonth_before_str(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		Date m = c.getTime();
		String mon = format.format(m);
		return mon;
	}
	public static String getYearMonth_() {
		Date tdate = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM");
		return fmt.format(tdate);
	}


	public static String getYear() {
		return getDate().substring(0, 4);
	}

	public static String getMonth() {
		String m = getDate().substring(4, 6);
		if (m.indexOf("0") == 0) {
			return getDate().substring(5, 6);
		}
		return getDate().substring(4, 6);
	}
	/*
	 * 返回上个月，如果是一月份，则直接返回01
	 */
	public static String getLastMonth() {
		Calendar caldr = Calendar.getInstance();
		int mth = caldr.get(Calendar.MONTH);
		if(mth==0)mth=1;
		if(mth<10)return "0"+mth;
		else
			return String.valueOf(mth);
	}

	public static String getMonthSimple() {
		Calendar caldr = Calendar.getInstance();
		return String.valueOf((caldr.get(Calendar.MONTH) + 1));
	}
	public static int getMonthNum() {
		Calendar caldr = Calendar.getInstance();
		return caldr.get(Calendar.MONTH) + 1;
	}
	// 一个日期上加天数
	public static String ateAdd(String startDate, int dd) {
		java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		Date date = new Date();
		try {
			date = df.parse(startDate);
		} catch (Exception ex) {
			System.out.print(ex);
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.DATE, dd);

		String mm = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (mm.length() == 1) {
			mm = "0" + mm;
		}
		String day = String.valueOf(calendar.get(Calendar.DATE));
		if (day.length() == 1) {
			day = "0" + day;

		}
		String returnDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-"
				+ mm + "-" + day;

		return returnDate;

	}
	//今天
	public static String today() {
		Date tdate = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		return fmt.format(tdate);
	}
	// 昨天
	public static String lastDay() {
		java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR,-1);
		//System.out.println(df.format(calendar.getTime()));

		String mm = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (mm.length() == 1) {
			mm = "0" + mm;
		}
		String day = String.valueOf(calendar.get(Calendar.DATE));
		if (day.length() == 1) {
			day = "0" + day;

		}
		String returnDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-"
				+ mm + "-" + day;

		return returnDate;

	}
	// 昨天
	public static String beforeYesterDay() {
		java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR,-2);
		//System.out.println(df.format(calendar.getTime()));

		String mm = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (mm.length() == 1) {
			mm = "0" + mm;
		}
		String day = String.valueOf(calendar.get(Calendar.DATE));
		if (day.length() == 1) {
			day = "0" + day;

		}
		String returnDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-"
				+ mm + "-" + day;
		return returnDate;
	}
	// 比较日期是否大于今天
	public static boolean compareDate(java.sql.Date date) {
		if(null==date)return false;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//System.out.println(calendar.getTime());
		return calendar.compareTo(Calendar.getInstance())>=0;

	}
	// 比较日期是否大于今天
	public static boolean isInLast3Day() {
		Calendar clast = Calendar.getInstance();
		//最后一天
		clast.set(Calendar.DAY_OF_MONTH,clast.getActualMaximum(Calendar.DAY_OF_MONTH));
		clast.set(Calendar.HOUR_OF_DAY,23);
		clast.set(Calendar.MINUTE, 59);
		clast.set(Calendar.SECOND, 59);
		//SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println(fmt.format(clast.getTime()));
		//现在
		Calendar now = Calendar.getInstance();
		//System.out.println(fmt.format(now.getTime()));
		//2天前
		Calendar day2ago = Calendar.getInstance();
		day2ago.set(Calendar.DAY_OF_MONTH,now.getActualMaximum(Calendar.DAY_OF_MONTH)-2);

		day2ago.set(Calendar.HOUR_OF_DAY,0);
		day2ago.set(Calendar.MINUTE, 0);
		day2ago.set(Calendar.SECOND, 0);
		//System.out.println(fmt.format(day2ago.getTime()));
		if(now.compareTo(clast)<=0&&now.compareTo(day2ago)>=0)
			return true;
		else return false;

	}


	public static double subtract(double num1,double num2) {
		BigDecimal b1 = new BigDecimal(Double.toString(num1));
		BigDecimal b2 = new BigDecimal(Double.toString(num2));
		return b1.subtract(b2).doubleValue();
	}
	public static double subtract2(double num1,double num2,double num3) {
		double result = subtract(num1,num2);
		return subtract(result,num3);
	}
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	//上个月的最后一天
	public static String lastMonthEndDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		//最后一天
		c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat("yyyyMMdd").format(c.getTime());
	}
	//指定年月的最后一天2018-03
	public static String getOneMonthEndDay(String yearMonth) {
		String year  = yearMonth.substring(0,4);
		String month = yearMonth.substring(5);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR,parseInt(year));
		c.set(Calendar.MONTH,parseInt(month));
		//最后一天
		c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat("yyyyMMdd").format(c.getTime());
	}

	public static void main(String[] args) {

		//System.out.println(Tool.getYearMonth_before());
		Tool.lastMonthEndDay();
		
//		for (int i = 0; i < 10; i++) {
//			//System.out.println(getTime());
//			System.out.println(sysTime());
//		}
	}
}
