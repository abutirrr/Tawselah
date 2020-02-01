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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.abutirr.project.Api.ApiClient;
import com.abutirr.project.DateAndTime;
import com.abutirr.project.R;
import com.abutirr.project.models.CreateAUser;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp extends Fragment {
    Button signUpButton ;;
    RadioButton radioButtonMale,radioButtonFemale;
    ImageView homeButton;
    TextView userName , password , email , phoneNumber , gender , dateOfBirth ,rePassword;

    DatePickerDialog.OnDateSetListener date;

    DateAndTime dateAndTime =new DateAndTime();
    ProgressBar progressBar;

    public SignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_sign_up, container, false);
        progressBar = v.findViewById(R.id.progress_bar_circular);
        progressBar.setVisibility(View.INVISIBLE);
        // binding
        userName = v.findViewById(R.id.userNameText);
        password = v.findViewById(R.id.passwordText);
        email = v.findViewById(R.id.emailText);
        phoneNumber = v.findViewById(R.id.phoneNumberText);
       // gender = v.findViewById(R.id.genderText);
        dateOfBirth = v.findViewById(R.id.dateOfBirthText);
        signUpButton  = v.findViewById(R.id.signUp);
        rePassword = v.findViewById(R.id.rePasswordText);
        homeButton = v.findViewById(R.id.homeButton);
        radioButtonMale=v.findViewById(R.id.radioButtonMale);
        radioButtonFemale=v.findViewById(R.id.radioButtonFemale);


        return v;
    }
        private void userSignUp(){

            String USERNAME , PASSWORD , EMAIL , PHONENUMBER , GENDER , DATEOFBIRTH, rePASSWORD;
            // get the input data into variables
            USERNAME = userName.getText().toString().trim();
            PASSWORD = password.getText().toString().trim();
            rePASSWORD = rePassword.getText().toString().trim();
            EMAIL = email.getText().toString().trim();
            PHONENUMBER = phoneNumber.getText().toString().trim();
            //GENDER = gender.getText().toString();
            DATEOFBIRTH = dateOfBirth.getText().toString().trim();
            if(radioButtonMale.isChecked())
                GENDER=radioButtonMale.getText().toString();
            else
                GENDER=radioButtonFemale.getText().toString();

            // HERE COMES THE SIGNUP VALIDATION

            if(USERNAME.isEmpty()){
                userName.setError("UserName required");
                userName.requestFocus();
                return;
            }
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

            if(DATEOFBIRTH.equals("YYYY/MM/DD")){
                dateOfBirth.setError("Date of Birth Rreuired");
                dateOfBirth.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            // create the request
            Call<CreateAUser> createAUserCall = ApiClient
                    .getInstance()
                    .getApi()
                    .createAUser(USERNAME,PASSWORD,EMAIL,PHONENUMBER,GENDER,DATEOFBIRTH);

            // send the request and reviecve response
            createAUserCall.enqueue(new Callback<CreateAUser>() {
                @Override
                public void onResponse(Call<CreateAUser> call, Response<CreateAUser> response) {
                    CreateAUser user = response.body();
                    if(response.code()== 201)
                    {
                       // signUpButton.setEnabled(false);
                        Toast.makeText(getContext(),"User Sucessfuly created", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        try {
                            String error = response.errorBody().string();
                            Toast.makeText(getContext(),"User Already Exists", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onFailure(Call<CreateAUser> call, Throwable t) {
                }
            });
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //Date Dialog
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dateAndTime.setDateDialog(date,getContext());
           }
       });

        //set text date from dialog
        date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                dateOfBirth.setText(year+"/"+month+"/"+dayOfMonth);
            }
        };


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userSignUp();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.host_fragment);
                navController.navigate(R.id.action_signUp_to_login);
            }
        });




    }
}
