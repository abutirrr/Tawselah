package com.abutirr.project.fragments.MainFragment.Home.searchRecycle;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abutirr.project.Api.ApiClient;
import com.abutirr.project.R;
import com.abutirr.project.models.Ride;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchedTrips extends Fragment {

    private RecyclerView recyclerView;

    private List<Ride> searchedRidesList;
    private TextView sourceCityText , destinationCityText;

    TextView noRidesFound ;
    String sourceCity , destinationCity ;

    ProgressBar progressBar;
    public SearchedTrips() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_searched_trips, container, false);

        sourceCityText = v.findViewById(R.id.sourceCitySearchedText);
        destinationCityText = v.findViewById(R.id.destinationCitySearchedText);
        progressBar=v.findViewById(R.id.progress_bar_circular);
        noRidesFound = v.findViewById(R.id.searchNoRidesFound);




        // RECIEVING DATA FROM ANOTHER FRAGMENT AND SENDING A REQUEST
        Bundle bundle=this.getArguments();
        sourceCity = bundle.getString("sourceCity");
        destinationCity = bundle.getString("destinationCity");
        //if (sourceCity)
        sourceCityText.setText(sourceCity);
        destinationCityText.setText(destinationCity);

        recyclerView=v.findViewById(R.id.recyclerView);
        final SearchAdapter adapter = new SearchAdapter(getContext(),searchedRidesList);



        final Call<List<Ride>> searchedRidesCall = ApiClient
                .getInstance()
                .getApi()
                .searchForRides(sourceCity,destinationCity);

        searchedRidesCall.enqueue(new Callback<List<Ride>>() {
            @Override
            public void onResponse(Call<List<Ride>> call, Response<List<Ride>> response) {
                if(response.code() == 200)
                {
                    searchedRidesList.addAll(response.body());

                    if(searchedRidesList.isEmpty()){
                        noRidesFound.setText("NO RIDES WHERE CREATED");
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }else if(response.code() == 404){
                    Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Ride>> call, Throwable t) {
                Toast.makeText(getContext(), "erorrrrrrrrr", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchedRidesList=new ArrayList<>();

    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);



    }
}
