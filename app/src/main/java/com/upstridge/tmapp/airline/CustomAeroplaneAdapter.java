package com.upstridge.tmapp.airline;

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

public class CustomAeroplaneAdapter extends BaseAdapter implements Filterable{

    Context c;
    ArrayList<Aeroplanes> aeroplanes;
    ArrayList<Aeroplanes> aeroplanesFiltered;
    private AeroplaneFilter aeroplaneFilter;

    LayoutInflater inflater;
    public CustomAeroplaneAdapter(Context c, ArrayList<Aeroplanes> aeroplanes){
        this.c = c;
        this.aeroplanes = aeroplanes;
        this.aeroplanesFiltered = aeroplanes;

        inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
    }

    @Override
    public int getCount() {
        return aeroplanesFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return aeroplanesFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.airplane_model,parent,false);
        }

        TextView nametxt = (TextView) convertView.findViewById(R.id.hotelName);
        ImageView logo = (ImageView) convertView.findViewById(R.id.hotelImage);
        TextView route = (TextView) convertView.findViewById(R.id.description);
        TextView arrival = (TextView) convertView.findViewById(R.id.location);
        TextView departure = (TextView) convertView.findViewById(R.id.contact);
        //TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView vip = (TextView) convertView.findViewById(R.id.vipfare);
        TextView business = (TextView) convertView.findViewById(R.id.businessfare);
        TextView economic = (TextView) convertView.findViewById(R.id.economicfare);
        TextView children = (TextView) convertView.findViewById(R.id.childrenfare);
        TextView organization = (TextView) convertView.findViewById(R.id.organization);
        TextView vehicleid = (TextView) convertView.findViewById(R.id.vehicleid);
        TextView firstclassapply = (TextView) convertView.findViewById(R.id.firstclassapply);

        Aeroplanes aeroplane = aeroplanes.get(position);
        nametxt.setText(aeroplane.getName());
        route.setText(aeroplane.getRoute());
        arrival.setText(aeroplane.getArrival());
        departure.setText(aeroplane.getDeparture());
        //price.setText(vehicle.getPrice());
        vip.setText(aeroplane.getVipprice());
        business.setText(aeroplane.getBusinessfare());
        economic.setText(aeroplane.getEconomicfare());
        children.setText(aeroplane.getChildrenfare());
        organization.setText(aeroplane.getOrganization());
        vehicleid.setText(aeroplane.getVehicleid());
        firstclassapply.setText(aeroplane.getFirstclassapply());

        PicassoClient.downloadImage(c,aeroplane.getImageUrl(), "aeroplane", logo);

        return convertView;
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
