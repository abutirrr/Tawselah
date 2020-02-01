package com.abutirr.project.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abutirr.project.Api.ApiClient;
import com.abutirr.project.DateAndTime;
import com.abutirr.project.R;
import com.abutirr.project.SPManager;
import com.abutirr.project.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEdit extends Fragment {
    Button updateProfileButton;
    ImageView backToProfileButton;

    EditText email,
            password,
            phoneNumber,
            rePassword;

    TextView userNameEditProfile,dateOfBirthTextEditProfile;

    DatePickerDialog.OnDateSetListener date;
    DateAndTime dateAndTime =new DateAndTime();

    ProgressBar progressBar;
    public ProfileEdit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        // BINDING
        progressBar = v.findViewById(R.id.progress_bar_circular);
        progressBar.setVisibility(View.INVISIBLE);

        updateProfileButton = v.findViewById(R.id.updateProfileButton);
        backToProfileButton = v.findViewById(R.id.backToProfileButton);
        email = v.findViewById(R.id.emailTextEditProfile);
        password = v.findViewById(R.id.passwordTextEditProfile);
        rePassword=v.findViewById(R.id.rePasswordTextEditProfile);
        phoneNumber = v.findViewById(R.id.phoneNumberTextEditProfile);
        dateOfBirthTextEditProfile = v.findViewById(R.id.dateOfBirthTextEditProfile);
        userNameEditProfile = v.findViewById(R.id.userNameEditProfile);

        // SETTING PROFILE VALUES WITH SHARED PREF
        userNameEditProfile.setText(SPManager.read(SPManager.USERNAME,""));
        password.setText(SPManager.read(SPManager.PASSWORD,""));
        rePassword.setText(SPManager.read(SPManager.PASSWORD,""));
        email.setText(SPManager.read(SPManager.EMAIL,""));
        phoneNumber.setText(SPManager.read(SPManager.PHONENUMBER,""));
        dateOfBirthTextEditProfile.setText(SPManager.read(SPManager.DATEOFBIRTH,""));

        return v;
    }

    public void updateUserInformation(){

        String EMAIL , PASSWORD , DATEOFBIRTH ,PHONENUMBER , rePASSWORD ;
        Integer USERID  ;

        USERID = SPManager.read(SPManager.USERID,-1);
        EMAIL = email.getText().toString();
        PASSWORD = password.getText().toString();
        rePASSWORD = password.getText().toString();
        DATEOFBIRTH = dateOfBirthTextEditProfile.getText().toString();
        PHONENUMBER = phoneNumber.getText().toString();

        // HERE COMES THE SIGNUP VALIDATION


        if(!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
            email.setError("false Email format");
            email.requestFocus();
            return;
        }
        if(PASSWORD.isEmpty()){
            password.setError("Passsword required");
            password.requestFocus();
            return;
        }
        if(rePASSWORD.isEmpty()){
            rePassword.setError("Passsword required");
            rePassword.requestFocus();
            return;
        }
        if(!PASSWORD.equals(rePASSWORD)){
            rePassword.setError("Passsword does not match");
            password.requestFocus();
            rePassword.requestFocus();
            return;
        }
        if(PHONENUMBER.isEmpty()){
            phoneNumber.setError("PhoneNumber required");
            phoneNumber.requestFocus();
            return;
        }
        if(PHONENUMBER.length() != 10){
            phoneNumber.setError("PhoneNumber must be only 10 digits");
            phoneNumber.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // update to the shared preference
        Call<User> updateAUserCall = ApiClient
                .getInstance()
                .getApi()
                .updateAUser(USERID,PASSWORD,EMAIL,PHONENUMBER,DATEOFBIRTH);


        updateAUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 201){
                    SPManager.save(SPManager.PASSWORD,password.getText().toString());
                    SPManager.save(SPManager.DATEOFBIRTH,dateOfBirthTextEditProfile.getText().toString());
                    SPManager.save(SPManager.EMAIL,email.getText().toString());
                    SPManager.save(SPManager.PHONENUMBER,phoneNumber.getText().toString());

                    Toast.makeText(getContext(), "Updated successfuly", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "did not work", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "errrrrrrrrorrrrrrrr", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserInformation();
            }
        });

        backToProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController = Navigation.findNavController(getActivity(),R.id.host_fragment);
                navController.navigate(R.id.action_profileEdit_to_profile);
            }
        });

        //Date Dialog
        dateOfBirthTextEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateAndTime.setDateDialog(date,getContext());
            }
        });

        //set text date from dialog
        date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateOfBirthTextEditProfile.setText(year+"/"+month+"/"+dayOfMonth);
            }
        };


    }
}
