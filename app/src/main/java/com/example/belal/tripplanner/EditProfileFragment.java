package com.example.belal.tripplanner;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View v= inflater.inflate(R.layout.fragment_edit_profile, container, false);


      return  v;

    }

}
