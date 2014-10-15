package com.travelplan.app;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultsActivity extends Activity {

    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    AlertDialogManager alert = new AlertDialogManager();
    GooglePlacesNearbyRequest googlePlaces;
    PlacesList nearPlaces;
    GPSTracker gps;
    Button btnShowOnMap;
    ProgressDialog pDialog;
    ListView lvResults;
    SeekBar rangeModifier;
    TextView txtRange;
    int range=0;
    // Searched keyword value
    String value;
    
    ArrayList<HashMap<String, String>> lstPlaces = new ArrayList<HashMap<String,String>>();
	
    // KEY Strings
    public static String KEY_REFERENCE = "reference"; // id of the place
    public static String KEY_NAME = "name"; // name of the place
    public static String KEY_VICINITY = "vicinity"; // Place area name
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		
		// get data from previous intent		
		Intent i=getIntent();
		value = i.getExtras().getString("keyword");
		// set data to textview
		TextView resultsTxt= (TextView) findViewById(R.id.txtResults);	
		resultsTxt.setText("Searched Keyword: "+ value);
		
        lvResults = (ListView) findViewById(R.id.listSearchResults);
		txtRange=(TextView) findViewById(R.id.textViewRange);
		
		rangeModifier=(SeekBar) findViewById(R.id.seekBarRange);
		rangeModifier.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				new LoadPlaces().execute();
				txtRange.setText("Range: "+(range)*10000+" meters");
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				range=progress;	
				lstPlaces.clear();
				
			}
		});
		
		// check connection
		cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent==false)
        {
        	alert.showAlertDialog(SearchResultsActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
            return;
        }

        // check gps connection
        gps = new GPSTracker(this);        
        if (gps.canGetLocation()==true) 
        {
            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } 
        else 
        {
            alert.showAlertDialog(SearchResultsActivity.this, "GPS Status", "Couldn't get location information. Please enable GPS", false);
            return;
        }

        //new LoadPlaces().execute();
        
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String reference = ((TextView) view.findViewById(R.id.reference)).getText().toString();

                Intent i = new Intent(getApplicationContext(), SinglePlaceActivity.class);
                i.putExtra(KEY_REFERENCE, reference);
                startActivity(i);
            }
        });
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
	
	class LoadPlaces extends AsyncTask<String, String, String> 
	{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchResultsActivity.this);
            pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            //pDialog.show();
        }
        
        protected String doInBackground(String... args) {
            // creating Places class object
            googlePlaces = new GooglePlacesNearbyRequest();

            try 
            {
            	// place types to view relevant places 
                String types = 
                		"airport|amusement_park|aquarium|art_gallery|bar" +
                		"|cafe|casino|lodging|museum|night_club" +
                		"|park|restaurant|shopping_mall|zoo";

                // Radius in meters
                double radius = (range+1)*10000;

                // get nearest places
                nearPlaces = googlePlaces.search(gps.getLatitude(), gps.getLongitude(), radius, types);

                alert.showAlertDialog(SearchResultsActivity.this, "GPS Status",
                        "Couldn't get location information. Please enable GPS",
                        false);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        //pDialog.dismiss();
        // updating UI from Background Thread
        runOnUiThread(new Runnable() {
            public void run() {
                /**
                 * Updating parsed Places into LISTVIEW
                 * */
                // Get json response status
                String status = nearPlaces.status;

                // Check for all possible status
                if(status.equals("OK")){
                    // Successfully got places details
                    if (nearPlaces.results != null) {
                        // loop through each place
                        for (Place p : nearPlaces.results) {
                        	if(p.name.toUpperCase().contains(value.toUpperCase()))
                        	{
	                            HashMap<String, String> map = new HashMap<String, String>();
	                            // Place reference won't display in listview - it will be hidden
	                            // Place reference is used to get "place full details"
	                            map.put(KEY_REFERENCE, p.reference);	
	                            // Place name
	                            map.put(KEY_NAME, p.name.toUpperCase());
	                            // adding HashMap to ArrayList
	                            lstPlaces.add(map);
                        	}
                        }
                        // list adapter
                        ListAdapter adapter = new SimpleAdapter(SearchResultsActivity.this, lstPlaces,
                                R.layout.list_item,
                                new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
                                R.id.reference, R.id.name });

                        // Adding data into listview
                        lvResults.setAdapter(adapter);
                    }
                }
                else if(status.equals("ZERO_RESULTS")){
                    // Zero results found
                    alert.showAlertDialog(SearchResultsActivity.this, "Near Places",
                            "Sorry no places found. Try to change the types of places",
                            false);
                }
                else if(status.equals("UNKNOWN_ERROR"))
                {
                    alert.showAlertDialog(SearchResultsActivity.this, "Places Error",
                            "Sorry unknown error occured.",
                            false);
                }
                else if(status.equals("OVER_QUERY_LIMIT"))
                {
                    alert.showAlertDialog(SearchResultsActivity.this, "Places Error",
                            "Sorry query limit to google places is reached",
                            false);
                }
                else if(status.equals("REQUEST_DENIED"))
                {
                    alert.showAlertDialog(SearchResultsActivity.this, "Places Error",
                            "Sorry error occured. Request is denied",
                            false);
                }
                else if(status.equals("INVALID_REQUEST"))
                {
                    alert.showAlertDialog(SearchResultsActivity.this, "Places Error",
                            "Sorry error occured. Invalid Request",
                            false);
                }
                else
                {
                    alert.showAlertDialog(SearchResultsActivity.this, "Places Error",
                            "Sorry error occured.",
                            false);
                }
            }
        });

    }}
    /*public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_search_screen, container, false);
            return rootView;
        }
    }*/
}
