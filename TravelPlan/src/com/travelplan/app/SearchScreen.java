package com.travelplan.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SearchScreen extends Activity {


    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Google Places
    GooglePlaces googlePlaces;

    // Places List
    PlacesList nearPlaces;

    // GPS Location
    GPSTracker gps;

    // Button
    Button btnShowOnMap;

    // Progress dialog
    ProgressDialog pDialog;

    // Places Listview
    ListView lv;

    // ListItems data
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();

    // KEY Strings
    public static String KEY_REFERENCE = "reference"; // id of the place
    public static String KEY_NAME = "name"; // name of the place
    public static String KEY_VICINITY = "vicinity"; // Place area name



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);






        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent==false

                ) {
            // Internet Connection is not present
            alert.showAlertDialog(SearchScreen.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        // creating GPS Class object
        gps = new GPSTracker(this);

        // check if GPS location can get
        if (gps.canGetLocation()==true) {
            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } else {
            // Can't get user's current location
            alert.showAlertDialog(SearchScreen.this, "GPS Status",
                    "Couldn't get location information. Please enable GPS",
                    false);
            // stop executing code by return
            return;
        }
        // Getting listview
        lv = (ListView) findViewById(R.id.list);

        // button show on map
        //btnShowOnMap = (Button) findViewById(R.id.btn_show_map);

        // calling background Async task to load Google Places
        // After getting places from Google all the data is shown in listview
        new LoadPlaces().execute();

        /** Button click event for shown on map */
        /*btnShowOnMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(),
                        PlacesMapActivity.class);
                // Sending user current geo location
                i.putExtra("user_latitude", Double.toString(gps.getLatitude()));
                i.putExtra("user_longitude", Double.toString(gps.getLongitude()));

                // passing near places to map activity
                i.putExtra("near_places", nearPlaces);
                // staring activity
                startActivity(i);
            }
        });*/


        /**
         * ListItem click event
         * On selecting a listitem SinglePlaceActivity is launched
         * */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String reference = ((TextView) view.findViewById(R.id.reference)).getText().toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        SinglePlaceActivity.class);

                // Sending place refrence id to single place activity
                // place refrence id used to get "Place full details"
                in.putExtra(KEY_REFERENCE, reference);
                startActivity(in);
            }
        });
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_screen, menu);
        return true;
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    class LoadPlaces extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchScreen.this);
            pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            //pDialog.show();
        }

        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            // creating Places class object
            googlePlaces = new GooglePlaces();

            try {
                // Separeate your place types by PIPE symbol "|"
                // If you want all types places make it as null
                // Check list of types supported by google
                //
                String types = "hindu_temple | airport | museum | stadium | aquarium | art_gallery | casino | city_hall |point_of_interest"; // Listing places only cafes, restaurants

                // Radius in meters
                double radius = 150000; // 150,000 meters
                Intent intent = getIntent();
                String query = intent.getExtras().getString("text");


                // get nearest places
                nearPlaces = googlePlaces.search(gps.getLatitude(),
                        gps.getLongitude(), radius, types,query);



                alert.showAlertDialog(SearchScreen.this, "GPS Status",
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
                            HashMap<String, String> map = new HashMap<String, String>();

                            // Place reference won't display in listview - it will be hidden
                            // Place reference is used to get "place full details"
                            map.put(KEY_REFERENCE, p.reference);

                            // Place name
                            map.put(KEY_NAME, p.name);


                            // adding HashMap to ArrayList
                            placesListItems.add(map);
                        }
                        // list adapter
                        ListAdapter adapter = new SimpleAdapter(SearchScreen.this, placesListItems,
                                R.layout.list_item,
                                new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
                                R.id.reference, R.id.name });

                        // Adding data into listview
                        lv.setAdapter(adapter);
                    }
                }
                else if(status.equals("ZERO_RESULTS")){
                    // Zero results found
                    alert.showAlertDialog(SearchScreen.this, "Near Places",
                            "Sorry no places found. Try to change the types of places",
                            false);
                }
                else if(status.equals("UNKNOWN_ERROR"))
                {
                    alert.showAlertDialog(SearchScreen.this, "Places Error",
                            "Sorry unknown error occured.",
                            false);
                }
                else if(status.equals("OVER_QUERY_LIMIT"))
                {
                    alert.showAlertDialog(SearchScreen.this, "Places Error",
                            "Sorry query limit to google places is reached",
                            false);
                }
                else if(status.equals("REQUEST_DENIED"))
                {
                    alert.showAlertDialog(SearchScreen.this, "Places Error",
                            "Sorry error occured. Request is denied",
                            false);
                }
                else if(status.equals("INVALID_REQUEST"))
                {
                    alert.showAlertDialog(SearchScreen.this, "Places Error",
                            "Sorry error occured. Invalid Request",
                            false);
                }
                else
                {
                    alert.showAlertDialog(SearchScreen.this, "Places Error",
                            "Sorry error occured.",
                            false);
                }
            }
        });

    }}
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_search_screen, container, false);
            return rootView;
        }
    }

}
