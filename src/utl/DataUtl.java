package utl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;

public class DataUtl {
	public DataUtl() {
		
	}
	
	public static String txtData(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(calendar.getTime());
	}
	
	public static Calendar setCurrentData() {
		Calendar c = new GregorianCalendar();
		return c;
	}

	public static Calendar setData(String data) {
		int day = Integer.parseInt(data.split("/")[0]);
		int mounth = Integer.parseInt(data.split("/")[1])-1;
		int year = Integer.parseInt(data.split("/")[2]);
		Calendar c = new GregorianCalendar(year,mounth,day);
        return c;
        
	}

	public static Date setDataSQL(String data) {
		java.sql.Date sqlDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			java.util.Date date = sdf.parse(data);
			sqlDate = new Date(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
			return sqlDate;
	}

	public static Calendar getStringData(java.sql.Date sqlDate) {
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			calendar.setTime(sdf.parse(sqlDate.toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}
}
