package projeto.undercode.com.proyectobrapro.controllers;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import projeto.undercode.com.proyectobrapro.R;


public class MapaGoogleMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        final Bundle bundle = getIntent().getExtras();
        Log.d("bundle", bundle.toString());

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-15.8329459, -48.0845006);
        LatLng markPosition = new LatLng(Double.valueOf(bundle.getString("Latitude")), Double.valueOf(bundle.getString("Longitude")));
        mMap.addMarker(new MarkerOptions()
                .position(markPosition)
                .title("Marker Ponto"));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getLayoutInflater().inflate(R.layout.marker, null);

                TextView obs= (TextView) v.findViewById(R.id.marker_Obs);
                TextView endereco= (TextView) v.findViewById(R.id.marker_Endereco);
                TextView data= (TextView) v.findViewById(R.id.marker_Data_cadastro);

                obs.setText("Observacao: " + bundle.getString("Obs"));
                endereco.setText("Endereco: " + bundle.getString("Endereco"));
                data.setText("Data de cadastro: " + bundle.getString("Data_cadastro"));

                return v;
            }
        });



        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markPosition, 16));
    }
}
