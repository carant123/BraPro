package projeto.undercode.com.proyectobrapro.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

/**
 * Created by Level on 16/09/2016.
 */
public class ControladorGPS implements Runnable, LocationListener {

    private static ControladorGPS objControladorGPS = null;
    private static Context mContext;

    private static Location mLocation;
    protected static LocationManager mLocationManager;

    protected ControladorGPS() {
    }

    public synchronized static ControladorGPS getInstance(Context context) {
        if (objControladorGPS == null) {
            objControladorGPS = new ControladorGPS();
        }
        mContext = context;
        return objControladorGPS;
    }

    public void iniciarGPS() {

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        try {
            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

            if (mLocationManager != null) {
                mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void finalizarGPS() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void run() {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    public static synchronized Location getLocation(){
        return mLocation;
    }
}

