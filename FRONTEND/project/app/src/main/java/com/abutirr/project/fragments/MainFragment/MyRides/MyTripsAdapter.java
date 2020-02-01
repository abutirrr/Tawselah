package com.abutirr.project.fragments.MainFragment.MyRides;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abutirr.project.Api.ApiClient;
import com.abutirr.project.R;
import com.abutirr.project.SPManager;
import com.abutirr.project.models.Ride;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.TRANSPARENT;

public class MyTripsAdapter extends RecyclerView.Adapter<MyTripsAdapter.MyViewHolder> {
    Context mContext;

    //غير كود changeVal
    List<Ride> mData ;
    Dialog mDialog;
  //  TextView noTripsFound;

    public MyTripsAdapter(Context mContext , List<Ride> mData) {
        this.mContext = mContext;
        this.mData=mData;
    }

    @NonNull
    @Override
    public MyTripsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        {
            View v;
            v= LayoutInflater.from(mContext).inflate(R.layout.item_recycle_my_trips,parent,false);
            final MyViewHolder myViewHolder = new MyViewHolder(v);

            mDialog=new Dialog(mContext);
            mDialog.setContentView(R.layout.fragment_dialog_trips);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));


            myViewHolder.item_contact_my_trips.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // INITALSZATION
                    TextView dialogSeats=mDialog.findViewById(R.id.textSeatsBookingText);
                    TextView dialogPrice=mDialog.findViewById(R.id.textPriceBookingText);
                    TextView dialogDestination=mDialog.findViewById(R.id.CityDestinationBookingText);
                    TextView dialogSource=mDialog.findViewById(R.id.CitySourceBookingText);
                    TextView dialogTime=mDialog.findViewById(R.id.TimeBookingText);
                    TextView dialogDate=mDialog.findViewById(R.id.DateBookingText);
                    TextView dialogDriverName = mDialog.findViewById(R.id.DriverNameBookingText);
                    TextView dialogCarModel = mDialog.findViewById(R.id.CarModelBookingText);
                    TextView dialogCarLicense = mDialog.findViewById(R.id.CarLicenseBookingText);
                    Button deleteButton=mDialog.findViewById(R.id.bookASeatButton);

                    // SETTING THE DIALOG TEXT VIEW
                    String SOURCE = mData.get(myViewHolder.getAdapterPosition()).getSourceCity()
                            +" / "+mData.get(myViewHolder.getAdapterPosition()).getSourcePin();

                    String DESTINATION = mData.get(myViewHolder.getAdapterPosition()).getDestinationCity()
                            +" / "+mData.get(myViewHolder.getAdapterPosition()).getDestinationPin();
                    dialogSource.setText(SOURCE);
                    dialogDestination.setText(DESTINATION);
                    String seats = mData.get(myViewHolder.getAdapterPosition()).getSeatsBooked()+"/"+ mData.get(myViewHolder.getAdapterPosition()).getSeatsAvailable() ;
                    dialogSeats.setText(seats);
                    dialogPrice.setText(String.valueOf(mData.get(myViewHolder.getAdapterPosition()).getPrice()));

                    String timeStamp = mData.get(myViewHolder.getAdapterPosition()).getRideDate();
                    String onlyDate = timeStamp.substring(0, 10);
                    String timeWithSec = mData.get(myViewHolder.getAdapterPosition()).getRideTime();
                    String onlyTime = timeWithSec.substring(0,5);

                    dialogDate.setText(onlyDate);
                    dialogTime.setText(onlyTime);
                    // THIS IS A PROBLEM , IT DOES NOT EXCEPT THE USER MODEL AND GETS A NULLREFERENCE OBJECT ================
                    String USERNAME = mData.get(myViewHolder.getAdapterPosition()).getDriver().getUser().getUserName();
                    String CARLICENSE = mData.get(myViewHolder.getAdapterPosition()).getDriver().getCar().getCarLicense();
                    String CARMODEL = mData.get(myViewHolder.getAdapterPosition()).getDriver().getCar().getCarModel();
                    dialogDriverName.setText(USERNAME);
                    dialogCarLicense.setText(CARLICENSE);
                    dialogCarModel.setText(CARMODEL);
                    //========================================================================================================

                    deleteButton.setText("Delete A Seat");


                    // FOR TESTING PURPOSES ONLY - TOAST
                    final int RIDEID =mData.get(myViewHolder.getAdapterPosition()).getRideId();
                    mDialog.show();

                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final int USERID = SPManager.read(SPManager.USERID,-1);
                            Call<ResponseBody> removeJoinedTripCall = ApiClient
                                    .getInstance()
                                    .getApi()
                                    .removeJoinedTrip(USERID,RIDEID);
                            removeJoinedTripCall.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.code() == 200){
                                        Toast.makeText(mContext,"Successfully Deleted",Toast.LENGTH_SHORT).show();

                                        // REFRESH THE LIST
                                        mData.remove(myViewHolder.getAdapterPosition());
                                        notifyDataSetChanged();
                                    }else{
                                        Toast.makeText(mContext, "something went wrong\n"+"ride id :"+RIDEID+"\nUSERID : "+USERID+"code : "+response.code()
                                                +"Response messege : "+response.message(), Toast.LENGTH_LONG).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
                                }
                            });
                            mDialog.dismiss();
                        }
                    });


                }
            });

            return myViewHolder;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull MyTripsAdapter.MyViewHolder holder, int position) {

        Ride search = mData.get(position);

        holder.sourceCity.setText(search.getSourceCity()+" / "+search.getSourcePin());
        holder.sourceCity.setTextColor(Color.YELLOW);
        holder.destinationCity.setText(search.getDestinationCity()+" / "+search.getDestinationPin());
        holder.destinationCity.setTextColor(Color.YELLOW);
        // TO ONLY EXTRACT THE DATE AND TIME OUT OF THE TIMESTAMP
        String timeStamp = search.getRideDate();
        String onlyDate = timeStamp.substring(0, 10);
        String timeWithSec = search.getRideTime();
        String onlyTime = timeWithSec.substring(0,5);
        holder.date.setText(onlyDate);
        holder.time.setText(onlyTime);

        String seats = search.getSeatsBooked()+"/"+search.getSeatsAvailable();
        if(search.getSeatsAvailable() ==  search.getSeatsBooked()){
            holder.seats.setText("  FULL");
            holder.seats.setTextColor(Color.RED);
        }else{
            holder.seats.setText(seats);
        }
        holder.price.setText(String.valueOf(search.getPrice()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView sourceCity ,
                destinationCity ,
                time ,
                date ,
                price ,
                seats ;
        private LinearLayout item_contact_my_trips;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            seats=itemView.findViewById(R.id.textSeatsMyTrips);
            price=itemView.findViewById(R.id.textPriceTrips);
            date=itemView.findViewById(R.id.textDateTrips);
            time=itemView.findViewById(R.id.textTimeTrips);
            sourceCity=itemView.findViewById(R.id.textCitySourceTrips);
            destinationCity=itemView.findViewById(R.id.textCityDistenationTrips);
            item_contact_my_trips=itemView.findViewById(R.id.item_contact_my_trips);

          //  noTripsFound = itemView.findViewById(R.id.myTripsNoTripsFound);

        }

    }
}
