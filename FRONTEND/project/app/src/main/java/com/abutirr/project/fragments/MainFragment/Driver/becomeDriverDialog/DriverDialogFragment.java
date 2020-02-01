package com.abutirr.project.fragments.MainFragment.Driver.becomeDriverDialog;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abutirr.project.Api.ApiClient;
import com.abutirr.project.MainActivity;
import com.abutirr.project.R;
import com.abutirr.project.SPManager;
import com.abutirr.project.fragments.MainFragment.Driver.DriverFragment;
import com.abutirr.project.models.BecomeDriverResponse;
import com.abutirr.project.models.ReturnDriverIdResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DriverDialogFragment extends DialogFragment {

    // INITALISATION
    Button beccomeDriverButton;
    EditText carModelText , carLicenseText ;
    Fragment selectedFragment ;

    ProgressBar progressBar;

    public DriverDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_driver_dialog, container, false);

        // BINDING
        beccomeDriverButton = v.findViewById(R.id.buttonBecomeDriver);
        carModelText = v.findViewById(R.id.editTextCarModel);
        carLicenseText = v.findViewById(R.id.editTextCarLicense);
        progressBar = v.findViewById(R.id.progress_bar_circular);
        progressBar.setVisibility(View.INVISIBLE);
        // NO TITLE
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return v;  }



        public void becomeAdriver(){


            String CARMODEL , CARLICENSE ;
            final int USERID;

            CARMODEL = carModelText.getText().toString().trim();
            CARLICENSE = carLicenseText.getText().toString().trim();
            USERID = SPManager.read(SPManager.USERID,-1);

            if(CARMODEL.isEmpty()){
                carModelText.setError("car model required");
                carModelText.requestFocus();
                return;
            }
            if(CARLICENSE.isEmpty()){
                carLicenseText.setError("car license required");
                carLicenseText.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            Call<BecomeDriverResponse> becomeDriverResponseCall = ApiClient
                    .getInstance()
                    .getApi()
                    .becomeADriver(USERID,CARMODEL,CARLICENSE);

            becomeDriverResponseCall.enqueue(new Callback<BecomeDriverResponse>() {
                @Override
                public void onResponse(Call<BecomeDriverResponse> call, Response<BecomeDriverResponse> response) {

                    if (response.code() == 200){
                        SPManager.save(SPManager.TYPEID,2);

                        Call<ReturnDriverIdResponse> returnDriverIdResponseCall = ApiClient
                                .getInstance()
                                .getApi()
                                .returnDriverId(USERID);

                        returnDriverIdResponseCall.enqueue(new Callback<ReturnDriverIdResponse>() {
                            @Override
                            public void onResponse(Call<ReturnDriverIdResponse> call, Response<ReturnDriverIdResponse> response) {
                                SPManager.save(SPManager.DRIVERID,response.body().getDriverId());

                                //====== MOVE TO DRIVER FRAGMENT =======
                                selectedFragment = new DriverFragment();
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, selectedFragment).commit();
                                getDialog().dismiss();
                                //======================================
                            }

                            @Override
                            public void onFailure(Call<ReturnDriverIdResponse> call, Throwable t) {

                            }
                        });

                        Toast.makeText(getContext(), "NOW YOU ARE A DRIVER", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else{
                        Toast.makeText(getContext(), "did not work", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<BecomeDriverResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "ERRRROR", Toast.LENGTH_SHORT).show();
                }
            });

        }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        beccomeDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                becomeAdriver();
            }
        });
    }

}
