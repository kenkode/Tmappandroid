package com.upstridge.tmapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.upstridge.tmapp.hotel.rooms;
import com.upstridge.tmapp.models.Hotels;
import com.upstridge.tmapp.models.PicassoClient;
import com.upstridge.tmapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

/**
 * Created by Wango-PC on 5/6/2017.
 */

public class CustomHotelAdapter extends RecyclerView.Adapter<CustomHotelAdapter.ViewHolder> implements Filterable{

    Context c;
    ArrayList<Hotels> hotels;
    ArrayList<Hotels> hotelsFiltered;
    private HotelFilter hotelFilter;
    private String date;
    private String time;
    private String area;

    LayoutInflater inflater;
    public CustomHotelAdapter(Context c, ArrayList<Hotels> hotels, String date, String time, String area){
        this.c = c;
        this.hotels = hotels;
        this.hotelsFiltered = hotels;
        this.date = date;
        this.time = time;
        this.area = area;

        inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.hotelmodel, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Hotels hotel = hotels.get(position);
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        //holder.checkBox.setText("Checkbox "+position);
        holder.nametxt.setText(hotel.getName());
        holder.branch.setText(hotel.getBranch());
        holder.organization.setText(hotel.getOrganization());

        PicassoClient.downloadImage(c,BASE_URL + "public/uploads/logo/"+hotel.getImageUrl(), "hotel", holder.logo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicle =((TextView)view.findViewById(R.id.hotelName)).getText().toString();
                //arr =((TextView)view.findViewById(R.id.adults)).getText().toString().replace("Arrival : ","");
                //dep =((TextView)view.findViewById(R.id.children)).getText().toString().replace("Departure : ","");
                //String ecprice =((TextView)view.findViewById(R.id.economicfare)).getText().toString().replace("Economic Fare : KES ","");
                //String capacity =((TextView)view.findViewById(R.id.capacity)).getText().toString();
                //String organization =((TextView)view.findViewById(R.id.organization)).getText().toString();
                //String vid =((TextView)view.findViewById(R.id.vehicleid)).getText().toString();

                //Toast.makeText(c,arr+"-"+dep+"-"+vipprice+"-"+ecprice+"-"+capacity+"-"+type,Toast.LENGTH_LONG).show();

                Intent i = new Intent(c, rooms.class);
                Bundle b = new Bundle();
                b.putString("area", area);
                b.putString("date", date);
                b.putString("time", time);
                b.putString("hotel", hotel.getName());
                b.putString("organization", hotel.getOrganization());
                b.putString("branch", hotel.getBranch());
                b.putString("branchid", hotel.getBranchid());
                i.putExtras(b);
                c.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return hotelsFiltered.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nametxt;
        public TextView branch;
        public TextView organization;
        public View view;
        public ProgressBar progressBar;
        public ImageView logo;


        public ViewHolder(View convertView) {
            super(convertView);

            view = convertView;
            nametxt = (TextView) convertView.findViewById(R.id.hotelName);
            logo = (ImageView) convertView.findViewById(R.id.hotelImage);
            branch = (TextView) convertView.findViewById(R.id.branch);
            organization = (TextView) convertView.findViewById(R.id.organization);

        }
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
