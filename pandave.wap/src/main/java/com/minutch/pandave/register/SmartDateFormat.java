package com.minutch.pandave.register;


import com.minutch.pandave.utils.DateUtils;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Minutch on 15/6/13.
 */
public class SmartDateFormat extends SimpleDateFormat {

	private static final long serialVersionUID = -7511895186890117827L;

	@Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
        return new StringBuffer(DateUtils.smartFormat(date));
    }

    @Override
    public Date parse(String text) throws ParseException {
        return DateUtils.smartFormat(text);
    }
}
