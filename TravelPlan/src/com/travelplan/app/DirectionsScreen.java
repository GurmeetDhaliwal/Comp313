package com.travelplan.app;

import com.travelplan.app.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DirectionsScreen extends FragmentActivity implements View.OnClickListener
{
	public static FragmentManager fragmentManager;
	
	GoogleMap googleMap;
	TextView coordinates;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_screen);

        fragmentManager = getSupportFragmentManager();
        try 
        {
            // Loading map
            //initilizeMap();
        	
        	Intent i = getIntent();			
			// Place referece id
			String latitude = i.getStringExtra("directionLATITUDE");	
			String longitude = i.getStringExtra("directionLONGITUDE");	
			
			coordinates=(TextView)findViewById(R.id.textViewCoordinates);
			
			coordinates.setText("Latitude: "+latitude+" | Longitude: "+longitude);
			
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        Button btnHome=(Button)findViewById(R.id.btnHomeBack);
        btnHome.setOnClickListener(this);       

    }

    private void initilizeMap() 
    {
        if (googleMap == null) 
        {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null)
            {
                Toast.makeText(getApplicationContext(),"Sorry! unable to create maps",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*protected void onResume() 
    {
        super.onResume();
        initilizeMap();
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnHomeBack:
                btnHomeClicked();
                break;
        }
    }

    private void btnHomeClicked() {
        Intent i=new Intent(DirectionsScreen.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
