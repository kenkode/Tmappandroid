package com.upstridge.tmapp.bus;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.upstridge.tmapp.R;

public class SeatsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats);
        seatLayout();
    }

    private void seatLayout(){
        RelativeLayout seats = (RelativeLayout)findViewById(R.id.seats);

        final ImageButton[] imageButtons;

        final int NUMBER_OF_IMAGE_BUTTONS = 5; //your number of image buttons

        imageButtons = new ImageButton[NUMBER_OF_IMAGE_BUTTONS];

        for(int i = 0 ; i < NUMBER_OF_IMAGE_BUTTONS; i++){
            imageButtons[i] = new ImageButton(this);
            imageButtons[i].setImageResource(R.drawable.seat_layout_tab_nor_avl);

            imageButtons[i].setBackgroundColor(Color.TRANSPARENT);
            imageButtons[i].setTag(i);
            imageButtons[i].setId(i);
            seats.addView(imageButtons[i]);
        }
    }

}
