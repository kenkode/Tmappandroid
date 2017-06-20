package com.upstridge.tmapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

/**
 * 
 * @author Saurabh tomar
 * 
 */

public class SeatSelectionActivityNew extends Activity implements OnItemClickListener
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
		blankIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.ras1);

		seatIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_tab_nor_avl);
		seatSelect = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_screen_nor_std);

		Bundle bundle = getIntent().getExtras();
		final String vehicle = bundle.getString("vid");
		final String vehiclename = bundle.getString("vehicle");
		final String destination = bundle.getString("destination");
		final String date = bundle.getString("date");
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
		if(type.equals("Large Bus")){
			gridView.setNumColumns(6);
		}else if(type.equals("Minibus")){
			gridView.setNumColumns(5);
		}else if(type.equals("Shuttle")){
			gridView.setNumColumns(3);
		}else if(type.equals("Matatu")){
			gridView.setNumColumns(3);
		}else if(type.equals("Large Matatu")){
			gridView.setNumColumns(4);
		}

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
					 b.putString("date", date);
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
		if(type.equals("Large Bus")){
			gridArray.add(new Item(seatIcon, "seat 1" ));
			gridArray.add(new Item(seatIcon, "seat 2" ));
			gridArray.add(new Item(blankIcon,"" ));
			gridArray.add(new Item(blankIcon,"" ));
			gridArray.add(new Item(blankIcon,"" ));
			gridArray.add(new Item(driverIcon,"driver" ));
		}else if(type.equals("Minibus")){
			gridArray.add(new Item(seatIcon, "seat 1" ));
			gridArray.add(new Item(seatIcon, "seat 2" ));
			gridArray.add(new Item(blankIcon,"" ));
			gridArray.add(new Item(blankIcon,"" ));
			gridArray.add(new Item(driverIcon,"driver" ));
		}else if(type.equals("Shuttle")){
			gridArray.add(new Item(seatIcon, "seat 1" ));
			gridArray.add(new Item(seatIcon, "seat 2" ));
			gridArray.add(new Item(driverIcon,"driver" ));
		}else if(type.equals("Matatu")){
			gridArray.add(new Item(seatIcon, "seat 1" ));
			gridArray.add(new Item(seatIcon, "seat 2" ));
			gridArray.add(new Item(driverIcon,"driver" ));
		}else if(type.equals("Large Matatu")){
			gridArray.add(new Item(seatIcon, "seat 1" ));
			gridArray.add(new Item(seatIcon, "seat 2" ));
			gridArray.add(new Item(blankIcon,"" ));
			gridArray.add(new Item(driverIcon,"driver" ));
		}



		int y = 0;
		int i = 3;
        int j = 0;
		int col = 0;
		int z = 0;

		if(type.equals("Large Bus")){
			z = z + 2;
		}else if(type.equals("Minibus")){
			z = z + 2;
		}

		if(type.equals("Large Bus")){
			col = col + 6;
		}else if(type.equals("Minibus")){
			col = col + 5;
		}else if(type.equals("Shuttle")){
			col = col + 3;
		}else if(type.equals("Matatu")){
			col = col + 3;
		}else if(type.equals("Large Matatu")){
			col = col + 4;
		}

		for (i = 3; i <= n-col; ++i) {
			if (i < 5) {
				gridArray.add(new Item(seatIcon, "seat " + i));
			}else{
				if (type.equals("Large Bus")) {
					if (i % 6 == 5) {
						gridArray.add(new Item(blankIcon, ""));
						y = y + 1;
					}else if(i == 9 || i == 10){
                        gridArray.add(new Item(blankIcon, ""));
                    } else {
						if(i < 9){
							gridArray.add(new Item(seatIcon, "seat " + (i - (i / 6))));
						}else {
							gridArray.add(new Item(seatIcon, "seat " + (i - 2 - (i / 6))));
						}
					}
				}else if(type.equals("Minibus")){
					if (i % 5 == 0) {
						gridArray.add(new Item(blankIcon, ""));
						y = y + 1;
					}else if(i == 8 || i == 9){
						gridArray.add(new Item(blankIcon, ""));
					} else {
						if (i < 8) {
							gridArray.add(new Item(seatIcon, "seat " + (i - (i / 5))));
						}else{
							gridArray.add(new Item(seatIcon, "seat " + (i - 2 - (i / 5))));
						}
					}
				}else if(type.equals("Shuttle") || type.equals("Matatu") || type.equals("Large Matatu")){
						gridArray.add(new Item(seatIcon, "seat " + i));
				}
			}
		}

		for (j = i; j <= n-col+y+z; ++j)
		{
			if (type.equals("Large Bus")) {
				if (j % 6 == 5) {
					gridArray.add(new Item(blankIcon, ""));
					y = y + 1;
				} else {
					gridArray.add(new Item(seatIcon, "seat " + (j - (j / 6))));
				}
			}else if(type.equals("Minibus")){
				if (j % 5 == 0) {
					gridArray.add(new Item(blankIcon, ""));
					y = y + 1;
				} else {
					gridArray.add(new Item(seatIcon, "seat " + (j - (j / 5))));
				}
			}
		}


		if(type.equals("Large Bus")){
			gridArray.add(new Item(seatIcon, "seat " + (n-5)));
			gridArray.add(new Item(seatIcon, "seat " + (n-4)));
			gridArray.add(new Item(seatIcon, "seat " + (n-3)));
			gridArray.add(new Item(seatIcon, "seat " + (n-2)));
			gridArray.add(new Item(seatIcon, "seat " + (n-1)));
			gridArray.add(new Item(seatIcon, "seat " + (n)));
		}else if(type.equals("Minibus")){
			gridArray.add(new Item(seatIcon, "seat " + (n-4)));
			gridArray.add(new Item(seatIcon, "seat " + (n-3)));
			gridArray.add(new Item(seatIcon, "seat " + (n-2)));
			gridArray.add(new Item(seatIcon, "seat " + (n-1)));
			gridArray.add(new Item(seatIcon, "seat " + (n)));
		}else if(type.equals("Shuttle") || type.equals("Matatu")){
			gridArray.add(new Item(seatIcon, "seat " + (n-2)));
			gridArray.add(new Item(seatIcon, "seat " + (n-1)));
			gridArray.add(new Item(seatIcon, "seat " + (n)));
		}else if(type.equals("Large Matatu")){
			gridArray.add(new Item(seatIcon, "seat " + (n-3)));
			gridArray.add(new Item(seatIcon, "seat " + (n-2)));
			gridArray.add(new Item(seatIcon, "seat " + (n-1)));
			gridArray.add(new Item(seatIcon, "seat " + (n)));
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
