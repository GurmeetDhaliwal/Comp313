package com.travelplan.app;


import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

	TextView searchText;  
    AutoCompleteTextView autoComp;
    AlertDialogManager alert = new AlertDialogManager();

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
        String value="";
    	value=autoComp.getText().toString();
    	Intent i = new Intent(this, SearchResultsActivity.class);
    	i.putExtra("keyword", value.toUpperCase());
    	startActivity(i); 
        
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
