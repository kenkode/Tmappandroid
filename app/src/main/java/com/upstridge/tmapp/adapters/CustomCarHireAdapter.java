package com.upstridge.tmapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.models.Carhire;
import com.upstridge.tmapp.models.PicassoClient;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

/**
 * Created by root on 10/3/17.
 */

public class CustomCarHireAdapter extends BaseAdapter {
    private Context context;
    public static ArrayList<Carhire> cars;


    public CustomCarHireAdapter(Context context, ArrayList<Carhire> cars) {

        this.context = context;
        this.cars = cars;

    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return cars.size();
    }

    @Override
    public Object getItem(int position) {
        return cars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.carmodel, null, true);



            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.tvId = (TextView) convertView.findViewById(R.id.carid);
            holder.tvType = (TextView) convertView.findViewById(R.id.cartype);
            holder.tvCapacity = (TextView) convertView.findViewById(R.id.capacity);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.price);
            holder.tvOrg = (TextView) convertView.findViewById(R.id.organization);



            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }


        final Carhire car = cars.get(position);
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        String amt = formatter.format(car.getPrice());
        //holder.checkBox.setText("Checkbox "+position);
        holder.tvType.setText("Type : "+car.getType());
        holder.tvCapacity.setText("Capacity : "+car.getCapacity());
        holder.tvPrice.setText("Price /day :\nKES "+amt);
        holder.tvOrg.setText(car.getOrganization_id());
        holder.tvId.setText(car.getId());
        ImageView logo = (ImageView) convertView.findViewById(R.id.hotelImage);

        PicassoClient.downloadImage(context,BASE_URL + "public/uploads/logo/"+car.getImage(), "car", logo);

        holder.checkBox.setChecked(cars.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
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

        return convertView;
    }

    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView tvId;
        private TextView tvPrice;
        private TextView tvType;
        private TextView tvOrg;
        private TextView tvCapacity;
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

