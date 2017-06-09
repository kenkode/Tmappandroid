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

public class CustomTaxiAdapter extends BaseAdapter implements Filterable{

    Context c;
    ArrayList<Taxi> taxis;
    ArrayList<Taxi> taxisFiltered;
    private TaxiFilter taxiFilter;

    LayoutInflater inflater;
    public CustomTaxiAdapter(Context c, ArrayList<Taxi> taxis){
        this.c = c;
        this.taxis = taxis;
        this.taxisFiltered = taxis;

        inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
    }

    @Override
    public int getCount() {
        return taxisFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return taxisFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.taximodel,parent,false);
        }

        TextView nametxt = (TextView) convertView.findViewById(R.id.vehicleName);
        ImageView logo = (ImageView) convertView.findViewById(R.id.eventImage);

        Taxi taxi = taxis.get(position);
        nametxt.setText(taxi.getName());

        PicassoClient.downloadImage(c,taxi.getImageUrl(), "taxi" ,logo);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(taxiFilter == null){
            taxiFilter = new TaxiFilter();
        }
        return taxiFilter;
    }

    private class TaxiFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<Taxi> tempList = new ArrayList<Taxi>();

                tempList.clear();
                for (Taxi taxi : taxis){
                    if(taxi.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(taxi);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;

            }else{
                filterResults.count = taxis.size();
                filterResults.values = taxis;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            taxisFiltered = (ArrayList<Taxi>)results.values;
            notifyDataSetChanged();
        }
    }
}
