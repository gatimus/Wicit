package com.natebass.wicit;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MainActivity extends ActionBarActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {
    public static final String BASE_URL = "http://cdph.data.ca.gov/resource/i7wi-ei4m.json";
    public static final String SOCRATA_APP_TOKEN = "1DpALfWSiKZLlyC4IPBnGszwN";
    
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        
        buildGoogleApiClient();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng defaultLocation = new LatLng(38.5556, -121.4689);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12));

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
                fetchData(bounds);
            }
        });
        
        mMap = map;
/*        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(defaultLocation));*/
    }

    private void fetchData(LatLngBounds bounds) {
        String locationsUrl = BASE_URL + "?$where=within_box(location," +
                bounds.southwest.longitude + "," + bounds.southwest.latitude + "," +
                bounds.northeast.latitude + "," + bounds.northeast.longitude + ")";
        locationsUrl += "&$$app_token=" + SOCRATA_APP_TOKEN;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // todo: Prompt user to enable "High Accuracy" GPS & Wifi Location if lastLocation == null
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LatLng currentLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
    }

    @Override
    public void onConnectionSuspended(int i) {
    }
}
