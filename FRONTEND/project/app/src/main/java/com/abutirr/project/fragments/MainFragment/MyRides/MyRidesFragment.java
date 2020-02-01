package com.abutirr.project.fragments.MainFragment.MyRides;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abutirr.project.Api.ApiClient;
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
public class MyRidesFragment extends Fragment {



    private RecyclerView recyclerView;
    List<Ride> myRidesList ;

    ProgressBar progressBar;

    TextView noTripsFound ;

    public MyRidesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_rides, container, false);
        noTripsFound = v.findViewById(R.id.myTripsNoTripsFound);
        recyclerView=v.findViewById(R.id.recyclePassegerTrips);
        progressBar=v.findViewById(R.id.progress_bar_circular);
       final MyTripsAdapter recycleviewAdapter = new MyTripsAdapter(getContext(),myRidesList);

        int USERID = SPManager.read(SPManager.USERID,-1);

        Call<List<Ride>> fetchJoinedRideCall = ApiClient
                .getInstance()
                .getApi()
                .fetchJoinedTrips(USERID);

        fetchJoinedRideCall.enqueue(new Callback<List<Ride>>() {
            @Override
            public void onResponse(Call<List<Ride>> call, Response<List<Ride>> response) {
                if (response.code() == 200) {

                    myRidesList.addAll(response.body());

                    if (myRidesList.isEmpty()) {
                        noTripsFound.setText("NO TRIPS SCHEDULED");
                    }
                    progressBar.setVisibility(View.INVISIBLE);

                } else if (response.code() == 404) {
                    Toast.makeText(getContext(), "no Rides Found", Toast.LENGTH_SHORT).show();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        {
            super.onCreate(savedInstanceState);
            myRidesList=new ArrayList<>();

        }
    }

}
