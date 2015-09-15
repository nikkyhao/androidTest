package com.xuhao.utility;

public class DateUtil {
   static  String date = "2015-09-09 07:17:03";
    public static void main(String[] args) {
	// TODO Auto-generated method stub
	int minute = getMinute(date);
	int hour = getHour(date);
	int day = getDay(date);
	int month = getMonth(date);
	int year = getYear(date);
	System.out.println("minute:"+minute+
			"hour"+hour+
			"day:"+day+
			"month"+month+
			"year:"+year
		);

    }
    
    public static int getMinute(String date) {
	String minuteString = date.substring(14, 16);
	int minute = Integer.parseInt(minuteString,10);
	return minute;
    }
    
    public static int getHour(String date) {
	String hoursString = date.substring(11,13);
	int hour = Integer.parseInt(hoursString,10);
	return hour;
    }


    public static int getDay(String date) {
	String dayString = date.substring(8,10);
	int day = Integer.parseInt(dayString,10);
	return day;
    }

    public static int getMonth(String date) {// ע��һ����0
	String monthString = date.substring(5,7);
	int month = Integer.parseInt(monthString,10);
	return month-1;
    }

    public static int getYear(String date) {
	String yearString = date.substring(0,4);
	int year = Integer.parseInt(yearString,10);
	return year;
    }
}
