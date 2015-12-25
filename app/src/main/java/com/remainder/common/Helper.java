package com.remainder.common;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Helper {

    public boolean isValidEmail(String email) {
//        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isNotEmptyNullText(String text)
    {
        if (TextUtils.isEmpty(text.trim()) && text != null)
            return false;

            return  true;
    }

    public static boolean isValidDate(String date)
    {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        df.setLenient(false);
        Date testDate;
        try {
            testDate = df.parse(date);
            if (!df.format(testDate).equals(date))
            {
                return false;
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isValidPhoneNumber(String phoneNo)
    {
        if(phoneNo.length() < 10 || phoneNo == null)
        {
          return false;
        }
        return Patterns.PHONE.matcher(phoneNo).matches();
    }
}
