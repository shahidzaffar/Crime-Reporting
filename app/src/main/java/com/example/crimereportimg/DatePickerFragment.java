package com.example.crimereportimg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

public class DatePickerFragment extends DialogFragment
{
    public static final String args_date="date";
    public static final String DATE_EXTRA ="com.example.crimereportimg.date" ;
    private DatePicker mDatePicker;
    public static DatePickerFragment newInstance(Date date)
    {

        Bundle args=new Bundle();
        args.putSerializable(args_date,date);
        DatePickerFragment dpf=new DatePickerFragment();
        dpf.setArguments(args);
        return dpf;


    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
       // return super.onCreateDialog(savedInstanceState);

        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dailog_date,null);
        Date dat=(Date) getArguments().getSerializable(args_date);
        int day, year, month;
        Calendar cal=Calendar.getInstance();
        cal.setTime(dat);
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=cal.get(Calendar.MONTH);
        year=cal.get(Calendar.YEAR);


        mDatePicker=(DatePicker) view.findViewById(R.id.date_picker_layout);
        mDatePicker.init(year,month,day,null);


         return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        int day=mDatePicker.getDayOfMonth();
                        int month=mDatePicker.getMonth();
                        int year=mDatePicker.getYear();

                        Date date=(Date) new GregorianCalendar(year,month,day).getTime();



                        sendRsult(Activity.RESULT_OK,date);
                    }
                })
                .create();
    }

    private void sendRsult(int resultOk, Date date)
    {
        Intent intent=new Intent();
        intent.putExtra(DATE_EXTRA,date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk,intent);
    }


}
