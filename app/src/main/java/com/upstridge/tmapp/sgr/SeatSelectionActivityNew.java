package com.upstridge.tmapp.sgr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.upstridge.tmapp.BookingActivity;
import com.upstridge.tmapp.CustomGridViewAdapter;
import com.upstridge.tmapp.Item;
import com.upstridge.tmapp.R;

import java.util.ArrayList;

/**
 * Created by Wango-PC on 6/8/2017.
 */

public class SeatSelectionActivityNew extends Activity implements AdapterView.OnItemClickListener
{
    GridView gridView;
    Button done;
    String type;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    ArrayList<String> selected = new ArrayList<String>();
    CustomGridViewAdapter customGridAdapter;
    public Bitmap seatIcon,driverIcon,blankIcon;
    public Bitmap seatSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats);

        driverIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.steering_icon);
        blankIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.blank);

        seatIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_tab_nor_avl);
        seatSelect = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_screen_nor_std);

        Bundle bundle = getIntent().getExtras();
        final String vehicle = bundle.getString("vid");
        final String vehiclename = bundle.getString("vehicle");
        final String destination = bundle.getString("destination");
        //final String date = bundle.getString("date");
        final String time = bundle.getString("time");
        final String origin = bundle.getString("origin");
        final String arrival = bundle.getString("arrival");
        final String departure = bundle.getString("departure");
        final String vip = bundle.getString("vip");
        final String economic = bundle.getString("economic");
        final String firstclassapply = bundle.getString("firstclassapply");
        type = bundle.getString("type");
        final String capacity = bundle.getString("capacity");
        final String organization = bundle.getString("organization");

        totalSeat(Integer.parseInt(capacity));

        done = (Button)findViewById(R.id.doneButton);
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setNumColumns(4);
        customGridAdapter = new CustomGridViewAdapter(this, R.layout.seatrow_grid, gridArray);
        gridView.setAdapter(customGridAdapter);
        gridView.setOnItemClickListener(this);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selected.size() == 0){
                    Toast.makeText(SeatSelectionActivityNew.this,"Please select atleast one seat!",Toast.LENGTH_SHORT).show();
                }else {

                    Intent i = new Intent(getApplicationContext(), BookingActivity.class);
                    Bundle b = new Bundle();
                    b.putString("destination", destination);
                    //b.putString("date", date);
                    b.putString("time", time);
                    b.putString("vehicle", vehiclename);
                    b.putString("origin", origin);
                    b.putString("arrival", arrival);
                    b.putString("departure", departure);
                    b.putString("vip", vip);
                    b.putString("economic", economic);
                    b.putString("type", type);
                    b.putString("capacity", capacity);
                    b.putString("organization", organization);
                    b.putString("vid", vehicle);
                    b.putString("firstclassapply", firstclassapply);
                    b.putStringArrayList("seats", selected);
                    i.putExtras(b);
                    startActivity(i);
                }
            }
        });
    }

    public void totalSeat(int n)
    {
        for (int i = 1; i <= n; ++i)
        {
            gridArray.add(new Item(seatIcon, "seat " + i));

        }

    }

    public void seatSelected(int pos)
    {
        gridArray.remove(pos);
        gridArray.add(pos, new Item(seatSelect, "select"));

        customGridAdapter.notifyDataSetChanged();
    }

    public void seatDeselcted(int pos)
    {

        gridArray.remove(pos);
        int i = pos + 1;
        gridArray.add(pos, new Item(seatIcon, "seat" + i));
        customGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        Item item = gridArray.get(position);
        Bitmap seatcompare = item.getImage();
        if (seatcompare == seatIcon)
        {
            seatSelected(position);
            selected.add(item.getTitle());
        }else if (seatcompare == blankIcon)
        {

        }
        else
        {
            seatDeselcted(position);

        }

    }

}