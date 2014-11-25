package com.travelplan.app;

import com.travelplan.app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DirectionsScreen extends Activity implements View.OnClickListener
{
	//public static FragmentManager fragmentManager;

	GoogleMap gMap;
	TextView coordinates;
	String latitude;
	String longitude;
	String name;
	
	Location location;
	LatLng myLocation;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_screen);
        
        //fragmentManager = getSupportFragmentManager();
        
        try 
        {        	
        	Intent i = getIntent();			
			// Place referece id
			latitude = i.getStringExtra("directionLATITUDE");	
			longitude = i.getStringExtra("directionLONGITUDE");	
			name=i.getStringExtra("directionNAME");
			coordinates=(TextView)findViewById(R.id.textViewCoordinates);			
			coordinates.setText("  Place Name: "+name+"\n  Latitude: "+latitude+" | Longitude: "+longitude);
			
			
			gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();
			
			if (gMap!=null){
	              Marker mark = gMap.addMarker(new MarkerOptions()
	              		.position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
	              		.title(name)
	              		.snippet(name)
	              		.icon(BitmapDescriptorFactory.defaultMarker())
	              		);	           
	            }
	         gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)), 16));
        	         
	         gMap.setMyLocationEnabled(true);
	         location=gMap.getMyLocation();
	         
	         if (location != null) {
	             myLocation = new LatLng(location.getLatitude(),location.getLongitude());
	         }
	         gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));
	         
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        Button btnHome=(Button)findViewById(R.id.btnHomeBack);
        btnHome.setOnClickListener(this);
    }
    
    
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_results, menu);
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
}
