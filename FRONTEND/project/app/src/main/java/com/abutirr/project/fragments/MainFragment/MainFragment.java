package com.abutirr.project.fragments.MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.abutirr.project.MainActivity;
import com.abutirr.project.fragments.MainFragment.Driver.DriverFragment;
import com.abutirr.project.fragments.MainFragment.Driver.becomeDriverDialog.DriverDialogFragment;
import com.abutirr.project.fragments.MainFragment.Home.HomeFragment;
import com.abutirr.project.fragments.MainFragment.MyRides.MyRidesFragment;
import com.abutirr.project.R;
import com.abutirr.project.SPManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment {
    TextView signOutText , profileText ;

    private MainActivity activity;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = ((MainActivity) context);
    }

    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        Fragment selectedFragment=null;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        bottomNavigationView=v.findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        fragmentTransaction.replace(R.id.fragment_container,new HomeFragment()).commit();

        signOutText = v.findViewById(R.id.signOutText);
        profileText = v.findViewById(R.id.profileText);


        return v;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    final Fragment[] selectedFragment = {null};
                    final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment[0] = new HomeFragment();
                            fragmentTransaction.replace(R.id.fragment_container, selectedFragment[0]).commit();
                            break;
                        case R.id.navigation_driver:
                            if (SPManager.read(SPManager.TYPEID,-1) == 1 ) {
                                DriverDialogFragment dialog = new DriverDialogFragment();
                                dialog.show(getFragmentManager(), "DriverDialogFragment");
                            } else {
                                selectedFragment[0] = new DriverFragment();
                                fragmentTransaction.replace(R.id.fragment_container, selectedFragment[0]).commit();
                            }
                            break;
                        case R.id.navigation_rides:
                            selectedFragment[0] = new MyRidesFragment();
                            fragmentTransaction.replace(R.id.fragment_container, selectedFragment[0]).commit();
                            break;
                    }
                    return true;
                }
            };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        profileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getNavController().navigate(R.id.action_mainFragment_to_profileFragment);

            }
        });

        signOutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPManager.signOut();

                activity.getNavController().navigate(R.id.action_mainFragment_to_login);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                System.exit(0);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }

}

