package com.abutirr.project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateAndTime {

    public void setDateDialog(DatePickerDialog.OnDateSetListener date, Context context){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2"));
        int year =cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH);
        int day =cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context
                ,android.R.style.Theme_Holo_Light_Dialog_MinWidth, date,year-20,month,day);
        // the maxmimum date is now
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }

    public void setDateDialogFuture(DatePickerDialog.OnDateSetListener date, Context context){

        // THERE IS A PROBLEM WITH THIS CODE , FIX IT
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2"));

        int year =cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH);
        int day =cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context
                ,android.R.style.Theme_Holo_Light_Dialog_MinWidth, date,year,month,day);

        // THE CODE WILL WORK AFTER NEW YEAR
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-3000);
        // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }

    public void setTimeDialog(TimePickerDialog.OnTimeSetListener time, Context context){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2"));
        int hours=cal.get(Calendar.HOUR);
        int minutes=cal.get(Calendar.MINUTE);


        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,time,hours,minutes,true);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();
    }
}
