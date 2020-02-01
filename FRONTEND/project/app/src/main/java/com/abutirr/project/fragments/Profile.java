package com.abutirr.project.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abutirr.project.MainActivity;
import com.abutirr.project.R;
import com.abutirr.project.SPManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    TextView userNameText
            ,emailText
            ,phoneNumberText,genderText
            ,dateOfBirthText,typeIdText;

    Button editButton ;
    ImageView homeButton ;

    private MainActivity activity;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = ((MainActivity)context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        // BINDING
        userNameText = v.findViewById(R.id.userNameProfile);
        emailText = v.findViewById(R.id.emailProfile);
        phoneNumberText = v.findViewById(R.id.phoneNumberProfile);
        genderText = v.findViewById(R.id.genderProfile);
        dateOfBirthText = v.findViewById(R.id.dateOfBirthProfile);
        typeIdText = v.findViewById(R.id.typeIdProfile);
        editButton = v.findViewById(R.id.editButton);
        homeButton=v.findViewById(R.id.homeButton);


       // SETTING PROFILE VALUES WITH SHARED PREF
        userNameText.setText(SPManager.read(SPManager.USERNAME,""));
        emailText.setText(SPManager.read(SPManager.EMAIL,""));
        phoneNumberText.setText(SPManager.read(SPManager.PHONENUMBER,""));
        genderText.setText(SPManager.read(SPManager.GENDER,""));
        dateOfBirthText.setText(SPManager.read(SPManager.DATEOFBIRTH,""));

        if(SPManager.read(SPManager.TYPEID,-1) == 2)
            typeIdText.setText("Driver");

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               activity.getNavController().navigate(R.id.action_profile_to_profileEdit);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController=Navigation.findNavController(getActivity(),R.id.host_fragment);
                navController.navigate(R.id.action_profileFragment_to_mainFragment);
            }
        });

    }




}
