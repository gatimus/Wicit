package com.natebass.wicit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.natebass.wicit.model.WicVendorData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by nwb on 2/16/15.
 */
public class WhoAcceptsWicFragment extends SupportMapFragment implements OnMapReadyCallback {
    private LatLng mPosFija;
    private GoogleMap mMap;

    public WhoAcceptsWicFragment() {
        super();
    }

    public static WhoAcceptsWicFragment newInstance(LatLng position) {
        WhoAcceptsWicFragment frag = new WhoAcceptsWicFragment();
        frag.mPosFija = position;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        getMapAsync(this);
        return super.onCreateView(arg0, arg1, arg2);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        final double SACRAMENTO_CA_LAT = 38.5556;
        final double SACRAMENTO_CA_LNG = -121.4689;
        final int DEFAULT_ZOOM = 12;
        mMap = map;
        LatLng defaultLocation = new LatLng(SACRAMENTO_CA_LAT, SACRAMENTO_CA_LNG);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
                // todo: Make sure the default loads first, this is currently overriding it
                new FetchData().execute(bounds);
            }
        });
    }

    class FetchData extends AsyncTask<LatLngBounds, Void, Boolean> {
        // todo: Limit the number of requests, if the user moves like crazy. Maybe cache something
        public static final String BASE_URL = "http://cdph.data.ca.gov/resource/i7wi-ei4m.json";
        public static final String SOCRATA_APP_TOKEN = "1DpALfWSiKZLlyC4IPBnGszwN";
        private WicVendorData[] wicVendors;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(LatLngBounds... bounds) {
            try {
                String locationsUrl = "";
                // todo: This loop is misleading because I only intend to pass in one LatLongBounds
                for (LatLngBounds bound : bounds) {
                    locationsUrl = BASE_URL + "?$where=within_box(location," +
                            bound.northeast.latitude + "," + bound.southwest.longitude + "," +
                            bound.southwest.latitude + "," + bound.northeast.longitude + ")";
                }
                locationsUrl += "&$$app_token=" + SOCRATA_APP_TOKEN;
                HttpGet httpGet = new HttpGet(locationsUrl);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpGet);
                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Gson gson = new Gson();
                    wicVendors = gson.fromJson(data, WicVendorData[].class);
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            for (WicVendorData wicVendor : wicVendors) {
                mMap.addMarker(new MarkerOptions().title(wicVendor.getVendor())
                        .snippet(wicVendor.getAddress())
                        .position(new LatLng(Double.parseDouble(wicVendor.getLocation().getLatitude()), Double.parseDouble(wicVendor.getLocation().getLongitude()))));
            }
        }
    }
}
