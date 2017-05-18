package com.upstridge.tmapp;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	ArrayList<Item> gridArray = new ArrayList<Item>();
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
		totalSeat(63);

		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setNumColumns(6);
		customGridAdapter = new CustomGridViewAdapter(this, R.layout.seatrow_grid, gridArray);
		gridView.setAdapter(customGridAdapter);
		gridView.setOnItemClickListener(this);
	}

	public void totalSeat(int n)
	{

		gridArray.add(new Item(seatIcon, "seat 1" ));
		gridArray.add(new Item(seatIcon, "seat 2" ));
		gridArray.add(new Item(blankIcon,"" ));
		gridArray.add(new Item(blankIcon,"" ));
		gridArray.add(new Item(blankIcon,"" ));
		gridArray.add(new Item(driverIcon,"driver" ));

		int y = 0;
		int i = 3;
        int j = 0;

		for (i = 3; i <= n-6; ++i)
		{
			if(i < 5) {
				gridArray.add(new Item(seatIcon, "seat " + i));
			}else if (i % 6 == 5) {
				gridArray.add(new Item(blankIcon, ""));
				y = y+1;
			}else{
				gridArray.add(new Item(seatIcon, "seat " + (i-(i/6))));
			}
			//i = i+y;
		}

		Toast.makeText(SeatSelectionActivityNew.this,i+"\n"+y+"\n"+(i+y),Toast.LENGTH_LONG).show();
		for (j = i; j <= n-6+y; ++j)
		{
			if (j % 6 == 5) {
				gridArray.add(new Item(blankIcon, ""));
				y = y+1;
			}else{
				gridArray.add(new Item(seatIcon, "seat " + (j-(j/6))));
			}
		}

		gridArray.add(new Item(seatIcon, "seat " + (n-5)));
		gridArray.add(new Item(seatIcon, "seat " + (n-4)));
		gridArray.add(new Item(seatIcon, "seat " + (n-3)));
		gridArray.add(new Item(seatIcon, "seat " + (n-2)));
		gridArray.add(new Item(seatIcon, "seat " + (n-1)));
		gridArray.add(new Item(seatIcon, "seat " + (n)));
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
		}else if (seatcompare == blankIcon)
		{

		}
		else
		{
			seatDeselcted(position);

		}

	}

}
