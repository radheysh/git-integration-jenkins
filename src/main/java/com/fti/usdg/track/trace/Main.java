/**
 * 
 */
package com.fti.usdg.track.trace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bgadm
 *
 */
public class Main {

	public static void main(String[] args) {
		String date1 = "2022-11-11T10:51:25.665Z";
		String date2 = "2022-11-11T10:50:46.016Z";
		try {
			if(convertIsoDateStringToDate(date1).before(convertIsoDateStringToDate(date2))) {
				System.out.println("Yes");
			}else {
				System.out.println("No");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Date convertIsoDateStringToDate(String stringDate) throws ParseException {
		SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = null;
		date = sdfo.parse(stringDate);
		return date;
	}
}
