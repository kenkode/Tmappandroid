package com.upstridge.tmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Wango-PC on 5/6/2017.
 */

public class CustomVehicleAdapter extends BaseAdapter implements Filterable{

    Context c;
    ArrayList<Vehicles> vehicles;
    ArrayList<Vehicles> vehiclesFiltered;
    private VehicleFilter vehicleFilter;

    LayoutInflater inflater;
    public CustomVehicleAdapter(Context c, ArrayList<Vehicles> vehicles){
        this.c = c;
        this.vehicles = vehicles;
        this.vehiclesFiltered = vehicles;

        inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
    }

    @Override
    public int getCount() {
        return vehiclesFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return vehiclesFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
           convertView = inflater.inflate(R.layout.model,parent,false);
        }

        TextView nametxt = (TextView) convertView.findViewById(R.id.hotelName);
        ImageView logo = (ImageView) convertView.findViewById(R.id.hotelImage);
        TextView route = (TextView) convertView.findViewById(R.id.roomtype);
        //TextView arrival = (TextView) convertView.findViewById(R.id.adults);
        //TextView departure = (TextView) convertView.findViewById(R.id.children);
        //TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView vip = (TextView) convertView.findViewById(R.id.availability);
        TextView economic = (TextView) convertView.findViewById(R.id.economicfare);
        TextView organization = (TextView) convertView.findViewById(R.id.organization);
        TextView vehicleid = (TextView) convertView.findViewById(R.id.vehicleid);
        TextView firstclassapply = (TextView) convertView.findViewById(R.id.firstclassapply);
        TextView type = (TextView) convertView.findViewById(R.id.type);
        TextView capacity = (TextView) convertView.findViewById(R.id.capacity);

        Vehicles vehicle = vehicles.get(position);
        nametxt.setText(vehicle.getName());
        route.setText(vehicle.getRoute());
        //arrival.setText(vehicle.getArrival());
        //departure.setText(vehicle.getDeparture());
        //price.setText(vehicle.getPrice());
        vip.setText(vehicle.getVipprice());
        economic.setText(vehicle.getEconomicfare());
        organization.setText(vehicle.getOrganization());
        vehicleid.setText(vehicle.getVehicleid());
        firstclassapply.setText(vehicle.getFirstclassapply());
        type.setText(vehicle.getType());
        capacity.setText(vehicle.getCapacity());

        PicassoClient.downloadImage(c,vehicle.getImageUrl(), "bus", logo);

        return convertView;
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
