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

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.models.PicassoClient;
import com.upstridge.tmapp.models.Taxi;
import com.upstridge.tmapp.taxi.PeriodActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

/**
 * Created by Wango-PC on 5/6/2017.
 */

public class CustomTaxiAdapter extends RecyclerView.Adapter<CustomTaxiAdapter.ViewHolder> implements Filterable{

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
    public CustomTaxiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.taximodel, null);
        return new CustomTaxiAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final CustomTaxiAdapter.ViewHolder holder, int position) {

        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        final Taxi taxi = taxis.get(position);
        holder.nametxt.setText(taxi.getName());
        taxi.getCapacity();

        PicassoClient.downloadImage(c,BASE_URL + "public/uploads/logo/"+taxi.getImageUrl(), "taxi", holder.logo);
        //Toast.makeText(c,vehicle.getVipprice(),Toast.LENGTH_SHORT).show();

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

                Intent i = new Intent(c, PeriodActivity.class);
                Bundle b = new Bundle();
                b.putString("vehicle", vehicle);
                b.putString("economic", taxi.getEconomicfare());
                b.putString("capacity",taxi.getCapacity());
                b.putString("organization", taxi.getOrganization());
                b.putString("vid", taxi.getVehicleid());
                i.putExtras(b);
                c.startActivity(i);
            }
        });

        //PicassoClient.downloadImage(c,BASE_URL + "public/uploads/logo/"+vehicle.getImageUrl(), "bus", holder.logo);


    }

    @Override
    public int getItemCount() {
        return taxisFiltered.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nametxt;
        public View view;
        public ProgressBar progressBar;
        public ImageView logo;


        public ViewHolder(View convertView) {
            super(convertView);

            view = convertView;
            nametxt = (TextView) convertView.findViewById(R.id.hotelName);

            //progressBar= (ProgressBar) convertView.findViewById(R.id.load_cars);
            logo = (ImageView) convertView.findViewById(R.id.hotelImage);
        }
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
