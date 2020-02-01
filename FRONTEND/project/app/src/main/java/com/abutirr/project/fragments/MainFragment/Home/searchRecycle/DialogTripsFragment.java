package com.abutirr.project.fragments.MainFragment.Home.searchRecycle;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abutirr.project.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogTripsFragment extends DialogFragment {
    
    Button joinATrip;
    TextView dialogSeats , dialogPrice ,dialogDestination , dialogSource,
    dialogTime,dialogDriverName,dialogCarModel,dialogCarLicense,dialogDate;

    // THE HOLL CLASS NEEDS TO BE REWRITTEN ***************************
    public DialogTripsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dialog_trips, container, false);

        // BINDING
        dialogSeats = v.findViewById(R.id.textSeatsBookingText);
        dialogPrice = v.findViewById(R.id.textPriceBookingText);
        dialogDestination = v.findViewById(R.id.CityDestinationBookingText);
        dialogSource = v.findViewById(R.id.CitySourceBookingText);
        dialogTime = v.findViewById(R.id.TimeBookingText);
        dialogDriverName = v.findViewById(R.id.DriverNameBookingText);
        dialogCarModel = v.findViewById(R.id.CarModelBookingText);
        dialogCarLicense = v.findViewById(R.id.CarLicenseBookingText);
        dialogDate = v.findViewById(R.id.DateBookingText);
        joinATrip = v.findViewById(R.id.bookASeatButton);

        // NO TITLE
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // BUTTON
        joinATrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialog().dismiss();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
