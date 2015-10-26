package utl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
}
