package com.example.crimereportimg;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.EditText;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment
{
    public static final String Crime_Args_Key="crime_id";
    private static final String DIALOG_DATE = "dialog_date";
    private static final int REQ_CODE=1;
    private Button mDateButton;
    private CheckBox isSolved;
    private EditText mTitle;

    Crime mCrime;

    public static Fragment newInstance(UUID mid)
    {
        Bundle args=new Bundle();
        args.putSerializable(Crime_Args_Key,mid);
        Fragment freag=new CrimeFragment();
        freag.setArguments(args);
        return freag;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mCrime=new Crime();

       // UUID mcId=(UUID)getActivity().getIntent().getSerializableExtra(MainActivity.nameID);
        UUID mcId=(UUID)getArguments().getSerializable(Crime_Args_Key);
        mCrime=CrimeLab.getsCrimeLabOBJ(getActivity()).getSingleCrime(mcId);
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.getsCrimeLabOBJ(getActivity()).updateDB(mCrime);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v=inflater.inflate(R.layout.fragment_crime,container,false);
        mDateButton=(Button) v.findViewById(R.id.dateBtn);
        mDateButton.setText(mCrime.getCrimDate().toString());
        //mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentManager fm=getFragmentManager();
                DatePickerFragment dp=DatePickerFragment.newInstance(mCrime.getCrimDate());
                dp.setTargetFragment(CrimeFragment.this,REQ_CODE);
                dp.show(fm,DIALOG_DATE);

            }
        });

        isSolved=(CheckBox)v.findViewById(R.id.crime_solved);
        isSolved.setChecked(mCrime.isStatus());
        isSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mCrime.setStatus(isChecked);
            }
        });
        mTitle=(EditText)v.findViewById(R.id.TitleTxt);
        mTitle.setText(mCrime.getTitleCrime());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitleCrime(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode!= Activity.RESULT_OK)
            return;
        if(requestCode==REQ_CODE)
        {
            Date date=(Date) data.getSerializableExtra(DatePickerFragment.DATE_EXTRA);
            mCrime.setCrimDate(date);
            mDateButton.setText(mCrime.getCrimDate().toString());

        }
    }
}
