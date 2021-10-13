package com.example.crimereportimg;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdaptor crimeAdaptor;
    private boolean mItemVisible;
    public static final String SUBTITLE_VISIBLE="subtitle_visible";


    private class CrimeAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Crime> mCrime;

        public void setmCrime(List<Crime> mCrime)
        {
            this.mCrime = mCrime;
        }

        public CrimeAdaptor(List<Crime> mCrime) {
            this.mCrime = mCrime;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutinflator = LayoutInflater.from(getActivity());


                return new CrimeHolder(layoutinflator, parent);


        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Crime crime = mCrime.get(position);

                CrimeHolder c = (CrimeHolder) holder;
                c.Bind(crime);

        }


        @Override
        public int getItemCount() {
            return mCrime.size();
        }
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_crime, container, false);
        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(savedInstanceState!=null)
            mItemVisible=savedInstanceState.getBoolean(SUBTITLE_VISIBLE);


        updateUI();
        setHasOptionsMenu(true);

        return v;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_list_crime, menu);

        MenuItem item=menu.findItem(R.id.show_subtitle);
        if(mItemVisible)
            item.setTitle(R.string.Hide_subtitle);
        else
            item.setTitle(R.string.show_subtitle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(SUBTITLE_VISIBLE,mItemVisible);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       //

        switch (item.getItemId())
        {
            case (R.id.add_crime) :
            {
                Crime c=new Crime();
                CrimeLab.getsCrimeLabOBJ(getActivity()).addCrime(c);
                Intent intent=CrimePagerActivity.getIntent(getActivity(),c.getMid());
                startActivity(intent); return true;
            }
            case (R.id.show_subtitle) :
                {

                    mItemVisible=!mItemVisible;
                    getActivity().invalidateOptionsMenu();
                    updateSubtitle();
                    return true;

                }
            default :
                return super.onOptionsItemSelected(item);


        }
    }

    private void updateSubtitle()
    {
        CrimeLab labObj=CrimeLab.getsCrimeLabOBJ(getActivity());
        int count=labObj.getmCrimes().size();
        String subtile =getString(R.string.subtitle_format,count);

        if(mItemVisible==false)
            subtile=null;
        AppCompatActivity act=(AppCompatActivity) getActivity();
        act.getSupportActionBar().setSubtitle(subtile);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        CrimeLab cl = CrimeLab.getsCrimeLabOBJ(getActivity());
        List<Crime> clist = cl.getmCrimes();
        if (crimeAdaptor == null) {
            crimeAdaptor = new CrimeAdaptor(clist);
            mCrimeRecyclerView.setAdapter(crimeAdaptor);
        } else
        {
            crimeAdaptor.setmCrime(clist);
            crimeAdaptor.notifyDataSetChanged();
        }


    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView mtitle;
        private TextView mDate;
        private Crime mCrime;
        private ImageView cImageView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_crime, parent, false));
            mtitle = (TextView) itemView.findViewById(R.id.title_crime);
            mDate = (TextView) itemView.findViewById(R.id.date_crime);
            cImageView = (ImageView) itemView.findViewById(R.id.imageViewCrime);
            itemView.setOnClickListener(this);
        }

        public void Bind(Crime crime) {
            mCrime = crime;
            mtitle.setText(mCrime.getTitleCrime());
            mDate.setText(mCrime.getCrimDate().toString());
            if (!mCrime.isStatus())
                cImageView.setVisibility(View.VISIBLE);
            else
                cImageView.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimePagerActivity.getIntent(getActivity(), mCrime.getMid());
            startActivity(intent);

        }
    }
}