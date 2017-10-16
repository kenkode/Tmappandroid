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

import com.upstridge.tmapp.airline.SeatSelectionActivityNew;
import com.upstridge.tmapp.models.Aeroplanes;
import com.upstridge.tmapp.models.PicassoClient;
import com.upstridge.tmapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

/**
 * Created by Wango-PC on 5/6/2017.
 */

public class CustomAeroplaneAdapter extends RecyclerView.Adapter<CustomAeroplaneAdapter.ViewHolder> implements Filterable{

    Context c;
    ArrayList<Aeroplanes> aeroplanes;
    ArrayList<Aeroplanes> aeroplanesFiltered;
    private AeroplaneFilter aeroplaneFilter;
    String destination;
    String date;
    String time;
    String origin;

    LayoutInflater inflater;
    public CustomAeroplaneAdapter(Context c, ArrayList<Aeroplanes> aeroplanes, String destination, String date, String time, String origin){
        this.c = c;
        this.aeroplanes = aeroplanes;
        this.aeroplanesFiltered = aeroplanes;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.origin = origin;

        inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.airplane_model, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        final Aeroplanes aeroplane = aeroplanes.get(position);
        holder.nametxt.setText(aeroplane.getName());
        holder.route.setText(aeroplane.getOname()+" to "+aeroplane.getDname());
        //arrival.setText(vehicle.getArrival());
        //departure.setText(vehicle.getDeparture());
        //price.setText(vehicle.getPrice());
        holder.vip.setText("Vip Fare : KES "+formatter.format(Double.parseDouble(aeroplane.getVipprice())));
        if(!aeroplane.getBusinessfare().equals("null")) {
            holder.business.setText("Business Fare : KES " + formatter.format(Double.parseDouble(aeroplane.getBusinessfare())));
        }else{
            holder.business.setText("N/A");
        }
        holder.economic.setText( "Economic Fare : KES "+formatter.format(Double.parseDouble(aeroplane.getEconomicfare())));
        holder.children.setText("Children Fare "+aeroplane.getChildrenfare() +"% of selected fare");
        holder.organization.setText(aeroplane.getOrganization());
        holder.vehicleid.setText(aeroplane.getVehicleid());
        holder.firstclassapply.setText(aeroplane.getFirstclassapply());
        //holder.type.setText(aeroplane.getType());
        holder.capacity.setText(aeroplane.getCapacity());

        PicassoClient.downloadImage(c,BASE_URL + "public/uploads/logo/"+aeroplane.getImageUrl(), "aeroplane", holder.logo);
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
                String children =((TextView)view.findViewById(R.id.childrenfare)).getText().toString().replace("Children Fare(%) : ","");

                //Toast.makeText(c,arr+"-"+dep+"-"+vipprice+"-"+ecprice+"-"+capacity+"-"+type,Toast.LENGTH_LONG).show();

                Intent i = new Intent(c, SeatSelectionActivityNew.class);
                Bundle b = new Bundle();
                b.putString("destination", destination);
                b.putString("date", date);
                b.putString("time", time);
                b.putString("vehicle", vehicle);
                b.putString("origin",origin);
                b.putString("arrival", aeroplane.getArrival());
                b.putString("departure", aeroplane.getDeparture());
                b.putString("vip", vipprice);
                b.putString("business", aeroplane.getBusinessfare());
                b.putString("economic", ecprice);
                b.putString("children", children);
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
        return aeroplanesFiltered.size();
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
            route = (TextView) convertView.findViewById(R.id.roomtype);
            //TextView arrival = (TextView) convertView.findViewById(R.id.adults);
            //TextView departure = (TextView) convertView.findViewById(R.id.children);
            //TextView price = (TextView) convertView.findViewById(R.id.price);
            business = (TextView) convertView.findViewById(R.id.businessfare);
            children = (TextView) convertView.findViewById(R.id.childrenfare);
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
        if(aeroplaneFilter == null){
            aeroplaneFilter = new AeroplaneFilter();
        }
        return aeroplaneFilter;
    }

    private class AeroplaneFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<Aeroplanes> tempList = new ArrayList<Aeroplanes>();

                tempList.clear();
                for (Aeroplanes aeroplane : aeroplanes){
                    if(aeroplane.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(aeroplane);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;

            }else{
                filterResults.count = aeroplanes.size();
                filterResults.values = aeroplanes;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            aeroplanesFiltered = (ArrayList<Aeroplanes>)results.values;
            notifyDataSetChanged();
        }
    }
}
