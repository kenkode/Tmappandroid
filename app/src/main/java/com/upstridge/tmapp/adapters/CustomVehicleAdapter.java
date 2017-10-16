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
import android.widget.Toast;

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.bus.SeatSelectionActivityNew;
import com.upstridge.tmapp.models.PicassoClient;
import com.upstridge.tmapp.models.Vehicles;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

/**
 * Created by Wango-PC on 5/6/2017.
 */

public class CustomVehicleAdapter extends RecyclerView.Adapter<CustomVehicleAdapter.ViewHolder> implements Filterable{

    Context c;
    ArrayList<Vehicles> vehicles;
    String destination;
    String date;
    String time;
    String origin;
    ArrayList<Vehicles> vehiclesFiltered;
    private VehicleFilter vehicleFilter;

    LayoutInflater inflater;
    public CustomVehicleAdapter(Context c, ArrayList<Vehicles> vehicles, String destination, String date, String time, String origin){
        this.c = c;
        this.vehicles = vehicles;
        this.vehiclesFiltered = vehicles;
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

        Vehicles vehicle = vehicles.get(position);
        holder.nametxt.setText(vehicle.getName());
        holder.route.setText(vehicle.getOname()+" to "+vehicle.getDname());
        //arrival.setText(vehicle.getArrival());
        //departure.setText(vehicle.getDeparture());
        //price.setText(vehicle.getPrice());
        holder.vip.setText("Vip Fare : KES "+formatter.format(Double.parseDouble(vehicle.getVipprice())));
        holder.economic.setText( "Economic Fare : KES "+formatter.format(Double.parseDouble(vehicle.getEconomicfare())));
        holder.organization.setText(vehicle.getOrganization());
        holder.vehicleid.setText(vehicle.getVehicleid());
        holder.firstclassapply.setText(vehicle.getFirstclassapply());
        holder.type.setText(vehicle.getType());
        holder.capacity.setText(vehicle.getCapacity());

        PicassoClient.downloadImage(c,BASE_URL + "public/uploads/logo/"+vehicle.getImageUrl(), "bus", holder.logo);
        //Toast.makeText(c,vehicle.getVipprice(),Toast.LENGTH_SHORT).show();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicle =((TextView)view.findViewById(R.id.hotelName)).getText().toString();
                //arr =((TextView)view.findViewById(R.id.adults)).getText().toString().replace("Arrival : ","");
                //dep =((TextView)view.findViewById(R.id.children)).getText().toString().replace("Departure : ","");
                String vipprice =((TextView)view.findViewById(R.id.availability)).getText().toString().replace("Vip Fare : KES ","");
                String ecprice =((TextView)view.findViewById(R.id.economicfare)).getText().toString().replace("Economic Fare : KES ","");
                String type =((TextView)view.findViewById(R.id.type)).getText().toString();
                String capacity =((TextView)view.findViewById(R.id.capacity)).getText().toString();
                String organization =((TextView)view.findViewById(R.id.organization)).getText().toString();
                String vid =((TextView)view.findViewById(R.id.vehicleid)).getText().toString();
                String firstclassapply =((TextView)view.findViewById(R.id.firstclassapply)).getText().toString();

                //Toast.makeText(c,arr+"-"+dep+"-"+vipprice+"-"+ecprice+"-"+capacity+"-"+type,Toast.LENGTH_LONG).show();

                Intent i = new Intent(c, SeatSelectionActivityNew.class);
                Bundle b = new Bundle();
                b.putString("destination", destination);
                b.putString("date", date);
                b.putString("time", time);
                b.putString("vehicle", vehicle);
                b.putString("origin",origin);
                //b.putString("arrival", arr);
                //b.putString("departure", dep);
                b.putString("vip", vipprice);
                b.putString("economic", ecprice);
                b.putString("type",type);
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
        return vehiclesFiltered.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nametxt;
        public TextView route;
        public TextView economic;
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
            route = (TextView) convertView.findViewById(R.id.roomtype);
            //TextView arrival = (TextView) convertView.findViewById(R.id.adults);
            //TextView departure = (TextView) convertView.findViewById(R.id.children);
            //TextView price = (TextView) convertView.findViewById(R.id.price);
            vip = (TextView) convertView.findViewById(R.id.availability);
            economic = (TextView) convertView.findViewById(R.id.economicfare);
            organization = (TextView) convertView.findViewById(R.id.organization);
            vehicleid = (TextView) convertView.findViewById(R.id.vehicleid);
            firstclassapply = (TextView) convertView.findViewById(R.id.firstclassapply);
            type = (TextView) convertView.findViewById(R.id.type);
            capacity = (TextView) convertView.findViewById(R.id.capacity);

            //progressBar= (ProgressBar) convertView.findViewById(R.id.load_cars);
            logo = (ImageView) convertView.findViewById(R.id.hotelImage);
        }
    }

    @Override
    public Filter getFilter() {
        if(vehicleFilter == null){
            vehicleFilter = new VehicleFilter();
        }
        return vehicleFilter;
    }

    private class VehicleFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint!=null && constraint.length()>0){
              ArrayList<Vehicles> tempList = new ArrayList<Vehicles>();

                tempList.clear();
                for (Vehicles vehicle : vehicles){
                    if(vehicle.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(vehicle);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;

            }else{
                filterResults.count = vehicles.size();
                filterResults.values = vehicles;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            vehiclesFiltered = (ArrayList<Vehicles>)results.values;
            notifyDataSetChanged();
        }
    }
}
