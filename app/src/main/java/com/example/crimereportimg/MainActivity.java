package com.example.crimereportimg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class MainActivity extends SingleFragmentContainer {

    public static final String nameID="com.example.crimereportimg.Crime_ID";
    public static Intent getIntent(Context context, UUID mid)
    {
        Intent intent=new Intent(context,MainActivity.class);
        intent.putExtra(nameID,mid);
        return intent;
    }

    @Override
    public Fragment createFragment()
    {
      UUID mid=(UUID) getIntent().getSerializableExtra(nameID);
      return (CrimeFragment.newInstance(mid));
    }
}