package com.example.crimereportimg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Crime> listCrime;
    public static final String nameID="com.example.crimereportimg.Crime_ID";


    public static Intent getIntent(Context context, UUID mid)
    {
        Intent intent=new Intent(context,CrimePagerActivity.class);
        intent.putExtra(nameID,mid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        viewPager=(ViewPager) findViewById(R.id.view_pager_crime);
        listCrime=CrimeLab.getsCrimeLabOBJ(this).getmCrimes();

        FragmentManager fm=getSupportFragmentManager();
        UUID mCrimeId=(UUID) getIntent().getSerializableExtra(nameID);
        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return CrimeFragment.newInstance(listCrime.get(position).getMid());
            }

            @Override
            public int getCount() {
                return listCrime.size();
            }
        });

        for (int i=0;i<listCrime.size();i++)
        {
            if(listCrime.get(i).getMid().equals(mCrimeId))
            {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }
}