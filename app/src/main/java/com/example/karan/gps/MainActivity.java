package com.example.karan.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
TextView tv;
LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.textView);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) //checking for permission, coarse location will tell ur current longitude and lattitude
                != PackageManager.PERMISSION_GRANTED && //cause in beg permission is not granted
                ActivityCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) //fine location tell ur stationary longitude and lattitude
                !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                                                                       Manifest.permission.ACCESS_FINE_LOCATION},0);
            return;
        }
        lm= (LocationManager) getSystemService(LOCATION_SERVICE); //location manager will have current location refference in it
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
               double latitude= location.getLatitude();
               double longitude= location.getLongitude();
               tv.setText("LATTITUDE:- "+latitude+"\n LONGITUDE:-"+longitude);
                Geocoder geocoder = new Geocoder(MainActivity.this); //this will convert lattitude and longitude in address
                try
                {
                  List<Address> adr = geocoder.getFromLocation(latitude,longitude,1);
                  String country = adr.get(0).getCountryName(); //to get country name
                  String localtiy = adr.get(0).getLocality();  //to get locality
                  String postalcode = adr.get(0).getPostalCode(); //to get postal address
                  String address = adr.get(0).getAddressLine(0); // to get address
                    tv.append("\n\n"+country+","+localtiy+","+postalcode+","+address);
                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle)
            {

            }

            @Override
            public void onProviderEnabled(String s)
            {

            }

            @Override
            public void onProviderDisabled(String s)
            {

            }
        });
    }
}
