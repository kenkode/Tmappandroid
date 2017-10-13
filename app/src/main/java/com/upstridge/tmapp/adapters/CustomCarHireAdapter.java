package com.upstridge.tmapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.models.Carhire;
import com.upstridge.tmapp.models.PicassoClient;

import org.joda.time.DateTime;
import org.joda.time.Months;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

/**
 * Created by root on 10/3/17.
 */

public class CustomCarHireAdapter extends RecyclerView.Adapter<CustomCarHireAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    public static ArrayList<Carhire> cars;


    public CustomCarHireAdapter(Context context, ArrayList<Carhire> cars) {

        this.context = context;
        this.cars = cars;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.carmodel, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Carhire car = cars.get(position);
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        String amt = formatter.format(car.getPrice());
        //holder.checkBox.setText("Checkbox "+position);
        holder.tvType.setText("Type : "+car.getType());
        holder.tvCapacity.setText("Capacity : "+car.getCapacity());
        holder.tvPrice.setText("Price /day :\nKES "+amt);
        holder.tvOrg.setText(car.getOrganization_id());
        holder.tvId.setText(car.getId());


        PicassoClient.downloadImage(context,BASE_URL + "public/uploads/logo/"+car.getImage(), "car", holder.logo);

        holder.checkBox.setChecked(cars.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, holder.view);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                Integer pos = (Integer)  holder.checkBox.getTag();
                //Toast.makeText(context, "Checkbox "+pos+" clicked!", Toast.LENGTH_SHORT).show();

                if(cars.get(pos).getSelected()){
                    cars.get(pos).setSelected(false);
                }else {
                    cars.get(pos).setSelected(true);
                }

            }
        });
    }

    public void refreshEvents(ArrayList<Carhire> c) {
        this.cars.clear();
        this.cars.addAll(c);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CheckBox checkBox;
        public TextView tvId;
        public TextView tvPrice;
        public TextView tvType;
        public TextView tvOrg;
        public TextView tvCapacity;
        public View view;
        public ProgressBar progressBar;
        public ImageView logo;


        public ViewHolder(View convertView) {
            super(convertView);

            view = convertView;
            checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            tvId = (TextView) convertView.findViewById(R.id.carid);
            tvType = (TextView) convertView.findViewById(R.id.cartype);
            tvCapacity = (TextView) convertView.findViewById(R.id.capacity);
            tvPrice = (TextView) convertView.findViewById(R.id.price);
            tvOrg = (TextView) convertView.findViewById(R.id.organization);
            progressBar= (ProgressBar) convertView.findViewById(R.id.load_cars);
            logo = (ImageView) convertView.findViewById(R.id.hotelImage);
        }
    }

    public static ArrayList<Carhire> getModel(){
        ArrayList<Carhire> list = new ArrayList<>();
        for(int i = 0; i < cars.size(); i++){

            Carhire car = new Carhire();
            if(cars.get(i).getSelected() == false ) {
                list.add(car);
            }
        }
        return list;
    }

    public static int carCount(){
        return cars.size();
    }
}
