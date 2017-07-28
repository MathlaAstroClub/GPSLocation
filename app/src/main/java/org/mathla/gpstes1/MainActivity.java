package org.mathla.gpstes1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnShowLocation;

    GpsService	gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowLocation = (Button) findViewById(R.id.btnShowGPS);
        btnShowLocation.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // buat class object dari GpsService
                gps = new GpsService(MainActivity.this);

                // dicek dulu apakah GPSnya idup
                if (gps.canGetLocation())
                {
                    // ambil latitude dan longitude
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // tampilkan make Toast
                    Toast.makeText(getApplicationContext(),
                            "Lokasi mu latitude: " + latitude + " Longitude : " + longitude, Toast.LENGTH_LONG).show();
                } else
                {
                    // jika GPS tidak aktif
                    gps.showSettingAlert();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
