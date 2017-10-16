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

import com.upstridge.tmapp.events.NumberOfClients;
import com.upstridge.tmapp.models.Event;
import com.upstridge.tmapp.models.PicassoClient;
import com.upstridge.tmapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.upstridge.tmapp.R.drawable.aeroplane;
import static com.upstridge.tmapp.config.Constants.BASE_URL;

/**
 * Created by Wango-PC on 6/8/2017.
 */

public class CustomEventAdapter extends RecyclerView.Adapter<CustomEventAdapter.ViewHolder> implements Filterable {

    Context c;
    ArrayList<Event> events;
    ArrayList<Event> eventsFiltered;
    private EventFilter eventFilter;

    LayoutInflater inflater;
    public CustomEventAdapter(Context c, ArrayList<Event> events){
        this.c = c;
        this.events = events;
        this.eventsFiltered = events;

        inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.eventmodel, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        final Event event = events.get(position);
        holder.nametxt.setText(event.getName());
        //arrival.setText(vehicle.getArrival());
        //departure.setText(vehicle.getDeparture());
        //price.setText(vehicle.getPrice());
        holder.vip.setText("Vip Fare : KES "+formatter.format(Double.parseDouble(event.getVipprice())));
        holder.description.setText("Description:\n"+event.getDescription());
        holder.economic.setText( "Economic Fare : KES "+formatter.format(Double.parseDouble(event.getEconomic())));
        holder.children.setText("Children Fare : KES "+formatter.format(Double.parseDouble(event.getChildren())));
        holder.organization.setText(event.getOrganizationId());
        holder.eventid.setText(event.getEventid());
        holder.location.setText(event.getAddress());
        holder.date.setText("Date : "+event.getDate());

        PicassoClient.downloadImage(c,BASE_URL + "public/uploads/logo/"+event.getImageUrl(), "event", holder.logo);
        //Toast.makeText(c,aeroplane.getVipprice(),Toast.LENGTH_SHORT).show();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String organization =((TextView)view.findViewById(R.id.organization)).getText().toString();
                String eventid =((TextView)view.findViewById(R.id.eventid)).getText().toString();
                String vipprice =((TextView)view.findViewById(R.id.vip)).getText().toString().replace("Vip Entrance : KES ","");
                String ecprice =((TextView)view.findViewById(R.id.economic)).getText().toString().replace("Normal Entrance : KES ","");
                //slots =((TextView)view.findViewById(R.id.slots)).getText().toString().replace("Remaining Slots : ","");
                String children =((TextView)view.findViewById(R.id.children)).getText().toString().replace("Children Entrance : KES ","");

                //Toast.makeText(c,arr+"-"+dep+"-"+vipprice+"-"+ecprice+"-"+capacity+"-"+type,Toast.LENGTH_LONG).show();

                Intent i = new Intent(c, NumberOfClients.class);
                Bundle b = new Bundle();
                b.putString("vip", vipprice);
                b.putString("economic", ecprice);
                b.putString("children",children);
                b.putString("slots",event.getSlots());
                b.putString("organization", organization);
                b.putString("eventid", eventid);
                i.putExtras(b);
                c.startActivity(i);
            }
        });

        //PicassoClient.downloadImage(c,BASE_URL + "public/uploads/logo/"+vehicle.getImageUrl(), "bus", holder.logo);


    }

    @Override
    public int getItemCount() {
        return eventsFiltered.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nametxt;
        public TextView economic;
        public TextView description;
        public TextView children;
        public TextView organization;
        public TextView location;
        public TextView vip;
        public TextView eventid;
        public TextView date;
        public View view;
        public ProgressBar progressBar;
        public ImageView logo;


        public ViewHolder(View convertView) {
            super(convertView);

            view = convertView;
            nametxt = (TextView) convertView.findViewById(R.id.eventName);
            logo = (ImageView) convertView.findViewById(R.id.hotelImage);
            description = (TextView) convertView.findViewById(R.id.roomtype);
            location = (TextView) convertView.findViewById(R.id.adults);
            //TextView contact = (TextView) convertView.findViewById(R.id.contact);
            date = (TextView) convertView.findViewById(R.id.date);
            vip = (TextView) convertView.findViewById(R.id.vip);
            economic = (TextView) convertView.findViewById(R.id.economic);
            children = (TextView) convertView.findViewById(R.id.children);
            organization = (TextView) convertView.findViewById(R.id.organization);
            eventid = (TextView) convertView.findViewById(R.id.eventid);
            //TextView slots = (TextView) convertView.findViewById(R.id.slots);

            progressBar= (ProgressBar) convertView.findViewById(R.id.load_cars);
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if(eventFilter == null){
            eventFilter = new EventFilter();
        }
        return eventFilter;
    }

    private class EventFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<Event> tempList = new ArrayList<Event>();

                tempList.clear();
                for (Event event : events){
                    if(event.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(event);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;

            }else{
                filterResults.count = events.size();
                filterResults.values = events;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            eventsFiltered = (ArrayList<Event>)results.values;
            notifyDataSetChanged();
        }
    }
}