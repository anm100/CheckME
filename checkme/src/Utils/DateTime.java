package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
/**
 * Static utils class for Class java.util.Date
 * @author Muhamad Igbaria and Bulous Abu Jaber
 *
 */
public class DateTime {
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	public static Calendar calendar = Calendar.getInstance();

	/**
	 * 
	 * @param h : Hour
	 * @param m : Minuts
	 * @return : Date object with year +1900 , month +1, and specific hour and minutes
	 */
	public static Date getTime(int h, int m) {
		Date d = new Date();
		try {
			return formatter.parse(
					String.format("%d-%d-%d-%d:%d:%d", d.getYear() + 1900, d.getMonth() + 1, d.getDate(), h, m, 0));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 
	 * @param d : Date
	 * @param period : number of days to add to Date
	 * @return Date object with day period
	 */
	public static Date addDay(Date d, int period) {
		Date date = new Date();
		date.setTime(d.getTime() + ((period) * 24 * 60 * 60 * 1000));
		return date;
	}

	/**
	 * 
	 * @param d : Date
	 * @return : Date Object with Month +1
	 */
	public static Date addMonth(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, 1);
		return c.getTime();
	}

	
	/**
	 * 
	 * @param d Date Object
	 * @return Date object with with month -1
	 */
	public static Date MinusMonth(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, -1);
		return c.getTime();
	}

	
	/**
	 * 
	 * @param d1 : Date Object
	 * @param d2 : Date Object
	 * @return	d1-d2
	 */
	public static int MinusTwoMonths(Date d1, Date d2) {
		int n = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(d1);
		while (c.getTime().getMonth() != d2.getMonth()) {
			c.add(Calendar.MONTH, 1);
			n++;
		}
		return n;

	}

	
	/**
	 * 
	 * @param year : Year
	 * @return array list of 12 Dates for every month in the year at day =1 of specific year
	 */
	public static List<Date> getMonths(int year) {
		Date d = DateTime.getDate(year, 1, 1);

		ArrayList<Date> dates = new ArrayList<Date>();
		for (int i = 0; i < 12; i++) {
			dates.add(d);
			d = DateTime.addMonth(d);
		}

		return dates;
	}

	
	/**
	 * 
	 * @param d Date Object
	 * @param hour : Hours to add to Date
	 * @return Date Object with Date hours + hour
	 */
	public static Date addHour(Date d, int hour) {
		Date date = new Date();
		date.setTime(d.getTime() + hour * 60 * 60 * 1000);
		return date;
	}

	/**
	 * 
	 * @param d Date Object
	 * @param min : minutes to add to the Date
	 * @return Date Object with Date minutes + min 
	 */
	public static Date addMinutes(Date d, int min) {
		Date date = new Date();
		date.setTime(d.getTime() + min * 60 * 1000);
		return date;
	}

	/**
	 * 
	 * @param d Date Object
	 * @return day of the week 1-7
	 */
	public static int getDayOfWeek(Date d) {
		calendar.setTime(d);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 
	 * @param d Date Object
	 * @return Day of the week as String like sunday,monday....
	 */
	public static String getDayOfWeekString(Date d) {
		int day = getDayOfWeek(d);
		switch (day) {
		case 1:
			return "Sunday";
		case 2:
			return "Monday";
		case 3:
			return "Tuesday";
		case 4:
			return "Wednesday";
		case 5:
			return "Thursday";
		case 6:
			return "Friday";
		case 7:
			return "Saturday";
		}
		return null;
	}

		
	/**
	 * 
	 * @param d Date Object
	 * @return number of week in the year
	 */
	public static int getWeekOfYear(Date d) {
		calendar.setTime(d);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 
	 * @param d Date Object
	 * @return Month in the year 1-12
	 */

	public static int getMonthOfYear(Date d) {
		calendar.setTime(d);
		return calendar.get(Calendar.MONTH + 1);
	}

	/**
	 * 
	 * @param y Year
	 * @param m Month
	 * @param d Date- Day in month
	 * @return Date yy/mm/dd with time 00:00:00
	 */
	public static Date getDate(int y, int m, int d) {
		try {
			return formatter.parse(String.format("%d-%d-%d-%d:%d:%d", y, m, d, 0, 0, 0));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 
	 * @param s : Date in String
	 * @return Date Object of the string Date
	 * @throws ParseException
	 */
	public static Date getDate(String s) throws ParseException {

		return dateFormat.parse(s);
	}

	
	/**
	 * 
	 * @param s : String Date
	 * @return Date object from the string s in format dd/MM/yyyy
	 * @throws ParseException
	 */
	public static Date getReportDate(String s) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.parse(s);
	}

	
	/**
	 * 
	 * @param y Year
	 * @param m Month
	 * @param d Date
	 * @param h Hour
	 * @param min Minutes
	 * @return Date Object of specific values
	 * @throws ParseException
	 */
	public static Date getDate(int y, int m, int d, int h, int min) throws ParseException {
		return formatter.parse(String.format("%d-%d-%d-%d:%d:%d", y, m, d, h, min, 0));
	}

	/**
	 * 
	 * @return current Date with time 00:00:00 
	 * @throws ParseException
	 */
	public static Date currentDay() throws ParseException {
		Date curr = new Date();
		return getDate(curr.getYear() + 1900, curr.getMonth() + 1, curr.getDate(), 0, 0);
	}

	/**
	 * 
	 * @return Current Date with the current date and time
	 * @throws ParseException
	 */
	public static Date currentDate() throws ParseException {
		Date curr = new Date();
		return getDate(curr.getYear() + 1900, curr.getMonth() + 1, curr.getDate(), curr.getHours(), curr.getMinutes());
	}

	
	/**
	 * 
	 * @return date time with: 1/current month/current year
	 * @throws ParseException
	 */
	public static Date currentMont() throws ParseException {
		Date curr = new Date();
		return getDate(curr.getYear() + 1900, curr.getMonth() + 1, 1);
	}

	/**
	 * random generator
	 */
	public static Random rand = new Random();

	/**
	 * 
	 * @return Random Date from the year of 1990, time: 00:00:00
	 */
	public static Date randomDate() {

		return DateTime.getDate(1990 + rand.nextInt(20), rand.nextInt(11), rand.nextInt(29));

	}

	
	/**
	 * 
	 * @param d Date Object
	 * @return The Date Time object as String like HH:mm
	 */
	public static String getTimeString(Date d) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(d);
	}

	/**
	 * 
	 * @param d Date object
	 * @return The Date Object as String without time like: dd/MM/yyyy
	 */
	public static String getDateString(Date d) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(d);
	}

	
	/**
	 * 
	 * @param date Calnedar Date
	 * @return date as java.util.Date Object
	 */
	public static Date calendarToDate(Calendar date) {

		try {
			return DateTime.getDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1,
					date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param date Date Object
	 * @param valueToAdd hours to add to the date
	 * @return the date with hours+valueToAdd
	 */

	public static Date addHoursToTime(Date date, int valueToAdd) {
		Calendar cdate = Calendar.getInstance();
		cdate.setTime(date);
		cdate.add(Calendar.MINUTE, valueToAdd);
		return calendarToDate(cdate);
	}
	
	/**
	 * 
	 * @param d1_start : Date 1 start
	 * @param d1_end : Date1 end
	 * @param d2_start Date2 start
	 * @param d2_end Date2 end
	 * @return true if the Date1 and Date2 Overlaps , false else ,Overlap if (StartA small than EndB) and (EndA bigger than StartB)
	 */

	public static boolean isOverlap(Date d1_start, Date d1_end, Date d2_start, Date d2_end) {
		// Overlap <==> (StartA < EndB) and (EndA > StartB)
		if ((d1_start.before(d2_end)) && (d1_end.after(d2_start)))
			return true;
		return false;
	}

	/**
	 * 
	 * @return tomorrow Date
	 */
	public static Date getTomorrowDate() {
		try {
			return addDay(currentDate(), 1);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

}
