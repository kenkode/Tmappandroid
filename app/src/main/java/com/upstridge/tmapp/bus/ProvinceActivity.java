package com.upstridge.tmapp.bus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.upstridge.tmapp.R;

import java.util.ArrayList;

public class ProvinceActivity extends Activity {
    ArrayList<String> provinceList;
    SearchView searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        final ListView pList=(ListView)findViewById(R.id.provinceList);
        searchBar = (SearchView)findViewById(R.id.searchBar);

        provinceList = new ArrayList<String>();
        getProvinceNames();
        // Create The Adapter with passing ArrayList as 3rd parameter
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, provinceList){

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view =super.getView(position, convertView, parent);

                        TextView textView=(TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                        textView.setTextColor(Color.WHITE);
                        textView.setTextSize(20);


                        return view;
                    }
                };
        pList.setAdapter(arrayAdapter);

        searchBar.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        pList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),OriginActivity.class).putExtra("province", provinceList.get(position)));
            }
        });
    }
    void getProvinceNames() {
        provinceList.add("Nairobi");
        provinceList.add("Central");
        provinceList.add("Coast");
        provinceList.add("Western");
        provinceList.add("Nyanza");
        provinceList.add("Eastern");
        provinceList.add("Rift-Valley");
        provinceList.add("North-Eastern");
    }

}
