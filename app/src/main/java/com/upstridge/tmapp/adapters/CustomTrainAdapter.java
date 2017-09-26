package com.upstridge.tmapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.upstridge.tmapp.models.PicassoClient;
import com.upstridge.tmapp.R;
import com.upstridge.tmapp.models.Trains;

import java.util.ArrayList;

/**
 * Created by Wango-PC on 6/8/2017.
 */

public class CustomTrainAdapter extends BaseAdapter implements Filterable {

    Context c;
    ArrayList<Trains> trains;
    ArrayList<Trains> trainsFiltered;
    private TrainFilter trainFilter;

    LayoutInflater inflater;
    public CustomTrainAdapter(Context c, ArrayList<Trains> trains){
        this.c = c;
        this.trains = trains;
        this.trainsFiltered = trains;

        inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
    }

    @Override
    public int getCount() {
        return trainsFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return trainsFiltered.get(position);
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
        TextView capacity = (TextView) convertView.findViewById(R.id.capacity);
        //TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView vip = (TextView) convertView.findViewById(R.id.availability);
        TextView economic = (TextView) convertView.findViewById(R.id.economicfare);
        TextView organization = (TextView) convertView.findViewById(R.id.organization);
        TextView vehicleid = (TextView) convertView.findViewById(R.id.vehicleid);
        TextView firstclassapply = (TextView) convertView.findViewById(R.id.firstclassapply);

        Trains train = trains.get(position);
        nametxt.setText(train.getName());
        route.setText(train.getRoute());
        //arrival.setText(train.getArrival());
        //departure.setText(train.getDeparture());
        //price.setText(vehicle.getPrice());
        vip.setText(train.getVipprice());
        economic.setText(train.getEconomicfare());
        organization.setText(train.getOrganization());
        vehicleid.setText(train.getVehicleid());
        firstclassapply.setText(train.getFirstclassapply());
        capacity.setText(train.getCapacity());

        PicassoClient.downloadImage(c,train.getImageUrl(), "train", logo);

        return convertView;
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