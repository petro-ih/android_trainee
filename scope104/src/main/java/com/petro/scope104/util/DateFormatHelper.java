package com.petro.scope104.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateFormatHelper {
    public static String formatDob(Date date){
        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        int yearsCount = (int) (TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - date.getTime())/365);
        return String.format(Locale.getDefault(),"%s, %d years", formatter.format(date), yearsCount);
    }
}
