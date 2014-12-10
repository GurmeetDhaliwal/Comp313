package com.travelplan.app;

import com.travelplan.app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
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
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DirectionsScreen extends Activity
{
	//public static FragmentManager fragmentManager;

	GoogleMap gMap;
	TextView coordinates;
	TextView txtMyLoc;
	public String latitude;
	public String longitude;
	String name;
	public Marker mark;
	
	Location location;
	LatLng myLocation;
	Location location2;
	
	GPSTracker gps;
	
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
			coordinates.setText(" Place Name: "+name+"\n  Latitude: "+latitude+"  |  Longitude: "+longitude+"");
			txtMyLoc=(TextView) findViewById(R.id.txtMyLocation);
			
			gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();
			
			if (gMap!=null){
	              mark = gMap.addMarker(new MarkerOptions()
	              		.position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
	              		.title(name)
	              		.snippet(name)
	              		.icon(BitmapDescriptorFactory.defaultMarker())
	              		);	   
	              
	              gMap.setOnMapLongClickListener(new OnMapLongClickListener() {
					
					@Override
					public void onMapLongClick(LatLng arg0) {
						// TODO Auto-generated method stub
						if(Math.abs(mark.getPosition().latitude - gps.getLatitude()) < 0.05 && Math.abs(mark.getPosition().longitude - gps.getLatitude()) < 0.05) {
	                        Toast.makeText(getApplicationContext(), "got clicked", Toast.LENGTH_SHORT).show();
	                    }
					}
				});
	            }
	         gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)), 16));
        	         
	         gMap.setMyLocationEnabled(true);
	         location=gMap.getMyLocation();         
	         
	         if (location != null) {
	             myLocation = new LatLng(location.getLatitude(),location.getLongitude());
	         }
	         gps = new GPSTracker(this);
				txtMyLoc.setText(" Current Coordinations:\n  Latitude: "+ gps.getLatitude() +"  |  "+"Longitude: "+ gps.getLongitude()+"");
	         gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));
  
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.directions_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.homeBtn) {
	        Intent i=new Intent(DirectionsScreen.this,MainActivity.class);
	        startActivity(i);
	        finish();
		}
		else if (id == R.id.getLocation) {
			//txtMyLoc.setText(" Current Coordinations:\n Lat: "+ gps.getLatitude() +" | "+"Lng: "+ gps.getLongitude());
			gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(),gps.getLongitude()), 18));
            
			Marker myMarker = gMap.addMarker(new MarkerOptions()
      		.position(new LatLng(gps.getLatitude(), gps.getLongitude()))
      		.title("My Location")
      		.snippet(gps.getLatitude()+ " " + gps.getLongitude())
      		.icon(BitmapDescriptorFactory.defaultMarker())
      		);	
			 //location.setLatitude(Double.parseDouble(latitude));
			 //location.setLongitude(Double.parseDouble(longitude));
	         //location2.setLatitude(gps.getLatitude());
	         //location2.setLongitude(gps.getLongitude());
	         //Log.e("DISTANCE", "Distance "+location.distanceTo(location2));
	         //Toast.makeText(getApplicationContext(),"Distance between - "+location.distanceTo(location2), Toast.LENGTH_SHORT).show();
			 //Location.distanceBetween(gps.getLatitude(), gps.getLongitude(), Double.parseDouble(latitude), Double.parseDouble(longitude), results);
			 Log.e("LAT-LNG", Double.parseDouble(latitude)+" - "+Double.parseDouble(longitude));
			 Log.e("MyLatLng",gps.latitude+" - "+gps.longitude);
			 Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
					 Uri.parse("http://maps.google.com/maps?saddr="+gps.latitude+","+gps.longitude+"&daddr="+Double.parseDouble(latitude)+","+Double.parseDouble(longitude)+""));
					 startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
