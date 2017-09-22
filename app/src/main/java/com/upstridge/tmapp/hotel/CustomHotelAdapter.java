package com.upstridge.tmapp.hotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.upstridge.tmapp.PicassoClient;
import com.upstridge.tmapp.R;

import java.util.ArrayList;

/**
 * Created by Wango-PC on 5/6/2017.
 */

public class CustomHotelAdapter extends BaseAdapter implements Filterable{

    Context c;
    ArrayList<Hotels> hotels;
    ArrayList<Hotels> hotelsFiltered;
    private HotelFilter hotelFilter;

    LayoutInflater inflater;
    public CustomHotelAdapter(Context c, ArrayList<Hotels> hotels){
        this.c = c;
        this.hotels = hotels;
        this.hotelsFiltered = hotels;

        inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
    }

    @Override
    public int getCount() {
        return hotelsFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return hotelsFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.hotelmodel,parent,false);
        }

        TextView nametxt = (TextView) convertView.findViewById(R.id.hotelName);
        ImageView logo = (ImageView) convertView.findViewById(R.id.hotelImage);
         TextView branch = (TextView) convertView.findViewById(R.id.branch);
        TextView organization = (TextView) convertView.findViewById(R.id.organization);

        Hotels hotel = hotels.get(position);
        nametxt.setText(hotel.getName());
        branch.setText(hotel.getBranch());
        organization.setText(hotel.getOrganization());

        PicassoClient.downloadImage(c,hotel.getImageUrl(), "hotel", logo);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(hotelFilter == null){
            hotelFilter = new HotelFilter();
        }
        return hotelFilter;
    }

    private class HotelFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<Hotels> tempList = new ArrayList<Hotels>();

                tempList.clear();
                for (Hotels hotel : hotels){
                    if(hotel.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(hotel);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;

            }else{
                filterResults.count = hotels.size();
                filterResults.values = hotels;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            hotelsFiltered = (ArrayList<Hotels>)results.values;
            notifyDataSetChanged();
        }
    }
}
