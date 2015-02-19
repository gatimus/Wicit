package com.natebass.wicit.api;

import com.natebass.wicit.model.WicVendorData;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by nwb on 2/12/15.
 */
public class LocationApiClient {
    private static LocationApiInterface locationApiInterface;
    
    public static LocationApiInterface getWicitApiClient()  {
        if (locationApiInterface == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://cdph.data.ca.gov")
                    .build();
            locationApiInterface = restAdapter.create(LocationApiInterface.class);
        }
        return locationApiInterface;
    }
    
    public interface LocationApiInterface {
        @GET("/resource/i7wi-ei4m.json")
        // todo: pass params as
        void getLocations(@Query("within_box")  Callback<List<WicVendorData>> callback);
    }
}
