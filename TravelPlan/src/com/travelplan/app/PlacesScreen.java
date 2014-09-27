package com.travelplan.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlacesScreen extends Activity implements View.OnClickListener{
    TextView txtSelectedPlace;
    TextView txtSelectedPlaceDesc;
    TextView selectedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_screen);

        Button btnHome=(Button)findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);

        Button btnGetDirection=(Button)findViewById(R.id.btnGetDirection);
        btnGetDirection.setOnClickListener(this);

        Button btnTravelListScreen=(Button)findViewById(R.id.btnTravelList);
        btnTravelListScreen.setOnClickListener(this);

        txtSelectedPlace=(TextView) findViewById(R.id.txtSelectedPlace);
        txtSelectedPlaceDesc=(TextView)findViewById(R.id.txtSelectedPlaceDesc);
        selectedList=(TextView)findViewById(R.id.txtSelectedTravelList);

        try
        {
            Intent intent = getIntent();
            String selectedPlace = intent.getStringExtra("selectedPlace");
            String selectedPlaceDesc = intent.getStringExtra("Desc");
            txtSelectedPlace.setText(selectedPlace);
            txtSelectedPlaceDesc.setText(selectedPlaceDesc);
        }
        catch (Exception e)
        {
            Log.e("Error",e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.places_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.btnHome:
                btnHomeClicked();
                break;
            case R.id.btnGetDirection:
                btnGetDirectionClicked();
                break;
            case R.id.btnTravelList:
                btnAddTravelList();
                break;
        }
    }

    private void btnGetDirectionClicked() {
        startActivity(new Intent("com.travelplan.app.DirectionsScreen"));
    }

    private void btnHomeClicked() {
        startActivity(new Intent("com.travelplan.app.MainActivity"));
    }

    final ArrayList<String> list = new ArrayList<String>();
    ListView lstViewCreatedTravelLists;
    String selectedTravelList;

    private void btnAddTravelList()
    {
        lstViewCreatedTravelLists=new ListView(this);

        list.clear();

        loadFromFile();
        AlertDialog.Builder builder = new AlertDialog.Builder(PlacesScreen.this);


        final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        lstViewCreatedTravelLists.setAdapter(adapter);
        builder.setTitle("Choose a travel list")
               .setView(lstViewCreatedTravelLists);

        lstViewCreatedTravelLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Add the place to the selected list
                selectedTravelList=lstViewCreatedTravelLists.getItemAtPosition(i).toString();
                String[] splittedTravelList=selectedTravelList.split("-");
                selectedList.setText(splittedTravelList[0].trim().toString());
                addPlaceToSelectedTravelList(txtSelectedPlace.getText().toString(),selectedList.getText().toString());
                Toast.makeText(getApplicationContext(),"Place has been added to the list "+selectedList.getText().toString()+"!",Toast.LENGTH_SHORT).show();
                PlacesScreen.this.finish();
            }
        });
        final Dialog dialog=builder.create();
        dialog.show();
    }

    private void loadFromFile(){

        File sdcard = new File(Environment.getExternalStorageDirectory()+"/TravelPlan");
        File file = new File(sdcard,"/TravelLists.txt");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
                list.add(line);
            }
        }
        catch (IOException e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void addPlaceToSelectedTravelList(String place, String travelList) {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            Log.e("External Storage Status: -> ","OK! <-");

            File dir=new File(Environment.getExternalStorageDirectory()+"/TravelPlan");
            dir.mkdirs();
            File textFile = new File(dir+"/"+travelList+".txt");
            try {
                BufferedWriter buf = new BufferedWriter(new FileWriter(textFile, true));
                buf.append(place.toUpperCase());
                buf.newLine();
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Log.e("External Storage Status: -> ","Failed! <-");
        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
