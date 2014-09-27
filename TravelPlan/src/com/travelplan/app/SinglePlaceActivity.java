package com.travelplan.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.regex.Pattern;

public class SinglePlaceActivity extends Activity {
	// flag for Internet connection status
	Boolean isInternetPresent = false;

	// Connection detector class
	ConnectionDetector cd;
	
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	// Google Places
	GooglePlaces googlePlaces;
	
	// Place Details
	PlaceDetails placeDetails;
	
	// Progress dialog
	ProgressDialog pDialog;
	
	// KEY Strings
	public static String KEY_REFERENCE = "reference"; // id of the place

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_place);
		
		Intent i = getIntent();
		
		// Place referece id
		String reference = i.getStringExtra(KEY_REFERENCE);
		
		// Calling a Async Background thread
		new LoadSinglePlaceDetails().execute(reference);
	}
	
	
	/**
	 * Background Async Task to Load Google places
	 * */
	class LoadSinglePlaceDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SinglePlaceActivity.this);
			pDialog.setMessage("Loading profile ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Profile JSON
		 * */
		protected String doInBackground(String... args) {
			String reference = args[0];
			
			// creating Places class object
			googlePlaces = new GooglePlaces();

			// Check if used is connected to Internet
			try {
				placeDetails = googlePlaces.getPlaceDetails(reference);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed Places into LISTVIEW
					 * */
					if(placeDetails != null){
						String status = placeDetails.status;
						
						// check place deatils status
						// Check for all possible status
						if(status.equals("OK")) if (placeDetails.result != null) {
                            String name = placeDetails.result.name;
                            String address = placeDetails.result.formatted_address;
                            String phone = placeDetails.result.formatted_phone_number;
                            String latitude = Double.toString(placeDetails.result.geometry.location.lat);
                            String longitude = Double.toString(placeDetails.result.geometry.location.lng);
                            String image_url = placeDetails.result.icon;
                            String website = placeDetails.result.website;


                            if (placeDetails.result.rating == null) {
                                placeDetails.result.rating = 0.0;
                            } else {
                                String rating = Double.toString(placeDetails.result.rating);
                                TextView lbl_rating = (TextView) findViewById(R.id.rating);
                                lbl_rating.setText(Html.fromHtml("<b>Rating:</b> " + rating));
                                int roundRating = (int)Math.round(placeDetails.result.rating);


                                RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
                                ratingBar.setNumStars(roundRating);

                                ratingBar.setFocusable(false);

                            }

                            int loader = R.drawable.loader;
                            Log.d("Place ", name + address + phone + latitude + longitude + website);

                            // Displaying all the details in the view
                            // single_place.xml
                            ImageView image = (ImageView) findViewById(R.id.imageView);
                            ImageLoader imgLoader = new ImageLoader(getApplicationContext());
                            TextView lbl_name = (TextView) findViewById(R.id.name);
                            TextView lbl_address = (TextView) findViewById(R.id.address);
                            TextView lbl_phone = (TextView) findViewById(R.id.phone);
                            TextView lbl_location = (TextView) findViewById(R.id.location);
                            TextView lbl_website = (TextView) findViewById(R.id.website);


                            RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
                            ratingBar.setRating(5);
                            ratingBar.setFocusable(false);
                            ratingBar.setClickable(false);
                                    // Check for null data from google
                                    // Sometimes place details might missing
                                    name = name == null ? "Not present" : name; // if name is null display as "Not present"
                            address = address == null ? "Not present" : address;
                            phone = phone == null ? "Not present" : phone;
                            latitude = latitude == null ? "Not present" : latitude;
                            longitude = longitude == null ? "Not present" : longitude;
                            website = website == null ? "Not Present" : website;

                            imgLoader.DisplayImage(image_url, loader, image);
                            lbl_name.setText(name);
                            lbl_address.setText(address);
                            lbl_phone.setText(Html.fromHtml("<b>Phone:</b> " + phone));
                            lbl_location.setText(Html.fromHtml("<b>Latitude:</b> " + latitude + ", <b>Longitude:</b> " + longitude));


                            lbl_website.setText(Html.fromHtml("<br>Website:</b> " + website));

                        }
						else if(status.equals("ZERO_RESULTS")){
							alert.showAlertDialog(SinglePlaceActivity.this, "Near Places",
									"Sorry no place found.",
									false);
						}
						else if(status.equals("UNKNOWN_ERROR"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry unknown error occured.",
									false);
						}
						else if(status.equals("OVER_QUERY_LIMIT"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry query limit to google places is reached",
									false);
						}
						else if(status.equals("REQUEST_DENIED"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry error occured. Request is denied",
									false);
						}
						else if(status.equals("INVALID_REQUEST"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry error occured. Invalid Request",
									false);
						}
						else
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry error occured.",
									false);
						}
					}else{
						alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
								"Sorry error occured.",
								false);
					}
					
					
				}
			});

		}

	}

}
