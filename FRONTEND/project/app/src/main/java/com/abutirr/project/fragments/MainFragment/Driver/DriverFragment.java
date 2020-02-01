package com.abutirr.project.fragments.MainFragment.Driver;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abutirr.project.Api.ApiClient;
import com.abutirr.project.fragments.MainFragment.Driver.CreateRideFragment.CreateRideFragment;
import com.abutirr.project.R;
import com.abutirr.project.SPManager;
import com.abutirr.project.models.Ride;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DriverFragment extends Fragment {


    private RecyclerView recyclerView;
    List<Ride> driverCreatedTripsList ;
    Button createARideButton ;

    TextView noRidesCreated ;

    ProgressBar progressBar;
    public DriverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_driver, container, false);

        noRidesCreated = v.findViewById(R.id.ridesNoRideCreated);
        progressBar=v.findViewById(R.id.progress_bar_circular);
        // === RECYCLE =======================================
        createARideButton=v.findViewById(R.id.buttonCreateRide);
        recyclerView=v.findViewById(R.id.recyclerCreatedTrips);
        final MyTripsCreatedAdapter recycleviewAdapter = new MyTripsCreatedAdapter(getContext(),driverCreatedTripsList);
        //=====================================================
        int DRIVERID = SPManager.read(SPManager.DRIVERID,-1);
        
        Call<List<Ride>> fetchDriverCreatedRidesCall = ApiClient
                .getInstance()
                .getApi()
                .fetchDriverCreatedRides(DRIVERID);
        
        fetchDriverCreatedRidesCall.enqueue(new Callback<List<Ride>>() {
            @Override
            public void onResponse(Call<List<Ride>> call, Response<List<Ride>> response) {
                if(response.code() == 200){

                    driverCreatedTripsList.addAll(response.body());

                    if(driverCreatedTripsList.isEmpty()){
                        noRidesCreated.setText("NO RIDES WHERE CREATED");
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(getContext(), "no Rides found", Toast.LENGTH_SHORT).show();
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(recycleviewAdapter);

            }
            @Override
            public void onFailure(Call<List<Ride>> call, Throwable t) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        
        


        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // GO TO RIDE CREATION 
        createARideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateRideFragment createRideFragment =new CreateRideFragment();
                createRideFragment.show(getFragmentManager(),"create ride");
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        {
            super.onCreate(savedInstanceState);
            driverCreatedTripsList=new ArrayList<>();
            
        }

    }
}
