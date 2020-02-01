package com.abutirr.project.fragments.MainFragment.Home.searchRecycle;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.abutirr.project.MainActivity;
import com.abutirr.project.R;
import com.abutirr.project.SPManager;
import com.abutirr.project.models.Ride;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.TRANSPARENT;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Ride> mData;
    Dialog mDialog;

    public SearchAdapter(Context mContext, List<Ride> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_recycle_trips, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(v);

        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.fragment_dialog_trips);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));

        myViewHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView dialogSeats = mDialog.findViewById(R.id.textSeatsBookingText);
                TextView dialogPrice = mDialog.findViewById(R.id.textPriceBookingText);
                TextView dialogDestination = mDialog.findViewById(R.id.CityDestinationBookingText);
                TextView dialogSource = mDialog.findViewById(R.id.CitySourceBookingText);
                TextView dialogTime = mDialog.findViewById(R.id.TimeBookingText);

                TextView dialogDriverName = mDialog.findViewById(R.id.DriverNameBookingText);
                TextView dialogCarModel = mDialog.findViewById(R.id.CarModelBookingText);
                TextView dialogCarLicense = mDialog.findViewById(R.id.CarLicenseBookingText);

                final TextView dialogDate = mDialog.findViewById(R.id.DateBookingText);
                Button joinATrip = mDialog.findViewById(R.id.bookASeatButton);

//                // FOR TESTING PURPOSES ONLY - TOAST
               final int RIDEID =mData.get(myViewHolder.getAdapterPosition()).getRideId();

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
                String USERNAME = mData.get(myViewHolder.getAdapterPosition()).getDriver().getUser().getUserName();
                String CARLICENSE = mData.get(myViewHolder.getAdapterPosition()).getDriver().getCar().getCarLicense();
                String CARMODEL = mData.get(myViewHolder.getAdapterPosition()).getDriver().getCar().getCarModel();
                dialogDriverName.setText(USERNAME);
                dialogCarLicense.setText(CARLICENSE);
                dialogCarModel.setText(CARMODEL);
                joinATrip.setText("Book A Seat");



                mDialog.show();

                joinATrip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            if(SPManager.read(SPManager.DRIVERID,-1) == mData.get(myViewHolder.getAdapterPosition()).getDriver().getDriverId()){
                                Toast.makeText(mContext, "YOU CAN NOT ADD YOURSELF \n     TO YOUR OWN TRIP", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }else{
                                int USERID = SPManager.read(SPManager.USERID,-1);

                                //  ADD THE USER TO THE TRIP
                                Call<ResponseBody> joinATripCall = ApiClient
                                        .getInstance()
                                        .getApi()
                                        .joinARide(USERID,RIDEID);

                                joinATripCall.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if(response.code() == 200){
                                            Toast.makeText(mContext, "RIDE SUCESSFULY ADDED", Toast.LENGTH_SHORT).show();
                                            notifyDataSetChanged();

                                        }else if (response.code() == 404){
                                            Toast.makeText(mContext, "TRIP IS FULL", Toast.LENGTH_SHORT).show();

                                        }else {
                                            Toast.makeText(mContext, "TRIP ALREADY BOOKED", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(mContext, "errrrror", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                mDialog.dismiss();
                            }

                    }
                });

            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
        return  mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView sourceCity ,
                destinationCity ,
                time ,
                date ,
                price ,
                seats ;
        private LinearLayout item_contact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sourceCity = itemView.findViewById(R.id.textCitySource);
            destinationCity = itemView.findViewById(R.id.textCityDestination);
            seats = itemView.findViewById(R.id.textSeats);
            price = itemView.findViewById(R.id.textPrice);
            date = itemView.findViewById(R.id.textDate);
            time = itemView.findViewById(R.id.textTime);

            item_contact=itemView.findViewById(R.id.item_contact);


        }
    }
}
