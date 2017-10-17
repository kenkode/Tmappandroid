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

import com.upstridge.tmapp.hotel.Customers;
import com.upstridge.tmapp.models.Room;
import com.upstridge.tmapp.models.PicassoClient;
import com.upstridge.tmapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

/**
 * Created by Wango-PC on 5/6/2017.
 */

public class CustomRoomAdapter extends RecyclerView.Adapter<CustomRoomAdapter.ViewHolder> implements Filterable{

    Context c;
    String date;
    String time;
    String branchid;
    ArrayList<Room> rooms;
    ArrayList<Room> roomsFiltered;
    private VehicleFilter roomFilter;

    LayoutInflater inflater;
    public CustomRoomAdapter(Context c, ArrayList<Room> rooms, String date, String time, String branchid){
        this.c = c;
        this.rooms = rooms;
        this.roomsFiltered = rooms;
        this.date = date;
        this.time = time;
        this.branchid = branchid;
        inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.roommodel, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Room room = rooms.get(position);
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        //Toast.makeText(c,room.getTue(),Toast.LENGTH_SHORT).show();

        String hotelprice = "";
        String price = "";
        //holder.checkBox.setText("Checkbox "+position);
        holder.nametxt.setText(room.getName());
        holder.adults.setText("Number of adults : "+room.getAdults());
        holder.children.setText("Number of children : "+room.getChildren());
        holder.availabilty.setText("Rooms available : "+room.getAvailability());
        if(day == Calendar.SUNDAY){
            if(room.getSun() == null){
                hotelprice = "Price : Undefined";
                price = room.getSun();
            }else {
                hotelprice = room.getSun();
                price = room.getSun();
            }
        }else if(day == Calendar.MONDAY){
            if(room.getMon() == null){
                hotelprice = "Price : Undefined";
                price = room.getMon();
            }else {
                hotelprice = room.getMon();
                price = room.getMon();
            }
        }else if(day == Calendar.TUESDAY){
            if(room.getTue() == null){
                hotelprice = "Price : Undefined";
                price = room.getTue();
            }else {
                hotelprice = room.getTue();
                price = room.getTue();
            }
        }else if(day == Calendar.WEDNESDAY){
            if(room.getWen() == null){
                hotelprice = "Price : Undefined";
                price = room.getWen();
            }else {
                hotelprice = room.getWen();
                price = room.getWen();
            }
        }else if(day == Calendar.THURSDAY){
            if(room.getThur() == null){
                hotelprice = "Price : Undefined";
                price = room.getThur();
            }else {
                hotelprice = room.getThur();
                price = room.getThur();
            }
        }else if(day == Calendar.FRIDAY){
            if(room.getFri() == null){
                hotelprice = "Price : Undefined";
                price = room.getFri();
            }else {
                hotelprice = room.getFri();
                price = room.getFri();
            }
        }else if(day == Calendar.SATURDAY){
            if(room.getSat() == null){
                hotelprice = "Price : Undefined";
                price = room.getSat();
            }else {
                hotelprice = room.getSat();
                price = room.getSat();
            }
        }
        if(hotelprice.equals("Price : Undefined")){
            holder.price.setText("Price : Undefined");
        }else {
            holder.price.setText("Price : KES " + formatter.format(Double.parseDouble(hotelprice)));
        }
        holder.roomid.setText(room.getRoomid());

        PicassoClient.downloadImage(c,BASE_URL + "public/uploads/logo/"+room.getImageUrl(), "room", holder.logo);

        final String finalHotelprice = hotelprice;
        final String finalPrice = price;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String vehicle =((TextView)view.findViewById(R.id.hotelName)).getText().toString();
                //arr =((TextView)view.findViewById(R.id.adults)).getText().toString().replace("Arrival : ","");
                //dep =((TextView)view.findViewById(R.id.children)).getText().toString().replace("Departure : ","");
                //String ecprice =((TextView)view.findViewById(R.id.economicfare)).getText().toString().replace("Economic Fare : KES ","");
                //String capacity =((TextView)view.findViewById(R.id.capacity)).getText().toString();
                //String organization =((TextView)view.findViewById(R.id.organization)).getText().toString();
                //String vid =((TextView)view.findViewById(R.id.vehicleid)).getText().toString();

                //Toast.makeText(c,arr+"-"+dep+"-"+vipprice+"-"+ecprice+"-"+capacity+"-"+type,Toast.LENGTH_LONG).show();
                if(finalHotelprice.equals("Price : Undefined")){
                    Toast.makeText(c,"This room can`t be selected because its price hasn`t been set",Toast.LENGTH_LONG).show();
                }else {
                    Intent i = new Intent(c, Customers.class);
                    Bundle b = new Bundle();
                    b.putString("branch", branchid);
                    b.putString("date", date);
                    b.putString("time", time);
                    b.putString("type", room.getName());
                    b.putString("organization", room.getOrganization());
                    b.putString("adults", room.getAdults());
                    b.putString("children", room.getChildren());
                    b.putString("capacity", room.getAvailability());
                    b.putString("price", finalPrice);
                    b.putString("roomid", room.getRoomid());
                    b.putString("roomtype", room.getName());
                    i.putExtras(b);
                    c.startActivity(i);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return roomsFiltered.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nametxt;
        public TextView branch;
        public TextView adults;
        public TextView children;
        public TextView availabilty;
        public TextView price;
        public TextView roomid;
        public View view;
        public ProgressBar progressBar;
        public ImageView logo;


        public ViewHolder(View convertView) {
            super(convertView);

            view = convertView;
            nametxt = (TextView) convertView.findViewById(R.id.roomName);
            logo = (ImageView) convertView.findViewById(R.id.roomImage);
            adults = (TextView) convertView.findViewById(R.id.adults);
            children = (TextView) convertView.findViewById(R.id.children);
            availabilty = (TextView) convertView.findViewById(R.id.availability);
            price = (TextView) convertView.findViewById(R.id.price);
            roomid = (TextView) convertView.findViewById(R.id.roomid);

        }
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
