package com.abutirr.project.fragments.MainFragment.Home;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.abutirr.project.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    Spinner spinnerCitySource ,spinnerCityDestenation;
    Button searchButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        spinnerCitySource = v.findViewById(R.id.spinnerCitySource);
        spinnerCityDestenation = v.findViewById(R.id.spinnerCityDestenation);

        String[] city = getResources().getStringArray(R.array.city);

        spinnerAdd(spinnerCitySource,city);
        spinnerAdd(spinnerCityDestenation,city);

        return v;
    }

    public void searchForARide(){

        String sourceCity , destinationCity ;
        sourceCity = spinnerCitySource.getSelectedItem().toString();
        destinationCity = spinnerCityDestenation.getSelectedItem().toString();

        if(sourceCity.equals("Select City")){
            Toast.makeText(getContext(), "SELECT SOURCE CITY", Toast.LENGTH_LONG).show();
            return;
        }else if(destinationCity.equals("Select City")){
            Toast.makeText(getContext(), "SELECT DESTINATION CITY", Toast.LENGTH_LONG).show();
            return;
        }else {

        }

        Bundle bundle = new Bundle();
        bundle.putString("sourceCity",sourceCity);
        bundle.putString("destinationCity",destinationCity);

        NavController navController = Navigation.findNavController(getActivity(),R.id.host_fragment);
        navController.navigate(R.id.action_mainFragment_to_searchedTrips,bundle);
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        searchButton=view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForARide();
            }
        });

    }


    public void spinnerAdd(Spinner spinner, String[] strings) {
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(this.getActivity(),android.R.layout.simple_list_item_1, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);

    }

}
