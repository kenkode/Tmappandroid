package com.upstridge.tmapp.events;

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
 * Created by Wango-PC on 6/8/2017.
 */

public class CustomEventAdapter extends BaseAdapter implements Filterable {

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
    public int getCount() {
        return eventsFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return eventsFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.eventmodel,parent,false);
        }

        TextView nametxt = (TextView) convertView.findViewById(R.id.eventName);
        ImageView logo = (ImageView) convertView.findViewById(R.id.hotelImage);
        TextView description = (TextView) convertView.findViewById(R.id.roomtype);
        TextView location = (TextView) convertView.findViewById(R.id.adults);
        TextView contact = (TextView) convertView.findViewById(R.id.contact);
        //TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView vip = (TextView) convertView.findViewById(R.id.vip);
        TextView economic = (TextView) convertView.findViewById(R.id.economic);
        TextView children = (TextView) convertView.findViewById(R.id.children);
        TextView organization = (TextView) convertView.findViewById(R.id.organization);
        TextView eventid = (TextView) convertView.findViewById(R.id.eventid);
        TextView slots = (TextView) convertView.findViewById(R.id.slots);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        Event event = events.get(position);
        nametxt.setText(event.getName());
        description.setText(event.getDescription());
        contact.setText(event.getContact());
        slots.setText(event.getSlots());
        location.setText(event.getAddress());
        //price.setText(vehicle.getPrice());
        vip.setText(event.getVipprice());
        economic.setText(event.getEconomic());
        children.setText(event.getChildren());
        organization.setText(event.getOrganizationId());
        eventid.setText(event.getEventid());
        date.setText(event.getDate());

        PicassoClient.downloadImage(c,event.getImageUrl(), "event", logo);

        return convertView;
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