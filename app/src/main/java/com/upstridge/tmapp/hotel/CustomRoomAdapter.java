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

public class CustomRoomAdapter extends BaseAdapter implements Filterable{

    Context c;
    ArrayList<Room> rooms;
    ArrayList<Room> roomsFiltered;
    private VehicleFilter roomFilter;

    LayoutInflater inflater;
    public CustomRoomAdapter(Context c, ArrayList<Room> rooms){
        this.c = c;
        this.rooms = rooms;
        this.roomsFiltered = rooms;

        inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
    }

    @Override
    public int getCount() {
        return roomsFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return roomsFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.roommodel,parent,false);
        }

        TextView nametxt = (TextView) convertView.findViewById(R.id.roomName);
        ImageView logo = (ImageView) convertView.findViewById(R.id.roomImage);
        TextView adults = (TextView) convertView.findViewById(R.id.adults);
        TextView children = (TextView) convertView.findViewById(R.id.children);
        TextView availabilty = (TextView) convertView.findViewById(R.id.availability);
        TextView price = (TextView) convertView.findViewById(R.id.price);

        Room room = rooms.get(position);
        nametxt.setText(room.getName());
        adults.setText(room.getAdults());
        children.setText(room.getChildren());
        availabilty.setText(room.getAvailability());
        price.setText(room.getPrice());

        PicassoClient.downloadImage(c,room.getImageUrl(), "room", logo);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(roomFilter == null){
            roomFilter = new VehicleFilter();
        }
        return roomFilter;
    }

    private class VehicleFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<Room> tempList = new ArrayList<Room>();

                tempList.clear();
                for (Room room : rooms){
                    if(room.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(room);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;

            }else{
                filterResults.count = rooms.size();
                filterResults.values = rooms;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            roomsFiltered = (ArrayList<Room>)results.values;
            notifyDataSetChanged();
        }
    }
}
