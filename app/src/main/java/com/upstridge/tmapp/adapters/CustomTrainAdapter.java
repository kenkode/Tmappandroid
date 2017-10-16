package com.upstridge.tmapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.upstridge.tmapp.models.PicassoClient;
import com.upstridge.tmapp.R;
import com.upstridge.tmapp.models.Trains;
import com.upstridge.tmapp.sgr.SgrSeats;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

/**
 * Created by Wango-PC on 6/8/2017.
 */

public class CustomTrainAdapter extends RecyclerView.Adapter<CustomTrainAdapter.ViewHolder> implements Filterable {

    Context c;
    ArrayList<Trains> trains;
    ArrayList<Trains> trainsFiltered;
    private TrainFilter trainFilter;
    String destination;
    String date;
    String time;
    String origin;

    LayoutInflater inflater;
    public CustomTrainAdapter(Context c, ArrayList<Trains> trains, String destination, String date, String time, String origin){
        this.c = c;
        this.trains = trains;
        this.trainsFiltered = trains;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.origin = origin;

        inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.model, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        final Trains train = trains.get(position);
        holder.nametxt.setText(train.getName());
        holder.route.setText(train.getOname()+" to "+train.getDname());

        holder.vip.setText("Vip Fare : KES "+formatter.format(Double.parseDouble(train.getVipprice())));
        holder.economic.setText( "Economic Fare : KES "+formatter.format(Double.parseDouble(train.getEconomicfare())));
        holder.organization.setText(train.getOrganization());
        holder.vehicleid.setText(train.getVehicleid());
        holder.firstclassapply.setText(train.getFirstclassapply());
        //holder.type.setText(aeroplane.getType());
        holder.capacity.setText(train.getCapacity());

        PicassoClient.downloadImage(c,BASE_URL + "public/uploads/logo/"+train.getImageUrl(), "train", holder.logo);
        //Toast.makeText(c,aeroplane.getVipprice(),Toast.LENGTH_SHORT).show();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicle =((TextView)view.findViewById(R.id.hotelName)).getText().toString();
                //arr =((TextView)view.findViewById(R.id.adults)).getText().toString().replace("Arrival : ","");
                //dep =((TextView)view.findViewById(R.id.children)).getText().toString().replace("Departure : ","");
                String vipprice =((TextView)view.findViewById(R.id.availability)).getText().toString().replace("Vip Fare : KES ","");
                String ecprice =((TextView)view.findViewById(R.id.economicfare)).getText().toString().replace("Economic Fare : KES ","");
                String capacity =((TextView)view.findViewById(R.id.capacity)).getText().toString();
                String organization =((TextView)view.findViewById(R.id.organization)).getText().toString();
                String vid =((TextView)view.findViewById(R.id.vehicleid)).getText().toString();
                String firstclassapply =((TextView)view.findViewById(R.id.firstclassapply)).getText().toString();

                //Toast.makeText(c,arr+"-"+dep+"-"+vipprice+"-"+ecprice+"-"+capacity+"-"+type,Toast.LENGTH_LONG).show();

                Intent i = new Intent(c, SgrSeats.class);
                Bundle b = new Bundle();
                b.putString("destination", destination);
                b.putString("date", date);
                b.putString("time", time);
                b.putString("vehicle", vehicle);
                b.putString("origin",origin);
                b.putString("arrival", train.getArrival());
                b.putString("departure", train.getDeparture());
                b.putString("vip", vipprice);
                b.putString("economic", ecprice);
                b.putString("capacity",capacity);
                b.putString("organization", organization);
                b.putString("vid", vid);
                b.putString("firstclassapply", firstclassapply);
                i.putExtras(b);
                c.startActivity(i);
            }
        });

        //PicassoClient.downloadImage(c,BASE_URL + "public/uploads/logo/"+vehicle.getImageUrl(), "bus", holder.logo);


    }

    @Override
    public int getItemCount() {
        return trainsFiltered.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nametxt;
        public TextView route;
        public TextView economic;
        public TextView business;
        public TextView children;
        public TextView organization;
        public TextView vehicleid;
        public TextView firstclassapply;
        public TextView type;
        public TextView capacity;
        public TextView vip;
        public View view;
        public ProgressBar progressBar;
        public ImageView logo;


        public ViewHolder(View convertView) {
            super(convertView);

            view = convertView;
            nametxt = (TextView) convertView.findViewById(R.id.hotelName);
            logo = (ImageView) convertView.findViewById(R.id.hotelImage);
            route = (TextView) convertView.findViewById(R.id.roomtype);
            //TextView arrival = (TextView) convertView.findViewById(R.id.adults);
            //TextView departure = (TextView) convertView.findViewById(R.id.children);
            capacity = (TextView) convertView.findViewById(R.id.capacity);
            //TextView price = (TextView) convertView.findViewById(R.id.price);
            vip = (TextView) convertView.findViewById(R.id.availability);
            economic = (TextView) convertView.findViewById(R.id.economicfare);
            organization = (TextView) convertView.findViewById(R.id.organization);
            vehicleid = (TextView) convertView.findViewById(R.id.vehicleid);
            firstclassapply = (TextView) convertView.findViewById(R.id.firstclassapply);

            //progressBar= (ProgressBar) convertView.findViewById(R.id.load_cars);
        }
    }

    @Override
    public Filter getFilter() {
        if(trainFilter == null){
            trainFilter = new TrainFilter();
        }
        return trainFilter;
    }

    private class TrainFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<Trains> tempList = new ArrayList<Trains>();

                tempList.clear();
                for (Trains train : trains){
                    if(train.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(train);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;

            }else{
                filterResults.count = trains.size();
                filterResults.values = trains;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            trainsFiltered = (ArrayList<Trains>)results.values;
            notifyDataSetChanged();
        }
    }
}