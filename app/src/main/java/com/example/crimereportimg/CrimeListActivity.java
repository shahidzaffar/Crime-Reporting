package com.example.crimereportimg;



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CrimeListActivity extends SingleFragmentContainer
{

    @Override
    public Fragment createFragment() {
        return new CrimeListFragment();
    }
}
