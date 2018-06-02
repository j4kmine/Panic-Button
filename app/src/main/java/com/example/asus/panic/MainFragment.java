package com.example.asus.panic;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements LocationListener {
    View rootView;
    ImageView imageView;
    DataHelper mDatabaseHelper;
    double longtitude,latitude;
    OkHttpClient client;
    MediaType JSON;
    LocationManager locationManager;
    public DataHelper mDatabase;
    public MainFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        imageView =(ImageView)rootView.findViewById(R.id.btn_danger);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper = new DataHelper(getActivity());
                List<Kontak> allkontak = mDatabaseHelper.listKontak();
                Gson gson = new GsonBuilder().create();
                JsonArray myCustomArray = gson.toJsonTree(allkontak).getAsJsonArray();
                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
                    Toast.makeText(getActivity(),"Gps Tidak Aktif" ,Toast.LENGTH_LONG).show();
                }else {
                    LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Toast.makeText(getActivity(),"Gps Tidak Aktif" ,Toast.LENGTH_LONG).show();
                    }else{
                        LocationManager locationManager;
                        locationManager =(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                        client = new OkHttpClient();
                        JSON = MediaType.parse("application/json; charset=utf-8");
                        PostTask task = new PostTask();
                        task.execute();

                    }
                }
            }
        });
        return rootView;
    }
    public class PostTask extends AsyncTask<String, Void, String> {
        private Exception exception;
        protected String doInBackground(String... urls) {
            String getResponse;
            try {
                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
                    Toast.makeText(getActivity(),"Gps Tidak Aktif" ,Toast.LENGTH_LONG).show();
                }else {
                    LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Toast.makeText(getActivity(),"Gps Tidak Aktif" ,Toast.LENGTH_LONG).show();
                    }else{
                        Vibrator v = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(10000);
                        mDatabaseHelper = new DataHelper(getActivity());
                        List<Kontak> allkontak = mDatabaseHelper.listKontak();
                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(allkontak).getAsJsonArray();
                        LocationManager locationManager;
                        locationManager =(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                        getResponse = post("http://45.63.62.230:8012/komos/", String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude()),String.valueOf(myCustomArray));
                    }
                }
                return null;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        private String post(String url, String longitude,String latitude,String json) throws IOException {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("latitude", latitude)
                    .addFormDataPart("longitude", longitude)
                    .addFormDataPart("kontak", json)
                    .addFormDataPart("token", FirebaseInstanceId.getInstance().getToken())
                    .build();
            Request requests = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(requests).execute();
            return response.body().string();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        longtitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
