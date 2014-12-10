package com.travelplan.app;


import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.model.people.Person;

public class MainActivity extends Activity implements View.OnClickListener,ConnectionCallbacks, OnConnectionFailedListener{

	TextView searchText;  
    AutoCompleteTextView autoComp;
    AlertDialogManager alert = new AlertDialogManager();

    

    private static final int RC_SIGN_IN = 0;
 // Logcat tag
 private static final String TAG = "MainActivity";

 // Profile pic image size in pixels
 private static final int PROFILE_PIC_SIZE = 400;

 // Google client to interact with Google API
 private GoogleApiClient mGoogleApiClient;

 /**
 * A flag indicating that a PendingIntent is in progress and prevents us
 * from starting further intents.
 */
 private boolean mIntentInProgress;

 private boolean mSignInClicked;

 private ConnectionResult mConnectionResult;

 private SignInButton btnSignIn;
 private Button btnSignOut;
 private ImageView imgProfilePic;
 private TextView txtName, txtEmail, txtConnected;
 private LinearLayout llProfileLayout;
 
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCreateTrip= (Button) findViewById(R.id.btnCreateTrip);
        btnCreateTrip.setOnClickListener(this);

        Button btnSearch=(Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        Button btnManualNavigation=(Button)findViewById(R.id.btnManualNavigation);
        btnManualNavigation.setOnClickListener(this);

        Button btnSearchNearby=(Button)findViewById(R.id.search);
        btnSearchNearby.setOnClickListener(this);



        Button btnTravelList=(Button)findViewById(R.id.btnTravelList);
        btnTravelList.setOnClickListener(this);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);

        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        btnSignOut.setOnClickListener(this);
		imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
		txtName = (TextView) findViewById(R.id.txtName);
		txtEmail = (TextView) findViewById(R.id.txtEmail);
		txtConnected=(TextView)findViewById(R.id.txtIsConnected);
		llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);

        
        
        mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API)
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();
        
        autoComp = (AutoCompleteTextView)findViewById(R.id.autoCompTxtSearch);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, places);
        //autoComp.setAdapter(adapter);

        autoComp.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView <?> parent, View arg1, int pos,long id) {
                //placeSelectedFromString=true;
            }
        });

        autoComp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //placeSelectedFromString=false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

protected void onStart() {
super.onStart();
mGoogleApiClient.connect();
}

protected void onStop() {
super.onStop();
if (mGoogleApiClient.isConnected()) {
	mGoogleApiClient.disconnect();
}
}
private void resolveSignInError() {
if (mConnectionResult.hasResolution()) {
	try {
		mIntentInProgress = true;
		mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
	} catch (SendIntentException e) {
		mIntentInProgress = false;
		mGoogleApiClient.connect();
	}
}
}

@Override
public void onConnectionFailed(ConnectionResult result) {
if (!result.hasResolution()) {
	GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
			0).show();
	return;
}

if (!mIntentInProgress) {
	// Store the ConnectionResult for later usage
	mConnectionResult = result;

	if (mSignInClicked) {
		// The user has already clicked 'sign-in' so we attempt to
		// resolve all
		// errors until the user is signed in, or they cancel.
		resolveSignInError();
	}
}
	txtConnected.setText(R.string.gPlusSignedFail);
}

@Override
protected void onActivityResult(int requestCode, int responseCode,
	Intent intent) {
if (requestCode == RC_SIGN_IN) {
	if (responseCode != RESULT_OK) {
		mSignInClicked = false;
	}

	mIntentInProgress = false;

	if (!mGoogleApiClient.isConnecting()) {
		mGoogleApiClient.connect();
	}
}
}

@Override
public void onConnected(Bundle arg0) {
mSignInClicked = false;
Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

// Get user's information
getProfileInformation();

// Update the UI after signin
updateUI(true);
txtConnected.setText(R.string.gPlusSignedSuccess);
}
private void updateUI(boolean isSignedIn) {
if (isSignedIn) {
	btnSignIn.setVisibility(View.GONE);
	btnSignOut.setVisibility(View.VISIBLE);
	llProfileLayout.setVisibility(View.VISIBLE);
} else {
	btnSignIn.setVisibility(View.VISIBLE);
	btnSignOut.setVisibility(View.GONE);
	llProfileLayout.setVisibility(View.GONE);
}
}

/**
* Fetching user's information name, email, profile pic
* */
private void getProfileInformation() {
try {
	if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
		Person currentPerson = Plus.PeopleApi
				.getCurrentPerson(mGoogleApiClient);
		String personName = currentPerson.getDisplayName();
		String personPhotoUrl = currentPerson.getImage().getUrl();
		String personGooglePlusProfile = currentPerson.getUrl();
		String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

		Log.e(TAG, "Name: " + personName + ", plusProfile: "
				+ personGooglePlusProfile + ", email: " + email
				+ ", Image: " + personPhotoUrl);

		txtName.setText(personName);
		txtEmail.setText(email);

		// by default the profile url gives 50x50 px image only
		// we can replace the value with whatever dimension we want by
		// replacing sz=X
		personPhotoUrl = personPhotoUrl.substring(0,
				personPhotoUrl.length() - 2)
				+ PROFILE_PIC_SIZE;

		new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

	} else {
		Toast.makeText(getApplicationContext(),
				"Person information is null", Toast.LENGTH_LONG).show();
	}
} catch (Exception e) {
	e.printStackTrace();
}
}

@Override
public void onConnectionSuspended(int arg0) {
mGoogleApiClient.connect();
updateUI(false);
}

private void signInWithGplus() {
if (!mGoogleApiClient.isConnecting()) {
	mSignInClicked = true;
	resolveSignInError();
}
}

/**
* Sign-out from google
* */
private void signOutFromGplus() {
	if (mGoogleApiClient.isConnected()) {
        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
        Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status arg0) {
                        Log.e(TAG, "User access revoked!");
                        mGoogleApiClient.connect();
                        updateUI(false);
                    }
 
                });
    }
}


private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
ImageView bmImage;

public LoadProfileImage(ImageView bmImage) {
	this.bmImage = bmImage;
}

protected Bitmap doInBackground(String... urls) {
	String urldisplay = urls[0];
	Bitmap mIcon11 = null;
	try {
		InputStream in = new java.net.URL(urldisplay).openStream();
		mIcon11 = BitmapFactory.decodeStream(in);
	} catch (Exception e) {
		Log.e("Error", e.getMessage());
		e.printStackTrace();
	}
	return mIcon11;
}
protected void onPostExecute(Bitmap result) {
	bmImage.setImageBitmap(result);
}
}

@Override
public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnSearch:

                btnSearchClicked();

                break;
            case R.id.btnCreateTrip:
                btnCreateTripClicked();
                break;
            case R.id.btnManualNavigation:
                btnManualNavigationClicked();
                break;
            case R.id.btnTravelList:
                btnTravelListClicked();
                break;
            case R.id.search:
                btnSearchNearbyClicked();
                break;
            case R.id.btn_sign_in:
            	signInWithGplus();
                break;
            case R.id.btn_sign_out:
            	// Signout button clicked
            	signOutFromGplus();
            	break;
        }

    }


    private void btnTravelListClicked() {
        startActivity(new Intent("com.travelplan.app.TravelList"));
    }

    private void btnManualNavigationClicked() {
        startActivity(new Intent("com.travelplan.app.ManualNavigationScreen"));

    }

    private void btnCreateTripClicked() {
        startActivity(new Intent("com.travelplan.app.CreateTripScreen"));

    }
    private void btnSearchNearbyClicked() {
        startActivity(new Intent("com.travelplan.app.NearBySearch"));
    }
    private void btnSearchClicked(){
        if(autoComp.length()>0)
        {
	    	String value="";
	    	value=autoComp.getText().toString();
	    	Intent i= new Intent(this, SearchResultsActivity.class);
	    	i.putExtra("keyword", value.toUpperCase());
	    	startActivity(i); 
        }
        else
        {
        	Toast.makeText(getApplicationContext(), "Please enter a keyword", Toast.LENGTH_SHORT).show();
        }
        
        /*text = textQuery.getText().toString();
        if(textQuery.length()==0)
        {
            alert.showAlertDialog(this, "Input Error",
                    "Please enter a name of the place.",
                    false);
        } 
        else if (placeSelectedFromString==true)
        {
            try
            {
                Intent i=new Intent(autoComp.getContext(),PlacesScreen.class);
                i.putExtra("selectedPlace",autoComp.getText().toString());
                ///////// Description will be added
                i.putExtra("Desc",getResources().getString(R.string.desc_noinfo));

                i.setType("text/plain");
                startActivity(i);
            }
            catch (Exception e)
            {
                Log.e("Error ->>>", e.toString());
            }
        }
        else if(placeSelectedFromString==false)
        {
            Intent i = new Intent(getApplicationContext(), SearchResultsActivity.class);
            i.putExtra("KEYWORD", autoComp.getText());
            Toast.makeText(getApplicationContext(), autoComp.getText(), Toast.LENGTH_SHORT).show();
            startActivity(i);
        }*/

    }

}