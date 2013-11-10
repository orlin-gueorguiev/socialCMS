package com.github.orlin.socialCMS.rest.jaxb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapters {
	/**
	 * Style = dd.MM.yy<br />
	 * for example 15.12.2013
	 * @author orlin
	 *
	 */
	public static class ShortDateAdapter extends XmlAdapter<String, Calendar> {

		private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

	    @Override
	    public String marshal(Calendar v) throws Exception {
	        return dateFormat.format(v.getTime());
	    }

	    @Override
	    public Calendar unmarshal(String v) throws Exception {
	        Date date = dateFormat.parse(v);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar;
	    }
		
	}
	
	/**
	 * Style = HH:mm, dd.MM.yy<br />
	 * for example 10:30, 15.12.2013
	 * @author orlin
	 *
	 */
	public static class TimeAndShortDateAdapter extends XmlAdapter<String, Calendar> {

//		private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, dd.MM.yy");

	    @Override
	    public String marshal(Calendar v) throws Exception {
	        return dateFormat.format(v.getTime());
	    }

	    @Override
	    public Calendar unmarshal(String v) throws Exception {
	        Date date = dateFormat.parse(v);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar;
	    }
		
	}	
}
