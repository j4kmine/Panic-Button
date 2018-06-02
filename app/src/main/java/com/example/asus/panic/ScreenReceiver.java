
        package com.example.asus.panic;

        import android.*;
        import android.Manifest;
        import android.app.Activity;
        import android.app.Application;
        import android.app.KeyguardManager;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.media.MediaPlayer;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.PowerManager;
        import android.os.Vibrator;
        import android.support.v4.app.ActivityCompat;
        import android.util.Log;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Toast;

        import com.google.firebase.iid.FirebaseInstanceId;
        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import com.google.gson.JsonArray;

        import java.io.IOException;
        import java.time.Duration;
        import java.time.LocalDate;
        import java.time.format.DateTimeFormatter;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;

        import okhttp3.MediaType;
        import okhttp3.MultipartBody;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.RequestBody;
        import okhttp3.Response;

        import static android.content.ContentValues.TAG;
        import static java.security.AccessController.getContext;

        /**
 * Created by ASUS on 4/30/2018.
 */

public class ScreenReceiver extends BroadcastReceiver implements LocationListener {
    private  MediaPlayer player;
    OkHttpClient client;
    MediaType JSON;
    DataHelper mDatabaseHelper;
    public DataHelper mDatabase;
    Context context;



    public ScreenReceiver(Context context) {
        this.context = context;
    }
    public static Integer screendeath = 0;
    public static boolean wasScreenOn = true;
    public static Date wasDate;
    public static Integer score =0 ;
    public  static boolean Alaram = false;

    @Override
    public void onLocationChanged(Location location) {

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

    public class PostTask extends AsyncTask<String, Void, String> {
        private Exception exception;

        protected String doInBackground(String... urls) {
            try {
                mDatabaseHelper = new DataHelper(context);
                List<Kontak> allkontak = mDatabaseHelper.listKontak();
                Gson gson = new GsonBuilder().create();
                JsonArray myCustomArray = gson.toJsonTree(allkontak).getAsJsonArray();

                if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    System.exit(0);
                }
                LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    System.exit(0);
                }
                LocationManager locationManager;
                locationManager =(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                Log.d(TAG,"this log created by asus");
                String getResponse = post("http://45.63.62.230:8012/komos/", String.valueOf(myCustomArray), String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude()));
                return getResponse;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }
        public String bowlingJson(String player1, String player2) {
            return "{'winCondition':'HIGH_SCORE',"
                    + "'name':'Bowling',"
                    + "'round':4,"
                    + "'lastSaved':1367702411696,"
                    + "'dateStarted':1367702378785,"
                    + "'players':["
                    + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                    + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                    + "]}";
        }

        private String post(String url, String json,String longitude,String latitude) throws IOException {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("latitude", latitude)
                    .addFormDataPart("longitude", longitude)
                    .addFormDataPart("kontak", json)
                    .addFormDataPart("token",FirebaseInstanceId.getInstance().getToken())
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // do whatever you need to do here
            wasScreenOn = false;
            screendeath = screendeath +1;
            Date currentTime = Calendar.getInstance().getTime();

            if(wasDate != null){
                long diff = currentTime.getTime() - wasDate.getTime();
                long diffSeconds = diff / 1000 % 60;
                if(diffSeconds <= 1){
                    score++;
                }else{
                    score = 0;
                    wasDate = null;
                }
                if(score == 4){
                    Alaram = true;
                    if(Alaram == true){
                        Vibrator v = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(100000);
                        client = new OkHttpClient();
                        JSON = MediaType.parse("application/json; charset=utf-8");
                        PostTask task = new PostTask();
                        task.execute();

                    }

                }
            }

            wasDate = currentTime;

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // and do whatever you need to do here
            wasScreenOn = true;

        }else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
//            Vibrator v = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
//            v.vibrate(10000);



        }
    }
}
