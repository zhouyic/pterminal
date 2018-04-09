package communicate.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class TimeMgmt {

	public boolean checkTimePattern(String dateStr) {
		int year = -1;
		int mon = -1;
		int day = -1;
		int hour = -1;
		int min = -1;
		int sec = -1;

		boolean isDateRight = false;

		int[][] days1 = { { 0, 0 }, { 1, 31 }, { 2, 28 }, { 3, 31 }, { 4, 30 },
				{ 5, 31 }, { 6, 30 }, { 7, 31 }, { 8, 31 }, { 9, 30 },
				{ 10, 31 }, { 11, 30 }, { 12, 31 } };
		int[][] days2 = { { 0, 0 }, { 1, 31 }, { 2, 29 }, { 3, 31 }, { 4, 30 },
				{ 5, 31 }, { 6, 30 }, { 7, 31 }, { 8, 31 }, { 9, 30 },
				{ 10, 31 }, { 11, 30 }, { 12, 31 } };

		if (14 == dateStr.length()) {
			try {
				year = java.lang.Integer.parseInt(dateStr.substring(0, 4));
				mon = java.lang.Integer.parseInt(dateStr.substring(4, 6));
				day = java.lang.Integer.parseInt(dateStr.substring(6, 8));
				hour = java.lang.Integer.parseInt(dateStr.substring(8, 10));
				min = java.lang.Integer.parseInt(dateStr.substring(10, 12));
				sec = java.lang.Integer.parseInt(dateStr.substring(12, 14));

			} catch (NumberFormatException nfe) {
				System.out.println(nfe.toString());
			}
		}

		else if (8 == dateStr.length()) {
			try {
				year = java.lang.Integer.parseInt(dateStr.substring(0, 4));
				mon = java.lang.Integer.parseInt(dateStr.substring(4, 6));
				day = java.lang.Integer.parseInt(dateStr.substring(6, 8));
			} catch (NumberFormatException nfe) {
				System.out.println(nfe.toString());
			}
		}

		boolean isLeapYear = false;

		if ((year % 4 == 0 && (year % 100 != 0)) || (year % 400 == 0))
			isLeapYear = true;

		if ((mon >= 1) && (mon <= 12)) {
			if (isLeapYear) {
				if (14 == dateStr.length()) {
					if ((year >= 1000) && (year <= 9999) && (day >= 1)
							&& (day <= days2[mon][1]) && (hour >= 0)
							&& (hour <= 23) && (min >= 0) && (min <= 59)
							&& (sec >= 0) && (sec <= 59))
						isDateRight = true;
				} else if (8 == dateStr.length()) {
					if ((year >= 1000) && (year <= 9999) && (mon >= 1)
							&& (mon <= 12) && (day >= 1)
							&& (day <= days2[mon][1]))
						isDateRight = true;
				}
			} else {
				if (14 == dateStr.length()) {
					if ((year >= 1000) && (year <= 9999) && (mon >= 1)
							&& (mon <= 12) && (day >= 1)
							&& (day <= days1[mon][1]) && (hour >= 0)
							&& (hour <= 23) && (min >= 0) && (min <= 59)
							&& (sec >= 0) && (sec <= 59))
						isDateRight = true;
				} else if (8 == dateStr.length()) {
					if ((year >= 1000) && (year <= 9999) && (mon >= 1)
							&& (mon <= 12) && (day >= 1)
							&& (day <= days1[mon][1]))
						isDateRight = true;
				}
			}
		}
		return isDateRight;
	}

	/**
	 * @name checkDateVal
	 * @param dateStr
	 * @param start
	 * @param end
	 * @return isDateRight
	 * @throws ParseException
	 *             if the params don't accord with the
	 *             SimpleDateFormat("yyyyMMddHHmmss")
	 * @function
	 */
	public boolean checkDateVal(String dateStr, String start, String end) {
		boolean isDateRight = false;
		Date date = null;
		Date startDate = null;
		Date endDate = null;
		SimpleDateFormat sdf = null;
		if (14 == dateStr.length()) {
			sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		} else if (8 == dateStr.length()) {
			sdf = new SimpleDateFormat("yyyyMMdd");
		} else
			return false;

		try {
			date = sdf.parse(dateStr);
		} catch (ParseException ex) {
			System.out.println(ex.toString());
		}

		if ((start == null) && (end != null)) {
			try {
				endDate = sdf.parse(end);
			} catch (ParseException ex1) {
				System.out.println(ex1.toString());
			}
			if ((date != null) && (endDate != null)){
				if (date.compareTo(endDate) <= 0)
					isDateRight = true;
			}
		}

		else if ((start != null) && (end == null)) {
			try {
				startDate = sdf.parse(start);
			} catch (ParseException ex1) {
				System.out.println(ex1.toString());
			}
			if ((date != null) && (startDate != null)){
				if (date.compareTo(startDate) >= 0)
					isDateRight = true;
			}
		} else if ((start != null) && (end != null)) {
			try {
				endDate = sdf.parse(end);
				startDate = sdf.parse(start);
			} catch (ParseException ex2) {
				System.out.println(ex2.toString());
			}
			if ((startDate != null) && (date != null) && (endDate != null)){
				if ((date.compareTo(startDate) >= 0)
						&& (date.compareTo(endDate) <= 0))
					isDateRight = true;
			}
		}
		return isDateRight;
	}

	public boolean checkDateV(String dateStr, String start, String end) {
		boolean isDateRight = false;
		long date = -1;
		long fromDate = -1;
		long toDate = -1;

		date = java.lang.Long.parseLong(dateStr);

		if ((start == null) && (end == null))
			isDateRight = true;

		else if ((start == null) && (end != null)) {

			try {
				toDate = java.lang.Long.parseLong(end);
			} catch (NumberFormatException nfe) {
				System.out.println(nfe.toString());
			}

			if (date <= toDate) {
				isDateRight = true;
			}
		}

		else if ((start != null) && (end == null)) {
			try {
				fromDate = java.lang.Long.parseLong(start);
			} catch (NumberFormatException nfe1) {
				System.out.println(nfe1.toString());
			}

			if (date >= fromDate) {
				isDateRight = true;
			}
		}

		else if ((start != null) && (end != null)) {
			try {
				toDate = java.lang.Long.parseLong(end);
				fromDate = java.lang.Long.parseLong(start);
			} catch (NumberFormatException nfe2) {
				System.out.println(nfe2.toString());
			}

			if ((date <= toDate) && (date >= fromDate)) {
				isDateRight = true;
			}
		}
		return isDateRight;
	}

	/***************************************************************************
	 * @name getCurDate
	 * @param mode
	 *            1--in yyyy-MM-dd pattern; 2--in yyyyMMdd pattern!
	 * @return dateStr 1--in yyyy-MM-dd pattern; 2--in yyyyMMdd pattern!
	 * @funtion get system current time in yyyy-MM-dd pattern or yyyyMMdd
	 *          pattern according to param value.1--in yyyy-MM-dd pattern; 2--in
	 *          yyyyMMdd pattern!
	 */
	public String getCurDate(int mode) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		Date date = new Date();

		switch (mode) {
		case 1:
			sdf.applyPattern("yyyy-MM-dd");
			break;
		case 2:
			sdf.applyPattern("yyyyMMdd");
			break;
		case 3:
			sdf.applyPattern("yyyy/MM/dd");
			break;
		case 4:
			sdf.applyPattern("yyyy-MM-dd HH-mm-ss");
			break;
		case 5:
			sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
			break;
		case 6:
			sdf.applyPattern("yyyyMMdd:HHmmss");
			break;
		case 7:
			sdf.applyPattern("yyyyMM");
			break;
		case 8:
			sdf.applyPattern("yyyyMMddHHmmss");
			break;
		case 9:
			sdf.applyPattern("yyyy-MM-dd HH:mm");
			break;

		default:
			break;
		}
		return sdf.format(date);
	}

	/**
	 * @name changeTimePattern1
	 * @param oldTime
	 * @return new pattern time
	 * @funtion turn "yyyy-MM-dd-hh-mi-ss" date pattern to "yyyyMMddhh24miss"
	 *          pattern.
	 */
	public static String changeTimePattern1(String oldTime) {
		StringTokenizer st = new StringTokenizer(oldTime, "-");
		if (oldTime.length() == 19)
			return st.nextToken() + st.nextToken() + st.nextToken();
		else
			return st.nextToken() + st.nextToken() + st.nextToken() + "000000";
	}

}
