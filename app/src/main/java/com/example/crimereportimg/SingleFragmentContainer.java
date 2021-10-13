package com.example.crimereportimg;



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentContainer extends AppCompatActivity
{

    public abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm=getSupportFragmentManager();
        Fragment frag=fm.findFragmentById(R.id.fragment_container);

        if(frag==null)
        {
            frag = createFragment();
            fm.beginTransaction().replace(R.id.fragment_container,frag).commit();
        }
    }

}
