package com.upstridge.tmapp.bus;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Wango-PC on 5/5/2017.
 */

public class CustomVehicleListView extends ArrayAdapter<String> {

    String[] name;
    String[] image;
    public CustomVehicleListView(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
}
