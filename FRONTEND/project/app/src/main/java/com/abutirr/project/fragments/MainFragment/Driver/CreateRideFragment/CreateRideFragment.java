package com.abutirr.project.fragments.MainFragment.Driver.CreateRideFragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abutirr.project.Api.ApiClient;
import com.abutirr.project.DateAndTime;
import com.abutirr.project.R;
import com.abutirr.project.SPManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRideFragment extends DialogFragment {


    Button buttonCreateRide;
    Spinner spinnerCitySource, spinnerCityDestenation,
            spinnerPinSource, spinnerPinDestenation, spinnerSeats, spinnerPrrice;
    TextView dateText,timeText;
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog.OnTimeSetListener time;


    DateAndTime dateAndTime =new DateAndTime();



    public CreateRideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_create_ride, container, false);

        buttonCreateRide = v.findViewById(R.id.buttonCreateRide);

        spinnerCitySource = v.findViewById(R.id.spinnerCitySource);
        spinnerCityDestenation = v.findViewById(R.id.spinnerCityDestenation);
        spinnerPinSource = v.findViewById(R.id.spinnerPinSource);
        spinnerPinDestenation = v.findViewById(R.id.spinnerPinDestenation);
        spinnerSeats = v.findViewById(R.id.spinnerSeats);
        spinnerPrrice = v.findViewById(R.id.spinnerPrice);

        dateText=v.findViewById(R.id.textDateCreateRide);
        timeText=v.findViewById(R.id.textTimeCreateRide);


        String[] city = getResources().getStringArray(R.array.city);
        String[] seats = getResources().getStringArray(R.array.seats);
        String[] price = getResources().getStringArray(R.array.price);
        String[] pinDefault = getResources().getStringArray(R.array.pinAmman);

        addSpinner(city, spinnerCitySource);
        addSpinner(city,spinnerCityDestenation);
        addSpinner(seats, spinnerSeats);
        addSpinner(price, spinnerPrrice);
        addSpinner(pinDefault, spinnerPinSource);
        addSpinner(pinDefault, spinnerPinDestenation);

        spinnerPinSource.setEnabled(false);
        spinnerPinDestenation.setEnabled(false);


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return v;
    }

    public void createARide(){
        // THE PROBLEM IS WITH  DATE WHEN GETTING THE VALUES BETWEEN 0-9 IT GIVES ZERO ************************
        int DRIVERID = SPManager.read(SPManager.DRIVERID,-1);
        String SOURCECITY = spinnerCitySource.getSelectedItem().toString();
        String DESTINATIONCITY = spinnerCityDestenation.getSelectedItem().toString();
        String SOURCEPIN = spinnerPinSource.getSelectedItem().toString();
        String DESTINATIONPIN = spinnerPinDestenation.getSelectedItem().toString();
        String RIDETIME =timeText.getText().toString();
        String RIDEDATE = dateText.getText().toString();
        String PRICE = spinnerPrrice.getSelectedItem().toString();
        int SEATSAVAILABLE = spinnerSeats.getSelectedItemPosition();
        String DATETIMESTAMP = RIDEDATE + " "+ RIDETIME;

        // VALIDATION
        if(SOURCECITY.equals("Select City")){
            TextView errorText = (TextView)spinnerCitySource.getSelectedView();
            errorText.setError("City Required");
            return ;
        }
        if(DESTINATIONCITY.equals("Select City")){
            TextView errorText = (TextView)spinnerCityDestenation.getSelectedView();
            errorText.setError("City Required");
            return ;
        }
        if(RIDEDATE.equals("YYYY/MM/DD")){
            dateText.setError("Date required");
            dateText.requestFocus();
            return ;
        }
        if(SEATSAVAILABLE == 0){
            TextView errorText = (TextView)spinnerSeats.getSelectedView();
            errorText.setError("Seats Reuired");
            errorText.requestFocus();
            return ;
        }
        if(PRICE.equals("Select Price")){
            TextView errorText = (TextView)spinnerPrrice.getSelectedView();
            errorText.setError("Price Reuired");
            errorText.requestFocus();
            return ;
        }

        Call<ResponseBody> createARideCall = ApiClient
                .getInstance()
                .getApi()
                .createARide(DRIVERID,SOURCECITY,DESTINATIONCITY,SOURCEPIN,DESTINATIONPIN,RIDETIME,DATETIMESTAMP,PRICE,SEATSAVAILABLE);

        createARideCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.code() == 201){

                        Toast.makeText(getContext(), "Ride Sucessfuly created", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(), "did not work", Toast.LENGTH_SHORT).show();
                }

                getDialog().dismiss();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();

            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        spinnerCitySource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(0)) {

                    spinnerPinSource.setEnabled(false);
                }

                else if (spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(1)) {
                    spinnerPinSource.setEnabled(true);
                    String[] ammanPin = getResources().getStringArray(R.array.pinAmman);
                    addSpinner(ammanPin, spinnerPinSource);
                }
                else if(spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(2)){
                    spinnerPinSource.setEnabled(true);
                    String[] aqabaPin = getResources().getStringArray(R.array.pinAqaba);
                    addSpinner(aqabaPin,spinnerPinSource);
                }
                else if(spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(3)){
                    spinnerPinSource.setEnabled(true);
                    String[] irbedPin = getResources().getStringArray(R.array.pinIrbed);
                    addSpinner(irbedPin,spinnerPinSource);
                }
                else if(spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(4)){
                    spinnerPinSource.setEnabled(true);
                    String[] zarqaPin = getResources().getStringArray(R.array.pinZarqa);
                    addSpinner(zarqaPin,spinnerPinSource);
                }
                else if(spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(5)){
                    spinnerPinSource.setEnabled(true);
                    String[] madabaPin = getResources().getStringArray(R.array.pinMadaba);
                    addSpinner(madabaPin,spinnerPinSource);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

              //  Toast.makeText(getContext(), "Please Select City", Toast.LENGTH_LONG).show();

            }
        });

        spinnerCityDestenation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                {

                    if (spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(0)) {

                        spinnerPinDestenation.setEnabled(false);
                    }

                    else if (spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(1)) {
                        spinnerPinDestenation.setEnabled(true);
                        String[] ammanPin = getResources().getStringArray(R.array.pinAmman);
                        addSpinner(ammanPin, spinnerPinDestenation);
                    }
                    else if(spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(2)){
                        spinnerPinDestenation.setEnabled(true);
                        String[] aqabaPin = getResources().getStringArray(R.array.pinAqaba);
                        addSpinner(aqabaPin,spinnerPinDestenation);
                    }
                    else if(spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(3)){
                        spinnerPinDestenation.setEnabled(true);
                        String[] irbedPin = getResources().getStringArray(R.array.pinIrbed);
                        addSpinner(irbedPin,spinnerPinDestenation);
                    }
                    else if(spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(4)){
                        spinnerPinDestenation.setEnabled(true);
                        String[] zarqaPin = getResources().getStringArray(R.array.pinZarqa);
                        addSpinner(zarqaPin,spinnerPinDestenation);
                    }
                    else if(spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(5)){
                        spinnerPinDestenation.setEnabled(true);
                        String[] madabaPin = getResources().getStringArray(R.array.pinMadaba);
                        addSpinner(madabaPin,spinnerPinDestenation);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCitySource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(0)) {

                    spinnerPinSource.setEnabled(false);
                   // Toast.makeText(getContext(), "Please Select City", Toast.LENGTH_SHORT).show();

                }

                else if (spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(1)) {
                    spinnerPinSource.setEnabled(true);
                    String[] ammanPin = getResources().getStringArray(R.array.pinAmman);
                    addSpinner(ammanPin, spinnerPinSource);
                }
                else if(spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(2)){
                    spinnerPinSource.setEnabled(true);
                    String[] aqabaPin = getResources().getStringArray(R.array.pinAqaba);
                    addSpinner(aqabaPin,spinnerPinSource);
                }
                else if(spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(3)){
                    spinnerPinSource.setEnabled(true);
                    String[] irbedPin = getResources().getStringArray(R.array.pinIrbed);
                    addSpinner(irbedPin,spinnerPinSource);
                }
                else if(spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(4)){
                    spinnerPinSource.setEnabled(true);
                    String[] zarqaPin = getResources().getStringArray(R.array.pinZarqa);
                    addSpinner(zarqaPin,spinnerPinSource);
                }
                else if(spinnerCitySource.getSelectedItem().toString() == spinnerCitySource.getItemAtPosition(5)){
                    spinnerPinSource.setEnabled(true);
                    String[] madabaPin = getResources().getStringArray(R.array.pinMadaba);
                    addSpinner(madabaPin,spinnerPinSource);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

             //   Toast.makeText(getContext(), "Please Select City", Toast.LENGTH_LONG).show();

            }
        });

        spinnerCityDestenation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                {

                    if (spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(0)) {

                        spinnerPinDestenation.setEnabled(false);
                    }

                    else if (spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(1)) {
                        spinnerPinDestenation.setEnabled(true);
                        String[] ammanPin = getResources().getStringArray(R.array.pinAmman);
                        addSpinner(ammanPin, spinnerPinDestenation);
                    }
                    else if(spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(2)){
                        spinnerPinDestenation.setEnabled(true);
                        String[] aqabaPin = getResources().getStringArray(R.array.pinAqaba);
                        addSpinner(aqabaPin,spinnerPinDestenation);
                    }
                    else if(spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(3)){
                        spinnerPinDestenation.setEnabled(true);
                        String[] irbedPin = getResources().getStringArray(R.array.pinIrbed);
                        addSpinner(irbedPin,spinnerPinDestenation);
                    }
                    else if(spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(4)){
                        spinnerPinDestenation.setEnabled(true);
                        String[] zarqaPin = getResources().getStringArray(R.array.pinZarqa);
                        addSpinner(zarqaPin,spinnerPinDestenation);
                    }
                    else if(spinnerCityDestenation.getSelectedItem().toString() == spinnerCityDestenation.getItemAtPosition(5)){
                        spinnerPinDestenation.setEnabled(true);
                        String[] madabaPin = getResources().getStringArray(R.array.pinMadaba);
                        addSpinner(madabaPin,spinnerPinDestenation);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonCreateRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "works", Toast.LENGTH_SHORT).show();
            }
        });
        buttonCreateRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createARide();
            }
        });



        //Date Dialog
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               dateAndTime.setDateDialogFuture(date,getContext());

            }
        });

        //set text date from dialog
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                dateText.setText(year+"/"+month+"/"+dayOfMonth);
            }
        };

        //Time Dialog
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateAndTime.setTimeDialog(time,getContext());
            }
        });

        //set text date from dialog
        time=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeText.setText(hourOfDay+":"+minute);
            }
        };
    }
    public void addSpinner(String[] strings, Spinner spinner) {

        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setAdapter(arrayAdapter);


    }
}
