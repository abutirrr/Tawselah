package com.abutirr.project.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abutirr.project.Api.ApiClient;
import com.abutirr.project.R;
import com.abutirr.project.SPManager;
import com.abutirr.project.models.ReturnDriverIdResponse;
import com.abutirr.project.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
    Button loginButton , signUpLoginButton;
    TextView userName , password ;

    ProgressBar progressBar;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.INVISIBLE);
        SPManager.init(getContext().getApplicationContext());
        if(SPManager.isLogged()){
            NavController navController = Navigation.findNavController(getActivity(), R.id.host_fragment);
            navController.navigate(R.id.action_login_to_mainFragment);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_login, container, false);

        // binding
        progressBar=v.findViewById(R.id.progress_bar_circular);
        loginButton = v.findViewById(R.id.loginButton);
        signUpLoginButton = v.findViewById(R.id.signUpLoginButton);
        userName = v.findViewById(R.id.userLoginText);
        password = v.findViewById(R.id.passwordLoginText);

        return  v;
    }

    public void login(){

        String USERNAME , PASSWORD ;
        USERNAME = userName.getText().toString().trim();
        PASSWORD = password.getText().toString().trim();

        if(USERNAME.isEmpty()){
            userName.setError("Username required to login");
            userName.requestFocus();
            return;
        }
        if(PASSWORD.isEmpty()){
            password.setError("Password required to login");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Call<User> userLoginCall = ApiClient
                .getInstance()
                .getApi()
                .userLogin(USERNAME, PASSWORD);

        userLoginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


                if(response.code() == 200){

                    // the response information PARSED
                    int UserId = response.body().getUserId();
                    String UserName = response.body().getUserName();
                    String password = response.body().getPassword();
                    String dateOfBirth = response.body().getDateOfBirth();
                    String gender = response.body().getGender();
                    String email = response.body().getEmail();
                    String phoneNumber = response.body().getPhoneNumber();
                    int typeId = response.body().getTypeId();


                    // SHARED PREFERENCE
                    SPManager.init(getContext().getApplicationContext());
                    SPManager.save(SPManager.USERID,UserId);
                    SPManager.save(SPManager.USERNAME,UserName);
                    SPManager.save(SPManager.PASSWORD,password);
                    SPManager.save(SPManager.DATEOFBIRTH,dateOfBirth);
                    SPManager.save(SPManager.GENDER,gender);
                    SPManager.save(SPManager.EMAIL,email);
                    SPManager.save(SPManager.PHONENUMBER,phoneNumber);
                    SPManager.save(SPManager.TYPEID,typeId);

                    if (SPManager.read(SPManager.TYPEID , -1) == 2){
                        Call<ReturnDriverIdResponse> returnDriverIdResponseCall = ApiClient
                                .getInstance()
                                .getApi()
                                .returnDriverId(UserId);

                        returnDriverIdResponseCall.enqueue(new Callback<ReturnDriverIdResponse>() {
                            @Override
                            public void onResponse(Call<ReturnDriverIdResponse> call, Response<ReturnDriverIdResponse> response) {
                                if(response.isSuccessful())
                                {
                                    SPManager.save(SPManager.DRIVERID,response.body().getDriverId());


                                    NavController navController = Navigation.findNavController(getActivity(), R.id.host_fragment);
                                    navController.navigate(R.id.action_login_to_mainFragment);
                                }


                                else
                                    Toast.makeText(getContext(), "there is a problem", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ReturnDriverIdResponse> call, Throwable t) {

                            }
                        });

                    }else{

                        NavController navController = Navigation.findNavController(getActivity(), R.id.host_fragment);
                        navController.navigate(R.id.action_login_to_mainFragment);
                    }
                }
                 else// if(response.code() == 204)
                 {
                    Toast.makeText(getContext(),"Invalid Entry", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();
            }
        });
        signUpLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.host_fragment);
                navController.navigate(R.id.action_login_to_signUp);
            }
        });
    }

}
