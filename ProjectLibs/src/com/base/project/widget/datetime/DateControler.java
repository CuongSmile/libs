/*
 * COMMENTS Copyright (C) 2013 TinhVan Outsourcing.
 */
package com.base.project.widget.datetime;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

/**
 * @author TND Nov 26, 2013
 */
public class DateControler {


    private DialogType mType = DialogType.NONE;

    private Context mContext;
    private int mYear;
    private int mMonth;
    private int mDay;

    private String date;

    public DateControler(Context pContext) {
        mContext = pContext;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * specify Date type show on dialog. {PAST, FUTURE, NONE}.<br/>
     * Default NONE
     * 
     * @param pDialogType
     */
    public void setDateType(DialogType pDialogType) {
        this.mType = pDialogType;
    }


    private DatePickerDialog getInstanceDatePickerDialog() {
        long cur = Calendar.getInstance().getTime().getTime();
        DatePickerDialog dialog = new DatePickerDialog(mContext, mDateSetListener, mYear, mMonth, mDay);
        switch (mType) {
            case PAST:
                dialog.getDatePicker().setMaxDate(cur);
                break;
            case FUTURE:
                dialog.getDatePicker().setMinDate(cur);
                break;
            default:
                break;
        }
        return dialog;
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDate();
            Log.i("datepicker", "set date:  " + date);
            if (mDateSetedListener != null) {
                mDateSetedListener.onDateSeted(mDay, mMonth, mYear);
            }
        }
    };

    private void updateDate() {
        date = getDate();
    }

    private String getDate() {
        return new StringBuilder().append(mDay).append("/").append(mMonth + 1).append("/").append(mYear)
                .append(" ").toString();
    }

    public void show() {
        getInstanceDatePickerDialog().show();
    }

    private IDateSetedListener mDateSetedListener;

    public void registerDateSetedListener(IDateSetedListener mDateSetedListener) {
        this.mDateSetedListener = mDateSetedListener;
    }

    public interface IDateSetedListener {
        void onDateSeted(int day, int month, int year);
    }

    public enum DialogType {
        PAST, FUTURE, NONE;
    }
}
